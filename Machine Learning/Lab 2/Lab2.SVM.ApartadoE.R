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


# Creamos la funcion que dira a que clase pertenece cada punto
print_clasificacion <- function (x, w, b) {
  
  if ((t (w) %*% x + b) >= 0)
  {
    cat("Punto:", sprintf("(%g, %g)", x[1], x[2]), "\n")
    print("Pertence a la clase: 1")
    val = 1
  }
  else
  {
    cat("Punto:", sprintf("(%g, %g)", x[1], x[2]), "\n")
    print("Pertence a la clase: -1")
    val = -1
  }
  # Si clasifica positivamente --> azul, si clasifica negativamente --> rojo
  points(x[1], x[2], col = ifelse(val == 1, "blue", "red"), pch = 19)
  text(x[1], x[2], labels = paste("(", x[1], ",", x[2], ")", sep = ""), pos = 3)
}

distancia<- function (x,y) {
  sqrt(sum((x-y)^2))
}



#----------------------------------------------------------------------------------------------------------------

# e. Etiquetados positivamente:
#    {(3, 1), (3, -1), (6, 1), (6, -1)}
#    Etiquetados negativamente:
#    {(1, 0), {0, 1), (0, -1), (-1, 0)}


# APARTADO E - KERNLAB ------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO E: \n\n")

dataE <- data.frame( 
  x1 = c(3,3,6,6,1,0,0,-1), 
  x2 = c(1,-1,1,-1,0,1,-1,0), 
  y = c(1,1,1,1,-1,-1,-1,-1) 
)

A = c(3,1) 
B = c(3,-1) 
C = c(6,1) 
D = c(6,-1) 
E = c(1,0) 
FF = c(0,1) 
G = c(0,-1) 
H = c(-1,0)

# Convertimos y en factor
dataE$y <- as.factor(dataE$y) 

# Entrenar el modelo SVM con kernlab usando un kernel lineal
svmE <- ksvm(as.matrix(dataE[, c("x1", "x2")]), dataE$y, kernel = "vanilladot", C = 1)

# 1. Vectores de soporte
vsE <- dataE[alphaindex(svmE)[[1]], 1:2]
cat("1.Vectores de soporte:\n")
print(vsE)

# 2. Calcular los valores del kernel
# Matriz de características (sin la columna 'y')
X <- as.matrix(dataE[, c("x1", "x2")])

# Calcular la matriz del kernel (producto punto)
K <- X %*% t(X)

cat("2.Valores del kernel:\n")
print(K)

# 3. Vector de pesos normal al hiperplano (W)
wE <- colSums(coef(svmE)[[1]] * as.matrix(vsE))
cat("3.Vector de pesos W:\n")
print(wE)

# 4. Calcular el ancho del canal
widthE <- 2 / sqrt(sum(wE^2))
cat("4.Ancho del canal:", widthE, "\n")

# 5. Calcular el valor de b
bE <- -b(svmE)
cat("5.Valor de b:", bE, "\n")

# 6. Ecuación del hiperplano y planos de soporte
cat("6.Ecuación del hiperplano y planos de soporte: \n")
cat("Ecuación del hiperplano: [", wE, "]' * x + [", bE, "] = 0\n")
cat("Plano de soporte positivo: [", wE, "]' * x + [", bE, "] = 1\n")
cat("Plano de soporte negativo: [", wE, "]' * x + [", bE, "] = -1\n")

# Crear gráfico
par(mfrow = c(1, 1))
plot(dataE[, c("x1", "x2")], col = ifelse(dataE$y == 1, "blue", "red"), pch = 19, xlim = c(-7, 7), ylim = c(-7, 7))
for (i in 1:nrow(dataE)) {
  text(dataE$x1[i], dataE$x2[i], labels = paste("(", dataE$x1[i], ",", dataE$x2[i], ")", sep = ""), pos = 3)
}
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)

# Dibujar hiperplano y márgenes
abline(a = -bE / wE[2], b = -wE[1] / wE[2], col = "green")
abline(a = (1 - bE) / wE[2], b = -wE[1] / wE[2], col = "red", lty = 2)
abline(a = (-1 - bE) / wE[2], b = -wE[1] / wE[2], col = "red", lty = 2)

# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 = c(4, 5)
x2 = c(-4, 0)

cat("7.Clasificación E:\n")
print_clasificacion(x1, wE, bE)
print_clasificacion(x2, wE, bE)

cat("\nEl punto [4, 5] se clasifica positivamente.")
cat("\nEl punto [-4, 0] se clasifica negativamente.")

