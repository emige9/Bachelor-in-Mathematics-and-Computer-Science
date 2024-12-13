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

# d. Repite el apartado anterior utilizando la función de transformacion

funcion_transformacion <- function(a) {
  if(sqrt(a[1]^2 + a[2]^2) > 2) {
    a[1] <- 4 - a[2] + abs(a[1] - a[2])
    a[2] <- 4 - a[1] + abs(a[1] - a[2])
  }
  a
}

# APARTADO D - KERNLAB ------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO D: \n\n")

dataD <- data.frame(
  x1 = c(2, 2, -2, -2, 1, 1, -1, -1),
  x2 = c(2, -2, -2, 2, 1, -1, -1, 1),
  y = c(1, 1, 1, 1, -1, -1, -1, -1)
)

A = c(2,2) 
B = c(2,-2)
C = c(-2,-2)
D = c(-2,2)
E = c(1,1) 
FF = c(1,-1)
G = c(-1,-1)
H = c(-1,1)

# Aplicar la función de transformación a los vectores X e Y
A2 <- funcion_transformacion(A)
B2 <- funcion_transformacion(B)
C2 <- funcion_transformacion(C)
D2 <- funcion_transformacion(D)

E2 <- funcion_transformacion(E)
FF2 <- funcion_transformacion(FF)
G2 <- funcion_transformacion(G)
H2 <- funcion_transformacion(H)

dataDTrans <- data.frame(
  x1 = c(A2[1], B2[1], C2[1], D2[1], E2[1], FF2[1], G2[1], H2[1]),
  x2 = c(A2[2], B2[2], C2[2], D2[2], E2[2], FF2[2], G2[2], H2[2]),
  y = c(1, 1, 1, 1, -1, -1, -1, -1)
)



# Convertimos y en factor
dataDTrans$y <- as.factor(dataDTrans$y) 

# Entrenar el modelo SVM con kernlab usando un kernel lineal
svmD <- ksvm(as.matrix(dataDTrans[, c("x1", "x2")]), dataDTrans$y, kernel = "vanilladot", C = 1)

# 1. Vectores de soporte
vsD <- dataDTrans[alphaindex(svmD)[[1]], 1:2]
cat("1.Vectores de soporte:\n")
print(vsD)

# 2. Calcular los valores del kernel
# Matriz de características (sin la columna 'y')
X <- as.matrix(dataDTrans[, c("x1", "x2")])

# Calcular la matriz del kernel (producto punto)
K <- X %*% t(X)

cat("2.Valores del kernel:\n")
print(K)

# 3. Vector de pesos normal al hiperplano (W)
wD <- colSums(coef(svmD)[[1]] * as.matrix(vsD))
cat("3.Vector de pesos W:\n")
print(wD)

# 4. Calcular el ancho del canal
widthD <- 2 / sqrt(sum(wD^2))
cat("4.Ancho del canal:", widthD, "\n")

# 5. Calcular el valor de b
bD <- -b(svmD)
cat("5.Valor de b:", bD, "\n")


# 6. Ecuación del hiperplano y planos de soporte
cat("6.Ecuación del hiperplano y planos de soporte: \n")
cat("Ecuación del hiperplano: [", wD, "]' * x + [", bD, "] = 0\n")
cat("Plano de soporte positivo: [", wD, "]' * x + [", bD, "] = 1\n")
cat("Plano de soporte negativo: [", wD, "]' * x + [", bD, "] = -1\n")

# Crear gráfico
par(mfrow = c(1, 1))
plot(dataDTrans[, c("x1", "x2")], col = ifelse(dataDTrans$y == 1, "blue", "red"), pch = 19, xlim = c(-7, 7), ylim = c(-7, 7))
for (i in 1:nrow(dataDTrans)) {
  text(dataDTrans$x1[i], dataDTrans$x2[i], labels = paste("(", dataDTrans$x1[i], ",", dataDTrans$x2[i], ")", sep = ""), pos = 3)
}
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)

# Dibujar hiperplano y márgenes
abline(a = -bD / wD[2], b = -wD[1] / wD[2], col = "green")
abline(a = (1 - bD) / wD[2], b = -wD[1] / wD[2], col = "red", lty = 2)
abline(a = (-1 - bD) / wD[2], b = -wD[1] / wD[2], col = "red", lty = 2)

# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 = c(5, 6)
x2 = c(1, -4)

cat("7.Clasificación D:\n")
print_clasificacion(x1, wD, bD)
print_clasificacion(x2, wD, bD)

cat("\nEl punto [5, 6] se clasifica positivamente.")
cat("\nEl punto [1, -4] se clasifica negativamente.")


