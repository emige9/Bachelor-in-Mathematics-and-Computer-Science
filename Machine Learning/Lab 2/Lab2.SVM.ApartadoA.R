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

  
# a. A=[0,0] que pertenece a la clase Y=+1; B=[4,4] con clase Y=-1. 
#    Clasificar los puntos [5,6] y [1,-4].


# APARTADO A ----------------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO A.1: \n\n")


# Datos
A=c(0,0)
B=c(4,4)
YA=1
YB=-1

# Creamos el conjunto de datos
dataA <- data.frame(x1 = c(0, 4), x2 = c(0, 4), y = c(1, -1))

# 1.Vectores de soporte
vsA <- matrix(c(A, B), nrow = 2, byrow = TRUE)
cat("1.Vectores de soporte:\n")
print(vsA)

# 2.Calculamos los valores del kernel
KAA=t (A) %*% A
KAB=t (A) %*% B
KBB=t (B) %*% B
cat("2.Valores del kernel:\n")
cat("K(A,A):", KAA, "\nK(A,B):", KAB, "\nK(B,B):", KBB, "\n")

b<-c(0,1,-1)
AM <- matrix(c(YA,YB, 0,KAA*YA,KAB*YB,1, KAB*YA,KBB*YB,1),ncol=3, byrow=TRUE)
soluciones=qr.solve(AM,b)
ALFA=matrix(c(soluciones[1], soluciones[2]), ncol=1)
YY=matrix(c(YA,YB), ncol=1)

# 3. Vector de pesos normal al hiperplano (W)
# Hacemos el dot product entre los vectores soporte y el coef. de Lagrange
wA <- t(vsA) %*% (ALFA*YY)
cat("3.Vector de pesos W:\n")
print(wA)

# 4. Calcular ancho del canal
widthA = 2/(sqrt(sum((wA)^2)))
cat("4.Ancho del canal:", widthA, "\n")

# 5. Calcular vector B
bA <- YA-(t(wA) %*% A)
cat("5.Valor de b:", bA, "\n")

# 6. Ecuación del hiperplano y planos de soporte
cat("6.Ecuación del hiperplano y planos de soporte: \n")
cat("Ecuación del hiperplano: [", wA, "]' * x + [", bA, "] = 0\n")
cat("Plano de soporte positivo: [", wA, "]' * x + [", bA, "] = 1\n")
cat("Plano de soporte negativo: [", wA, "]' * x + [", bA, "] = -1\n")

# Crear un gráfico
par(mfrow = c(1, 1))
plot(dataA[, c("x1", "x2")], col = ifelse(dataA$y == 1, "blue", "red"), pch = 19, xlim = c(-7, 7), ylim = c(-7, 7))
# Etiquetar los puntos
for (i in 1:nrow(dataA)) {
  text(dataA$x1[i], dataA$x2[i], labels = paste("(", dataA$x1[i], ",", dataA$x2[i], ")", sep = ""), pos = 3)
}
# Agregar una leyenda
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19, xpd = TRUE)

# Dibujar el hiperplano
abline(a = -bA/wA[2], b = -wA[1]/wA[2], col = "green")
abline(a = (1-bA)/wA[2], b = -wA[1]/wA[2], col = "red", lty = 2)
abline(a = (-1-bA)/wA[2], b = -wA[1]/wA[2], col = "red", lty = 2)

# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 = c(5, 6)
x2 = c(1, -4)

cat("7.Clasificación A.1:\n")
print_clasificacion(x1,wA, bA)
print_clasificacion(x2,wA, bA)



# APARTADO A - KERNLAB ------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO A.2: \n\n")

# Datos
A=c(0,0)
B=c(4,4)

# Datos de entrenamiento
dataA <- data.frame(x1 = c(0, 4), x2 = c(0, 4), y = c(1, -1))

# Convertimos y en factor
dataA$y <- as.factor(dataA$y)

# Entrenar el modelo SVM con kernlab usando un kernel lineal
svmA <- ksvm(as.matrix(dataA[, c("x1", "x2")]), dataA$y, kernel = "vanilladot", C = 1)

# 1. Vectores de soporte
vsA <- dataA[alphaindex(svmA)[[1]], 1:2]
cat("1.Vectores de soporte:\n")
print(vsA)

# 2. Calcular valores del Kernel
KAA <- t(A) %*% A
KAB <- t(A) %*% B
KBB <- t(B) %*% B
cat("2.Valores del kernel:\n")
cat("K(A,A):", KAA, "\nK(A,B):", KAB, "\nK(B,B):", KBB, "\n")

# 3. Vector de pesos (W)
wA <- colSums(coef(svmA)[[1]] * as.matrix(vsA))
cat("3.Vector de pesos W:\n")
print(wA)

# 4. Ancho del canal
widthA <- 2 / sqrt(sum(wA^2))
cat("4.Ancho del canal:", widthA, "\n")

# 5. Vector B (sesgo)
bA <- -b(svmA)
cat("5.Valor de b:", bA, "\n")

# 6. Ecuación del hiperplano y planos de soporte
cat("6.Ecuación del hiperplano y planos de soporte: \n")
cat("Ecuación del hiperplano: [", wA, "]' * x + [", bA, "] = 0\n")
cat("Plano de soporte positivo: [", wA, "]' * x + [", bA, "] = 1\n")
cat("Plano de soporte negativo: [", wA, "]' * x + [", bA, "] = -1\n")

# Graficar los datos y el hiperplano
par(mfrow = c(1, 1))
plot(dataA[, c("x1", "x2")], col = ifelse(dataA$y == 1, "blue", "red"), pch = 19, xlim = c(-7, 7), ylim = c(-7, 7))
for (i in 1:nrow(dataA)) {
  text(dataA$x1[i], dataA$x2[i], labels = paste("(", dataA$x1[i], ",", dataA$x2[i], ")", sep = ""), pos = 3)
}
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)

# Dibujar el hiperplano
abline(a = -bA / wA[2], b = -wA[1] / wA[2], col = "green")
abline(a = (1 - bA) / wA[2], b = -wA[1] / wA[2], col = "red", lty = 2)
abline(a = (-1 - bA) / wA[2], b = -wA[1] / wA[2], col = "red", lty = 2)

# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 = c(5, 6)
x2 = c(1, -4)

cat("7.Clasificación A.2:\n")
print_clasificacion(x1,wA, bA)
print_clasificacion(x2,wA, bA)



# APARTADO A - IPOP ---------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO A.3: \n\n")

# Definir datos de entrada (XOR)
x <- matrix(c(0, 0, 4, 4), ncol = 2, byrow = TRUE)
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


# 2. Calcular valores del Kernel
A <- c(0, 0)
B <- c(4, 4)
KAA <- sum(A * A)
KAB <- sum(A * B)
KBB <- sum(B * B)
cat("2.Valores del kernel:\n")
cat("K(A,A):", KAA, "\nK(A,B):", KAB, "\nK(B,B):", KBB, "\n")

# 3. Calcular el vector de pesos w
wA <- t(x) %*% (alphas * y)
cat("3.Vector de pesos W:\n")
print(wA)

# 4. Calcular el ancho del margen (canal)
widthA <- 2 / sqrt(sum(wA^2))
cat("4.Ancho del canal:", widthA, "\n")

# 5. Calcular el sesgo b usando los vectores de soporte
bA <- mean(y[support_indices] - x[support_indices, ] %*% wA)
cat("5.Valor de b:", bA, "\n")

# 6. Ecuación del hiperplano y planos de soporte
cat("6.Ecuación del hiperplano y planos de soporte:\n")
cat("Ecuación del hiperplano: [", wA, "]' * x + [", bA, "] = 0\n")
cat("Plano de soporte positivo: [", wA, "]' * x + [", bA, "] = 1\n")
cat("Plano de soporte negativo: [", wA, "]' * x + [", bA, "] = -1\n")


# Crear un data frame para graficar los puntos y asignar colores a las clases
data_points <- data.frame(x1 = x[, 1], x2 = x[, 2], y = factor(y))
# Graficar puntos de datos
plot(data_points[, c("x1", "x2")], col = ifelse(data_points$y == 1, "blue", "red"), 
     pch = 19, xlim = c(-7, 7), ylim = c(-7, 7), 
     xlab = "x1", ylab = "x2", main = "Hiperplano y márgenes")
# Etiquetas en los puntos de datos
for (i in 1:nrow(data_points)) {
  text(data_points$x1[i], data_points$x2[i], 
       labels = paste("(", data_points$x1[i], ",", data_points$x2[i], ")", sep = ""), 
       pos = 3)
}
# Leyenda para clases
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)


# Dibujar el hiperplano y los márgenes usando w y b
abline(a = -b / wA[2], b = -wA[1] / wA[2], col = "green", lwd = 2)  # Hiperplano
abline(a = (1 - b) / wA[2], b = -wA[1] / wA[2], col = "red", lty = 2)  # Margen positivo
abline(a = (-1 - b) / wA[2], b = -wA[1] / wA[2], col = "red", lty = 2)  # Margen negativo


# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 <- c(5, 6)
x2 <- c(1, -4)

cat("7. Clasificación A.3:\n")
print_clasificacion(x1,wA, bA)
print_clasificacion(x2,wA, bA)


















