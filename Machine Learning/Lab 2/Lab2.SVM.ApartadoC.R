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

# c. [2, 2], [2, -2], [-2, -2], [-2, 2] [2, 2], [2, -2], [-2, -2], [-2, 2] que 
#    pertenece a la clase Y=+1; [1, 1], [1, -1], [-1, -1], [-1, 1] que pertenece a 
#    la clase Y=+1. Encuentra puntos que clasifiquen positiva y negativamente


# APARTADO C ----------------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO C.1: \n\n")

A = c(2,2) 
B = c(2,-2)
C = c(-2,-2)
D = c(-2,2)
E = c(1,1) 
FF = c(1,-1)
G = c(-1,-1)
H = c(-1,1)


dataC <- data.frame(
  x1 = c(2, 2, -2, -2, 1, 1, -1, -1),
  x2 = c(2, -2, -2, 2, 1, -1, -1, 1),
  y = c(1, 1, 1, 1, -1, -1, -1, -1)
)

# Como la distancia entre A y E es la menor, A y E son los vectores soporte. (hay otros con distancia menor, pero tomamos estos)
# 1.Vectores de soporte
vsC <- matrix(c(A,E), nrow = 2, byrow = TRUE)
cat("1.Vectores de soporte:\n")
print(vsC)

# 2. Calcular los valores del kernel
KAA <- t(A) %*% A
KAE <- t(A) %*% E
KEE <- t(E) %*% E
cat("2.Valores del kernel:\n")
cat("K(A,A):", KAA, "\nK(A,E):", KAE, "\nK(E,E):", KEE, "\n")

YA=1
YE=-1

b<-c(0,1,-1)
AM <- matrix(c(YA,YE, 0,KAA*YA,KAE*YE,1, KAE*YA,KEE*YE,1),ncol=3, byrow=TRUE)
soluciones=qr.solve(AM,b)
ALFA=matrix(c(soluciones[1], soluciones[2]), ncol=1)
YY=matrix(c(YA,YE), ncol=1)


# 3. Vector de pesos normal al hiperplano (W)
wC <- t(vsC) %*% (ALFA*YY)
cat("3.Vector de pesos W:\n")
print(wC)

# 4. Calcular el ancho del canal
widthC <- 2 / sqrt(sum(wC^2))
cat("4.Ancho del canal:", widthC, "\n")

# 5. Calcular el valor de b
bC <- YA-(t(wC) %*% A)
cat("5.Valor de b:", bC, "\n")


# 6. Ecuación del hiperplano y planos de soporte
# Mostrar ecuaciones del hiperplano y márgenes si w no es nulo
if (!all(wC == 0)) {
  cat("6.Ecuación del hiperplano y planos de soporte: \n")
  cat("Ecuación del hiperplano: [", wC, "]' * x + [", bC, "] = 0\n")
  cat("Plano de soporte positivo: [", wC, "]' * x + [", bC, "] = 1\n")
  cat("Plano de soporte negativo: [", wC, "]' * x + [", bC, "] = -1\n")
}

# Crear gráfico
par(mfrow = c(1, 1))
plot(dataC[, c("x1", "x2")], col = ifelse(dataC$y == 1, "blue", "red"), pch = 19, xlim = c(-7, 7), ylim = c(-7, 7))
for (i in 1:nrow(dataC)) {
  text(dataC$x1[i], dataC$x2[i], labels = paste("(", dataC$x1[i], ",", dataC$x2[i], ")", sep = ""), pos = 3)
}
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)

# Dibujar hiperplano y márgenes
# Graficar el hiperplano y los márgenes si w no es nulo
if (!all(wC == 0)) {
  abline(a = -bC / wC[2], b = -wC[1] / wC[2], col = "green")
  abline(a = (1 - bC) / wC[2], b = -wC[1] / wC[2], col = "red", lty = 2)
  abline(a = (-1 - bC) / wC[2], b = -wC[1] / wC[2], col = "red", lty = 2)
}

# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 = c(5, 6)
x2 = c(1, -4)

cat("7.Clasificación C.1:\n")
print_clasificacion(x1, wC, bC)
print_clasificacion(x2, wC, bC)

cat("\nEl punto [5, 6] se clasifica positivamente.")
cat("\nEl punto [1, -4] se clasifica negativamente.")


# APARTADO C - KERNLAB ------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO C.2: \n\n")

dataC <- data.frame(
  x1 = c(2, 2, -2, -2, 1, 1, -1, -1),
  x2 = c(2, -2, -2, 2, 1, -1, -1, 1),
  y = c(1, 1, 1, 1, -1, -1, -1, -1)
)
#A = c(2,2) 
#B = c(2,-2)
#C = c(-2,-2)
#D = c(-2,2)
#E = c(1,1) 
#FF = c(1,-1)
#G = c(-1,-1)
#H = c(-1,1)

# Convertimos y en factor
dataC$y <- as.factor(dataC$y) 

# Entrenar el modelo SVM con kernlab usando un kernel lineal
svmC <- ksvm(as.matrix(dataC[, c("x1", "x2")]), dataC$y, kernel = "vanilladot", C = 1)

# 1. Vectores de soporte
vsC <- dataC[alphaindex(svmC)[[1]], 1:2]
cat("1.Vectores de soporte:\n")
print(vsC)

# 2. Calcular los valores del kernel
# Matriz de características (sin la columna 'y')
X <- as.matrix(dataC[, c("x1", "x2")])
y <- dataC$y

# Calcular la matriz del kernel (producto punto)
K <- X %*% t(X)

cat("2.Valores del kernel:\n")
print(K)

# 3. Vector de pesos normal al hiperplano (W)
wC <- colSums(coef(svmC)[[1]] * as.matrix(vsC))
cat("3.Vector de pesos W:\n")
print(wC)

# 4. Calcular el ancho del canal
widthC <- 2 / sqrt(sum(wC^2))
cat("4.Ancho del canal:", widthC, "\n")

# 5. Calcular el valor de b
bC <- -b(svmC)
cat("5.Valor de b:", bC, "\n")

# Es posible (como en este caso) que el problema no sea linealmente separable
# Verificar si w es nulo
if (all(wC == 0)) {
  cat("El vector w es nulo. Prueba con un kernel no lineal o ajusta los parámetros.\n")
  
  # Entrenar con un kernel RBF en caso de no separabilidad lineal
  svmC <- ksvm(X, y, kernel = "rbfdot", C = 1)
  
  # Recalcular vectores de soporte, w y b con el nuevo modelo
  vsC <- dataC[alphaindex(svmC)[[1]], 1:2]
  wC <- colSums(coef(svmC)[[1]] * as.matrix(vsC))
  bC <- -b(svmC)
  
  cat("Modelo ajustado con kernel RBF.\n")
  cat("3.Vector de pesos W:\n")
  print(wC)
  widthC <- 2 / sqrt(sum(wC^2))
  cat("4.Ancho del canal:", widthC, "\n")
  cat("5.Valor de b:", bC, "\n")
} else {
  # Calcular el ancho del margen solo si w no es nulo
  widthC <- 2 / sqrt(sum(wC^2))
  cat("4.Ancho del canal:", widthC, "\n")
}

# 6. Ecuación del hiperplano y planos de soporte
# Mostrar ecuaciones del hiperplano y márgenes si w no es nulo
if (!all(wC == 0)) {
  cat("6.Ecuación del hiperplano y planos de soporte: \n")
  cat("Ecuación del hiperplano: [", wC, "]' * x + [", bC, "] = 0\n")
  cat("Plano de soporte positivo: [", wC, "]' * x + [", bC, "] = 1\n")
  cat("Plano de soporte negativo: [", wC, "]' * x + [", bC, "] = -1\n")
}

# Crear gráfico
par(mfrow = c(1, 1))
plot(dataC[, c("x1", "x2")], col = ifelse(dataC$y == 1, "blue", "red"), pch = 19, xlim = c(-7, 7), ylim = c(-7, 7))
for (i in 1:nrow(dataC)) {
  text(dataC$x1[i], dataC$x2[i], labels = paste("(", dataC$x1[i], ",", dataC$x2[i], ")", sep = ""), pos = 3)
}
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)

# Dibujar hiperplano y márgenes
# Graficar el hiperplano y los márgenes si w no es nulo
if (!all(wC == 0)) {
  abline(a = -bC / wC[2], b = -wC[1] / wC[2], col = "green")
  abline(a = (1 - bC) / wC[2], b = -wC[1] / wC[2], col = "red", lty = 2)
  abline(a = (-1 - bC) / wC[2], b = -wC[1] / wC[2], col = "red", lty = 2)
}

# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 = c(5, 6)
x2 = c(1, -4)

cat("7.Clasificación C.3:\n")
print_clasificacion(x1, wC, bC)
print_clasificacion(x2, wC, bC)

cat("\nEl punto [5, 6] se clasifica positivamente.")
cat("\nEl punto [1, -4] se clasifica positivamente. \n")
cat("\nNo tiene sentido. Debemos aplicar la función de transformacion.\n")


#En este caso no tiene sentido hacer la clasificación como en apartados anteriores.



# APARTADO C - IPOP ---------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO C.3: \n\n")

# Como la distancia entre A y E es la menor, A y E son los vectores soporte.

# Definir datos de entrada (XOR)
x <- matrix(c(2, 2, 1, 1), ncol = 2, byrow = TRUE)
y <- c(1, -1)  # Etiquetas

# Calcular matriz H
H <- (y %*% t(y)) * (x %*% t(x))

# Vector c
c <- rep(-1, length(y))

# Restricciones
MA <- t(y)
b <- 0
l <- rep(0, length(y))
u <- rep(1e6, length(y))

# Parámetro de tolerancia para el optimizador
r <- 0.1

# Resolver el problema de optimización con ipop
resultado <- ipop(c = c, H = H, A = MA, b = b, l = l, u = u, r = r)

# Extraer los multiplicadores de Lagrange (alfas)
alphas <- primal(resultado)

# Imprimir los alphas
cat("Multiplicadores de Lagrange (alphas):\n")
print(alphas)


# 1. Vectores de soporte
# Filtrar los vectores de soporte (donde alphas > un pequeño umbral)
support_indices <- which(alphas > 1e-5)
support_vectors <- x[support_indices, ]
cat("1.Vectores de soporte:\n")
print(support_vectors)

# 2. Calcular los valores del kernel
A = c(2,2) 
E = c(1,1) 
KAA <- t(A) %*% A
KAE <- t(A) %*% E
KEE <- t(E) %*% E
cat("2.Valores del kernel:\n")
cat("K(A,A):", KAA, "\nK(A,E):", KAE, "\nK(E,E):", KEE, "\n")

# 3. Calcular el vector de pesos w
wC <- t(x) %*% (alphas * y)
cat("3.Vector de pesos W:\n")
print(wC)

# 4. Calcular el ancho del margen (canal)
widthC <- 2 / sqrt(sum(wC^2))
cat("4.Ancho del canal:", widthC, "\n")

# 5. Calcular el sesgo b usando los vectores de soporte
bC <- mean(y[support_indices] - x[support_indices, ] %*% wC)
cat("5.Valor de b:", bC, "\n")

# 6. Ecuación del hiperplano y planos de soporte
# Mostrar ecuaciones del hiperplano y márgenes si w no es nulo
if (!all(wC == 0)) {
  cat("6.Ecuación del hiperplano y planos de soporte: \n")
  cat("Ecuación del hiperplano: [", wC, "]' * x + [", bC, "] = 0\n")
  cat("Plano de soporte positivo: [", wC, "]' * x + [", bC, "] = 1\n")
  cat("Plano de soporte negativo: [", wC, "]' * x + [", bC, "] = -1\n")
}

# Crear gráfico
par(mfrow = c(1, 1))
plot(dataC[, c("x1", "x2")], col = ifelse(dataC$y == 1, "blue", "red"), pch = 19, xlim = c(-7, 7), ylim = c(-7, 7))
for (i in 1:nrow(dataC)) {
  text(dataC$x1[i], dataC$x2[i], labels = paste("(", dataC$x1[i], ",", dataC$x2[i], ")", sep = ""), pos = 3)
}
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)

# Dibujar hiperplano y márgenes
# Graficar el hiperplano y los márgenes si w no es nulo
if (!all(wC == 0)) {
  abline(a = -bC / wC[2], b = -wC[1] / wC[2], col = "green")
  abline(a = (1 - bC) / wC[2], b = -wC[1] / wC[2], col = "red", lty = 2)
  abline(a = (-1 - bC) / wC[2], b = -wC[1] / wC[2], col = "red", lty = 2)
}

# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 = c(5, 6)
x2 = c(1, -4)

cat("7.Clasificación C.3:\n")
print_clasificacion(x1, wC, bC)
print_clasificacion(x2, wC, bC)

cat("\nEl punto [5, 6] se clasifica positivamente.")
cat("\nEl punto [1, -4] se clasifica negativamente.")

