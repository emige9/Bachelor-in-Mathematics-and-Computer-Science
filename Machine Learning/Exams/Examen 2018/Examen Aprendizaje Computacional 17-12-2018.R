# ------------------------------------------------------------------------------
# Ejercicio 2 ------------------------------------------------------------------
# ------------------------------------------------------------------------------

# Resolución con RPART ---------------------------------------------------------

library(e1071)
library(rpart)
voteTraining <- read.csv("C:/Users/Usuario/Desktop/Universidad/5º CURSO (2024-2025)/Primer Cuatrimestre/Aprendizaje Computacional/Exámenes/Examen 2018/vote.data.txt", header=FALSE)
voteTest <- read.csv("C:/Users/Usuario/Desktop/Universidad/5º CURSO (2024-2025)/Primer Cuatrimestre/Aprendizaje Computacional/Exámenes/Examen 2018/voteTraining.test.txt", header = FALSE)

# Import DataSet, vote.data NO HEADER.

voteTraining

names(voteTraining)<-c("handicappedinfants",
                       "waterprojectcostsharing",
                       "adoptionofthebudgetresolution",
                       "physicianfeefreeze",
                       "elsalvadoraid",
                       "religiousgroupsinschools",
                       "antisatellitetestban",
                       "aidtonicaraguancontra",
                       "mxmissile",
                       "immigration",
                       "synfuelscorporationcutback",
                       "educationspending",
                       "superfundrighttosue",
                       "crime",
                       "dutyfreeexports",
                       "exportadministrationactsouthafrica", "votation")

names(voteTest)<-c("handicappedinfants",
                   "waterprojectcostsharing",
                   "adoptionofthebudgetresolution",
                   "physicianfeefreeze",
                   "elsalvadoraid",
                   "religiousgroupsinschools",
                   "antisatellitetestban",
                   "aidtonicaraguancontra",
                   "mxmissile",
                   "immigration",
                   "synfuelscorporationcutback",
                   "educationspending",
                   "superfundrighttosue",
                   "crime",
                   "dutyfreeexports",
                   "exportadministrationactsouthafrica", "votation")


#Eliminar la variable physician fee freeze)
drops <- c("physicianfeefreeze")
voteTraining[ , !(names(voteTraining) %in% drops)]
voteTraining
voteTest[ , !(names(voteTest) %in% drops)]
voteTest
#Entrenar
fit <- rpart(formula= voteTraining$votation ~., data=voteTraining)


#Dibujar arbol
plot(fit)
#Mostrar tabla CP
print(fit$cptable)
text(fit, use.n=TRUE)

#Podar el arbol eligiendo el Cp adecuado
opt <- which.min(fit$cptable[,"xerror"])
cp1 <- fit$cptable[opt, "CP"]
arbolpodado<-prune (fit, cp=cp1)
plot(arbolpodado)
text(arbolpodado, use.n=TRUE)

fit
arbolpodado


#Predecir
predict <- predict(object = arbolpodado, newdata = voteTest, type = "class")
predict
#Para comparar accuracy
prueba_fit <- predict(object = fit, newdata = voteTest, type = "class")


#Accuracy
matriz1 <- table(predict, voteTest$votation)
matriz2 <- table(prueba_fit, voteTest$votation)

suma_matriz1 <- sum(matriz1)
suma_matriz2 <- sum(matriz2)

aciertos1 <- sum(diag(matriz1))
aciertos2 <- sum(diag(matriz1))

accuracy1 <- aciertos1/suma_matriz1
accuracy2 <- aciertos2/suma_matriz2

#Comparar valores de accuracy
print(accuracy1)
print(accuracy2)
#Ambos son iguales, podar el arbol por los valores CP no influye aqui. Esto de debe a que el numero de splits no es grande
#y la tabla CP es pequeña, por tanto, no hay cambio significativo. Por ello, con un accuracy de  0.9703704, como nos ha
#salido, es lo suficientemente bueno y adecuado.


#Cuando se añade el campo eliminado, lo que ocurre es que simplmente el dataset se hace mas grande.



# ------------------------------------------------------------------------------
# Ejercicio 3 ------------------------------------------------------------------
# ------------------------------------------------------------------------------

# kernel = linear 
# kernel = radial, gamma = 0,1
# kernel = polynomial, degree = 3

# Importamos las Librerias necesarias
library (kernlab)
library(e1071)

print_clasificacion <- function (x, w, b) {
  
  if ((t (w) %*% x + b) >= 0)
  {
    print (x)
    print("Pertence a la clase: 1")
    val = 1
  }
  else
  {
    print (x)
    print("Pertence a la clase: -1")
    val = -1
  }
  # Si clasifica positivamente --> azul, si clasifica negativamente --> rojo
  points(x[1], x[2], col = ifelse(val == 1, "blue", "red"), pch = 19)
  text(x[1], x[2], labels = paste("(", x[1], ",", x[2], ")", sep = ""), pos = 3)
}

dataA <- data.frame( 
  x1 = c(1, 3, 1, 3, 2, 3, 4), 
  x2 = c(1, 3, 3, 1, 2.5, 2.5, 3), 
  y = c(-1, 1, 1, -1, 1, -1, -1) 
) 

library(ggplot2)
ggplot(data = dataA, aes(x=x1, y=x2, color=as.factor(y))) + geom_point(size=3)
# Es claro que es linealmente separable

# Crear un gráfico
par(mfrow = c(1, 1))
plot(dataA[, c("x1", "x2")], col = ifelse(dataA$y == 1, "blue", "red"), pch = 19, xlim = c(0, 5), ylim = c(0, 5))
# Etiquetar los puntos
for (i in 1:nrow(dataA)) {
  text(dataA$x1[i], dataA$x2[i], labels = paste("(", dataA$x1[i], ",", dataA$x2[i], ")", sep = ""), pos = 3)
}
# Agregar una leyenda
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)

# Indicamos que la columna y es la importante 
dataA$y <- as.factor(dataA$y)

# Creamos el SVM con los datos del C con un kernel lineal 
svmA <- ksvm(as.matrix(dataA[, c("x1", "x2")]), dataA$y, kernel = "vanilladot", C = 1) 

#Vectores de soporte 
vsA <- dataA[alphaindex(svmA)[[1]], 1:2] 

# Vector de pesos normal al hiperplano (W) 
# Hacemos el CrosProduct entre los vectores soporte y el coe. de Lagrange 
wA <- colSums(coef(svmA)[[1]] * as.matrix(vsA))

# Calcular ancho del canal 
widthA = 2/(sqrt(sum((wA)^2))) 

# Calcular vector B 
bA <- -b(svmA)

# Calcular la ecuacion del hiperplano y de los planos de soporte positivo y negativo 
Withd=2 / (sum (sqrt ((wA)^2))) 
cat(c("[",wA,"]' * x + [",bA,"] = 0\n"), collapse=" ") 
cat(c("[",wA,"]' * x + [",bA,"] = 1\n"), collapse=" ") 
cat(c("[",wA,"]' * x + [",bA,"] = -1\n"), collapse=" ") 

# Dibujar el hiperplano
abline(a = -bA/wA[2], b = -wA[1]/wA[2], col = "green")
abline(a = (1 - bA) / wA[2], b = -wA[1] / wA[2], col = "red", lty = 2)
abline(a = (-1 - bA) / wA[2], b = -wA[1] / wA[2], col = "red", lty = 2)


# Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
clasif1 = c(4, 2.5)
clasif2 = c(4, 1)

print_clasificacion(clasif1,wA, bA)
print_clasificacion(clasif2,wA, bA)

# El punto [4, 2.5] se clasifica positivamente.
# El punto [4, 1] se clasifica positivamente.


### B ###
dataB <- data.frame( 
  x1 = c(1, 3, 1, 3, 2, 3, 4, 1.5, 1), 
  x2 = c(1, 3, 3, 1, 2.5, 2.5, 3, 1.5, 2), 
  y = c(-1, 1, 1, -1, 1, -1, -1, 1, -1) 
) 

ggplot(data = dataB, aes(x=x1, y=x2, color=as.factor(y))) + geom_point(size=3)
# Es claro que NO es linealmente separable

# Crear un gráfico
par(mfrow = c(1, 1))
plot(dataB[, c("x1", "x2")], col = ifelse(dataB$y == 1, "blue", "red"), pch = 19, xlim = c(0, 5), ylim = c(0, 5))
# Etiquetar los puntos
for (i in 1:nrow(dataB)) {
  text(dataB$x1[i], dataB$x2[i], labels = paste("(", dataB$x1[i], ",", dataB$x2[i], ")", sep = ""), pos = 3)
}
# Agregar una leyenda
legend("topleft", legend = c("Y = +1", "Y = -1"), col = c("blue", "red"), pch = 19)

# Indicamos que la columna y es la importante 
dataB$y <- as.factor(dataB$y)

# Creamos el SVM con los datos del C con un kernel lineal 
svmB <- ksvm(as.matrix(dataB[, c("x1", "x2")]), dataB$y, kernel = "vanilladot", C = 1) 

#Vectores de soporte 
vsB <- dataB[alphaindex(svmB)[[1]], 1:2] 

# Vector de pesos normal al hiperplano (W) 
# Hacemos el CrosProduct entre los vectores soporte y el coe. de Lagrange 
wB <- colSums(coef(svmB)[[1]] * as.matrix(vsB)) 

# Calcular ancho del canal 
widthB = 2/(sqrt(sum((wB)^2))) 

# Calcular vector B 
bB <- -b(svmB)

# Calcular la ecuacion del hiperplano y de los planos de soporte positivo y negativo 
Withd=2 / (sum (sqrt ((wB)^2))) 
paste(c("[",wB,"]' * x + [",bB,"] = 0"), collapse=" ") 
paste(c("[",wB,"]' * x + [",bB,"] = 1"), collapse=" ") 
paste(c("[",wB,"]' * x + [",bB,"] = -1"), collapse=" ") 

# Dibujar el hiperplano
abline(a = -bB/wB[2], b = -wB[1]/wA[2], col = "green")
abline(a = (1 - bB) / wB[2], b = -wB[1] / wB[2], col = "red", lty = 2)
abline(a = (-1 - bB) / wB[2], b = -wB[1] / wB[2], col = "red", lty = 2)


# Determinamos a la clase que pertenece cada uno
# Puntos a clasificar
clasif1 = c(4, 2.5)
clasif2 = c(4, 1)

print_clasificacion(clasif1,wB, bB)
print_clasificacion(clasif2,wB, bB)

# El punto [4, 2.5] se clasifica positivamente.
# El punto [4, 1] se clasifica positivamente.



