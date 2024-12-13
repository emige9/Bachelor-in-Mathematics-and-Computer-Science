#-------------------------------------------------------------------------------
# Ejercicio 1 ------------------------------------------------------------------
#-------------------------------------------------------------------------------
  
library(rpart)
set.seed(100)

x <- c(0,0,0,0,1,1,1)
y <- c(0,0,1,1,0,0,1)
z <- c(0,1,0,1,0,1,1)

Salida <- c(0,1,1,0,1,0,0)

A <- data.frame(x,y,z,Salida)
names(A) <- c("x","y","z","Salida")

# Creo el conjunto de datos
data_size <- 5    #Escogemos 5 datos, ya que es el 80% de 7 aprox.
test_size <- 2

samples <- sample(data_size, data_size, replace = FALSE)
indexes <- samples[1:test_size]
train <- A[-indexes,]
test <- A[indexes,]


# Rpart

# Entrenamiento
arbol <- rpart(formula = Salida~.,data=train, method = "class")
print(arbol)


# Predicción
prediccion <- predict(arbol, newdata = test, type = "class")
print(prediccion)

# Poda
opt <- which.min(arbol$cptable[,"xerror"])
cp1 <- arbol$cptable[opt,"CP"]
arbolpodado <- prune(arbol, cp=cp1)
print(arbolpodado)


#-------------------------------------------------------------------------------
# Ejercicio 2 ------------------------------------------------------------------
#-------------------------------------------------------------------------------

library(e1071)

Datos <- read.csv("C:/Users/Usuario/Desktop/Universidad/5º CURSO (2024-2025)/Primer Cuatrimestre/Aprendizaje Computacional/Exámenes/Examen 2021/ejercicio2SVM.csv", stringsAsFactors = TRUE)
set.seed(100)

Datos$y <- as.factor(Datos$y)

#Hemos usado la siguiente librería para ver mejor si son separables linealmente
library(ggplot2)
ggplot(data = Datos, aes(x=X2, y=X1, color=as.factor(y))) + geom_point(size=3)


# RESPUESTA:
# Vemos claramente que no son separables linealmente.
# Por tanto el kernel será polinomial o radial.

svm <- svm(y~. , Datos, kernel = "polynomial")

# 1. Vectores de soporte
vs <- Datos[svm$index, 1:2]
cat("\nVectores soporte\n")
print(vs)

# 3. Vector de pesos normal al hiperplano(W)
w <- crossprod(as.matrix(vs), svm$coefs)
cat("\nVector de pesos normal al hiperplano\n")
print(w)

# 2. Calcular el ancho del canal
width = 2/(sqrt(sum((w)^2)))
cat("\nAncho del canal\n")
print(width)


# 4. Calcular vector B
b <- -svm$rho
cat("\nVector B\n")
print(b)

# 5. Calcular la ecuación del hiperplano y de los planos de soporte positivo y negativo
cat("\nEcuación del hiperplano y planos positivo y negativo\n")
cat(c("[",w,"]'*x + [",b,"] = 0\n"), collapse="")
cat(c("[",w,"]'*x + [",b,"] = 1\n"), collapse="")
cat(c("[",w,"]'*x + [",b,"] = -1\n"), collapse="")

# 6. Dibujar los puntos y el hiperplano
plot(svm,Datos)

# 7. Clasificamos los puntos
cat("Clasificación punto (0.5, 0.8)")
x = c(0.5, 0.8)
if((t(w) %*% x + b) >= 0){
  print("1")
} else if((t(w) %*% x + b) < 0){
  print("-1")
}

cat("Clasificación punto (0.6, 0.2)")
x = c(0.6, 0.2)
if((t(w) %*% x + b) >= 0){
  print("1")
} else if((t(w) %*% x + b) < 0){
  print("-1")
}


#-------------------------------------------------------------------------------
# Ejercicio 3 ------------------------------------------------------------------
#-------------------------------------------------------------------------------

set.seed(100)

Datos <- read.csv("C:/Users/Usuario/Desktop/Universidad/5º CURSO (2024-2025)/Primer Cuatrimestre/Aprendizaje Computacional/Exámenes/Examen 2021/detect-malicious-URL.csv",header = T, stringsAsFactors = TRUE)

# Validacion Cruzada
d_size<-dim(Datos)[1]
dtest_size <- ceiling(0.2*d_size) # me quedo con un 20% para test
samples<-sample(d_size, d_size, replace=FALSE)
indexes<-samples[1:dtest_size]
train<-Datos[-indexes,]
test<-Datos[indexes,]

########## PERCEPTRON MULTICAPA ####################
library(nnet)

# Entrenamiento
training <- nnet(label~., data=train, size=5, maxit=1000, decay=1, trace=FALSE) 

# Prediccion
prediccion <- predict(training,test,type="raw")

# Matriz de confusión
matrizconfusionPM <- table(prediccion, test$label, dnn=c("Prediction", "Actual"))
# Acuracy
accuracyPM <- sum(diag(matrizconfusionPM))/sum(matrizconfusionPM)

######### RANDOMFOREST ##################
library(randomForest)

# Creamos el randomforest con 10 arboles
rf <- randomForest(label~., data = train, ntree=10, proximity=TRUE)

# Prediccion y accuracy
matrizconfusionRF<- table(predict(rf, newdata = test), test$label)
accuracyRF <- sum(diag(matrizconfusionRF)) / sum(matrizconfusionRF)

########## SVM #########################
library(e1071)
svm <- svm(label~ ., data = train)

# Prediccion y accuracy
matrizconfusionSVM<-table(predict(svm,test), test$label, dnn=c("Prediction", "Actual"))
accuracySVM<- sum(diag(matrizconfusionSVM))/sum(matrizconfusionSVM)


# Ahora la comparación entre ambos
accuracyPM # 0.1
accuracyRF # 0.8
accuracySVM # 0.9

# Si decidimos ampliar la cantidad de arboles del randomforest podremos tener un accuracy de 1.


