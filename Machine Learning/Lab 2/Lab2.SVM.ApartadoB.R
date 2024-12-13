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


# b. A=[2,0] que pertenece a la clase Y=+1; B=[0,0] y C=[1,1] con clase 
#    Y=-1. Clasificar los puntos [5,6] y [1,-4].


# APARTADO B ----------------------------------------------------------------------------------------------------


cat("\n Resolución APARTADO B.1: \n\n")
# En este caso debemos encontrar cuales son los más cercanos, los cuales serán los futuros vectores soporte 
A = c(2,0) 
B = c(0,0) 
C = c(1,1) 

# Distancia entre A y B 
distanceAB = distancia(A,B)
#Distancia entre A y C 
distanceAC = distancia(A,C)

# Como la distancia entre A y C es la menor, A y C son los vectores soporte.
# Como B no es vector soporte no lo añado al dataframe.
# Comento el data frame con los tres puntos.
# Datos de entrenamiento
dataB <- data.frame( 
  #  x1 = c(2, 0, 1), 
  #  x2 = c(0, 0, 1), 
  #  y = c(1, -1, -1)
  x1 = c(2, 1), 
  x2 = c(0, 1), 
  y = c(1, -1)
) 

# 1.Vectores de soporte
vsB <- matrix(c(A, C), nrow = 2, byrow = TRUE)
cat("1.Vectores de soporte:\n")
print(vsB)

# 2. Calcular los valores del kernel
KAA <- t(A) %*% A
KAC <- t(A) %*% C
KCC <- t(C) %*% C
cat("2.Valores del kernel:\n")
cat("K(A,A):", KAA, "\nK(A,C):", KAC, "\nK(C,C):", KCC, "\n")

YA=1
YC=-1

b<-c(0,1,-1)
AM <- matrix(c(YA,YC, 0,KAA*YA,KAC*YC,1, KAC*YA,KCC*YC,1),ncol=3, byrow=TRUE)
soluciones=qr.solve(AM,b)
ALFA=matrix(c(soluciones[1], soluciones[2]), ncol=1)
YY=matrix(c(YA,YC), ncol=1)

# 3. Vector de pesos normal al hiperplano (W)
# Hacemos el dot product entre los vectores soporte y el coef. de Lagrange
wB <- t(vsB) %*% (ALFA*YY)
cat("3.Vector de pesos W:\n")
print(wB)

# 4. Calcular ancho del canal
widthB = 2/(sqrt(sum((wB)^2)))
cat("4.Ancho del canal:", widthB, "\n")

# 5. Calcular vector B
bB <- YA-(t(wB) %*% A)
cat("5.Valor de b:", bB, "\n")

# 6. Ecuación del hiperplano y planos de soporte
cat("6.Ecuación del hiperplano y planos de soporte: \n")
cat("Ecuación del hiperplano: [", wB, "]' * x + [", bB, "] = 0\n")
cat("Plano de soporte positivo: [", wB, "]' * x + [", bB, "] = 1\n")
cat("Plano de soporte negativo: [", wB, "]' * x + [", bB, "] = -1\n")

# Crear gráfico
par(mfrow = c(1, 1))
plot(dataB[, c("x1", "x2")], col = ifelse(dataB$y == 1, "blue", "red"), pch = 19, xlim = c(-7, 7), ylim = c(-7, 7))
for (i in 1:nrow(dataB)) {
  text(dataB$x1[i], dataB$x2[i], labels = paste("(", dataB$x1[i], ",", dataB$x2[i], ")", sep = ""), pos = 3)
}
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)

# Dibujar hiperplano y márgenes
abline(a = -bB / wB[2], b = -wB[1] / wB[2], col = "green")
abline(a = (1 - bB) / wB[2], b = -wB[1] / wB[2], col = "red", lty = 2)
abline(a = (-1 - bB) / wB[2], b = -wB[1] / wB[2], col = "red", lty = 2)

# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 = c(5, 6)
x2 = c(1, -4)

cat("7.Clasificación B.1:\n")
print_clasificacion(x1, wB, bB)
print_clasificacion(x2, wB, bB)



# APARTADO B - KERNLAB ------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO B.2: \n\n")

# En este caso debemos encontrar cuales son los más cercanos, los cuales serán los futuros vectores soporte 
A = c(2,0) 
B = c(0,0) 
C = c(1,1) 

# Distancia entre A y B 
distanceAB = distancia(A,B)
#Distancia entre A y C 
distanceAC = distancia(A,C)

# Como la distancia entre A y C es la menor, A y C son los vectores soporte.
# Como B no es vector soporte no lo añado al dataframe.
# Comento el data frame con los tres puntos.
# Datos de entrenamiento
dataB <- data.frame( 
  #  x1 = c(2, 0, 1), 
  #  x2 = c(0, 0, 1), 
  #  y = c(1, -1, -1)
  x1 = c(2, 1), 
  x2 = c(0, 1), 
  y = c(1, -1)
) 

# Convertimos y en factor
dataB$y <- as.factor(dataB$y) 

# Entrenar el modelo SVM con kernlab usando un kernel lineal
svmB <- ksvm(as.matrix(dataB[, c("x1", "x2")]), dataB$y, kernel = "vanilladot", C = 1)

# 1. Vectores de soporte
vsB <- dataB[alphaindex(svmB)[[1]], 1:2]
cat("1.Vectores de soporte:\n")
print(vsB)

# 2. Calcular los valores del kernel
KAA <- t(A) %*% A
KAC <- t(A) %*% C
KCC <- t(C) %*% C
cat("2.Valores del kernel:\n")
cat("K(A,A):", KAA, "\nK(A,C):", KAC, "\nK(C,C):", KCC, "\n")

# 3. Vector de pesos normal al hiperplano (W)
wB <- colSums(coef(svmB)[[1]] * as.matrix(vsB))
cat("3.Vector de pesos W:\n")
print(wB)

# 4. Calcular el ancho del canal
widthB <- 2 / sqrt(sum(wB^2))
cat("4.Ancho del canal:", widthB, "\n")

# 5. Calcular el valor de b
bB <- -b(svmB)
cat("5.Valor de b:", bB, "\n")

# 6. Ecuación del hiperplano y planos de soporte
cat("6.Ecuación del hiperplano y planos de soporte: \n")
cat("Ecuación del hiperplano: [", wB, "]' * x + [", bB, "] = 0\n")
cat("Plano de soporte positivo: [", wB, "]' * x + [", bB, "] = 1\n")
cat("Plano de soporte negativo: [", wB, "]' * x + [", bB, "] = -1\n")

# Crear gráfico
par(mfrow = c(1, 1))
plot(dataB[, c("x1", "x2")], col = ifelse(dataB$y == 1, "blue", "red"), pch = 19, xlim = c(-7, 7), ylim = c(-7, 7))
for (i in 1:nrow(dataB)) {
  text(dataB$x1[i], dataB$x2[i], labels = paste("(", dataB$x1[i], ",", dataB$x2[i], ")", sep = ""), pos = 3)
}
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)

# Dibujar hiperplano y márgenes
abline(a = -bB / wB[2], b = -wB[1] / wB[2], col = "green")
abline(a = (1 - bB) / wB[2], b = -wB[1] / wB[2], col = "red", lty = 2)
abline(a = (-1 - bB) / wB[2], b = -wB[1] / wB[2], col = "red", lty = 2)

# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 = c(5, 6)
x2 = c(1, -4)

cat("7.Clasificación B.2:\n")
print_clasificacion(x1, wB, bB)
print_clasificacion(x2, wB, bB)




# APARTADO B - IPOP ---------------------------------------------------------------------------------------------
cat("\n Resolución APARTADO B.3: \n\n")

# Como la distancia entre A y C es la menor, A y C son los vectores soporte.

# Definir datos de entrada (XOR)
x <- matrix(c(2, 0, 1, 1), ncol = 2, byrow = TRUE)
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
A = c(2,0) 
C = c(1,1) 
KAA <- t(A) %*% A
KAC <- t(A) %*% C
KCC <- t(C) %*% C
cat("2.Valores del kernel:\n")
cat("K(A,A):", KAA, "\nK(A,C):", KAC, "\nK(C,C):", KCC, "\n")

# 3. Calcular el vector de pesos w
wB <- t(x) %*% (alphas * y)
cat("3.Vector de pesos W:\n")
print(wB)

# 4. Calcular el ancho del margen (canal)
widthB <- 2 / sqrt(sum(wB^2))
cat("4.Ancho del canal:", widthB, "\n")


# 5. Calcular el sesgo b usando los vectores de soporte
bB <- mean(y[support_indices] - x[support_indices, ] %*% wB)
cat("5.Valor de b:", bB, "\n")

# 6. Ecuación del hiperplano y planos de soporte
cat("6.Ecuación del hiperplano y planos de soporte:\n")
cat("Ecuación del hiperplano: [", wB, "]' * x + [", bB, "] = 0\n")
cat("Plano de soporte positivo: [", wB, "]' * x + [", bB, "] = 1\n")
cat("Plano de soporte negativo: [", wB, "]' * x + [", bB, "] = -1\n")


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
abline(a = -b / wB[2], b = -wB[1] / wB[2], col = "green", lwd = 2)  # Hiperplano
abline(a = (1 - b) / wB[2], b = -wB[1] / wB[2], col = "red", lty = 2)  # Margen positivo
abline(a = (-1 - b) / wB[2], b = -wB[1] / wB[2], col = "red", lty = 2)  # Margen negativo


# 7.Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
x1 <- c(5, 6)
x2 <- c(1, -4)

cat("7. Clasificación A.3:\n")
print_clasificacion(x1,wB, bB)
print_clasificacion(x2,wB, bB)

