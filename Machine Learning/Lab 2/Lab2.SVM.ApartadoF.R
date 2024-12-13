# Laboratorio 2
# Autor: Emilio Gómez Esteban
# Titulacion: Doble Grado Matematicas e Ingenieria Informatica

# Realiza un programa en R que calcule los siguientes parámetros de una Maquina de Soporte Vectorial (SVM, en ingles):
# 1. Determinar y mostrar los Vectores Soporte.
# 2. Calcular todos los valores del Kernel (es, la matriz K formada por: K(A,A) K(B,A) K(B,A) K(B,B)
#    Siendo A y B los puntos de un dataset con 2 observaciones, Supón que el Kernel es el dot product (K(u,v)=u.v)
# 3. Ancho del canal
# 4. Vector de Pesos normal al Hiperplano (W)
# 5. Vector B
# 6. La ecuación del Hiperplano y de los planos de soporte positivo y negativo.
# 7. Determinar la clase a la que pertenece un punto dado.
# Además, pintar el conjunto de puntos (suponiendo que inicialmente los puntos están en el plano Euclídeo) y el Hiperplano.

library(kernlab)
library(MASS)
library(e1071)


#----------------------------------------------------------------------------------------------------------------

# f. Realiza los apartados anteriores con el dataset IRIS


# APARTADO F - KERNLAB ------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO F: \n\n")


# Creamos los datas para el ejercicio 
data(iris) 
dataF <- iris 

# Entrenar el modelo SVM con kernlab usando un kernel lineal
svmF <- ksvm(Species~., data = iris, kernel = "vanilladot", C = 1)

# 1. Vectores de soporte
vsF <- dataF[alphaindex(svmF)[[1]], 1:2]
cat("1.Vectores de soporte:\n")
print(vsF)

# 2. Calcular los valores del kernel
X <- as.matrix(dataF[, c("Sepal.Length", "Sepal.Width")])

# Calcular la matriz del kernel (producto punto)
K <- X %*% t(X)

cat("2.Valores del kernel:\n")
print(K)

# 3. Vector de pesos normal al hiperplano (W)
wF <- colSums(coef(svmF)[[1]] * as.matrix(vsF))
cat("3.Vector de pesos W:\n")
print(wF)

# 4. Calcular el ancho del canal
widthF <- 2 / sqrt(sum(wF^2))
cat("4.Ancho del canal:", widthF, "\n")

# 5. Calcular el valor de b
bF <- -b(svmF)
cat("5.Valor de b:", bF, "\n")

# 6. Mostrar las ecuaciones del hiperplano y planos de soporte (esto también será multiclase)
cat("6.Ecuaciones del hiperplano y planos de soporte para cada clase:\n")
# El modelo SVM ajusta un hiperplano por clase en un modelo multiclase
for (i in 1:length(bF)) {
  cat(paste("Para la clase", levels(iris$Species)[i], ":\n"))
  cat("Ecuación del hiperplano: ", wF, "* x + ", bF[i], " = 0\n")
  cat("Plano de soporte positivo ",i,": [", wF, "]' * x + [", bF[i], "] = 1\n")
  cat("Plano de soporte negativo ",i,": [", wF, "]' * x + [", bF[i], "] = -1\n")
}

# Crear gráfico de los datos de entrenamiento y las clases
par(mfrow = c(1, 1))
plot(dataF[, 1:2], col = as.factor(dataF$Species), pch = 19, xlim = c(-8, 8), ylim = c(-8, 8))
legend("topleft", legend = levels(dataF$Species), col = 1:length(levels(dataF$Species)), pch = 19)

# 8. Dibujar los márgenes de separación (esto se hace para la primera clase en el modelo binario)
# Si estás trabajando con una clasificación binaria, puedes dibujar el margen de la primera clase.
# Este paso sería diferente si se tratara de clasificación multiclase
if (length(levels(dataF$Species)) == 2) {
  abline(a = -bF / wF[2], b = -wF[1] / wF[2], col = "green") # Hiperplano
  abline(a = (1 - bF) / wF[2], b = -wF[1] / wF[2], col = "red", lty = 2) # Margen positivo
  abline(a = (-1 - bF) / wF[2], b = -wF[1] / wF[2], col = "red", lty = 2) # Margen negativo
}


# Función para clasificar puntos
print_clasificacion <- function (x, w, b) {
  # Calculamos el valor para cada clase
  resultados <- sapply(1:length(b), function(i) {
    t(w) %*% x + b[i]
  })
  
  # Determinamos la clase de acuerdo al valor más grande
  clase_predicha <- which.max(resultados)  # La clase con el valor máximo
  
  cat("Punto:", sprintf("(%g, %g)", x[1], x[2]), "\n")
  cat("Pertenece a la clase:", levels(iris$Species)[clase_predicha], "\n")
  
  # Visualización de los puntos
  points(x[1], x[2], col = ifelse(clase_predicha == 1, "blue", ifelse(clase_predicha == 2, "green", "red")), pch = 19)
  text(x[1], x[2], labels = paste("(", x[1], ",", x[2], ")", sep = ""), pos = 3)
}


# 7. Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 <- c(5, 6)
x2 <- c(1, -4)

cat("\n7.Clasificación E:\n")
print_clasificacion(x1, wF, bF)
print_clasificacion(x2, wF, bF)

cat("\nEl punto [5, 6] se clasifica como clase", levels(iris$Species)[which.max(sapply(1:length(bF), function(i) t(wF) %*% x1 + bF[i]))], ".\n")
cat("\nEl punto [1, -4] se clasifica como clase", levels(iris$Species)[which.max(sapply(1:length(bF), function(i) t(wF) %*% x2 + bF[i]))], ".\n")

