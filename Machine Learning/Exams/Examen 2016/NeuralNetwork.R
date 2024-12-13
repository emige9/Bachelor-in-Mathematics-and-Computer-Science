library(stats)
library(pROC)
library(nnet)
library(e1071)
library(rpart)
library(neuralnet)

datos_icb <- read.table("C:/Users/PC/Downloads/datos_icb.txt",header=T)
datos_icb$recid <- as.factor(datos_icb$recid)
set.seed(100)

ind <- sample(500,500)

i<-1

ii <- 50*(i-1)+1
is <- 50*i
idt <- ind[ii:is]
dtrain <- datos_icb[-idt,]
dtest <- datos_icb[idt,]
for (i in 1:10){
  perceptron.fit <- nnet(recid~., data=dtrain, size=i, trace=FALSE)
  #perceptron.fit <- neuralnet(recid~., data=dtrain, hidden= c(i))
  perceptron.pred <- predict(perceptron.fit,dtest,type="class")
  
  matrizconfusion<-table(perceptron.pred,  dtest$recid)
  accuracyPerceptron<-sum(diag(matrizconfusion))/sum(matrizconfusion)
  
  #perceptron.fit$wts #pesos
  cat("Iteración ",i,": ",accuracyPerceptron,"\n")
  
}

