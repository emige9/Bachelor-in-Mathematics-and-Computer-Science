library(e1071)
library(nnet)
library(rpart)

data_titanic <- read.csv("C:/Users/Usuario_UMA/Downloads/train.csv", stringsAsFactors=TRUE)
#eliminar datos faltantes
data_titanic<-na.omit(data_titanic)

#eliminar columnas no necesarias
data_titanic$Name=NULL
data_titanic$PassengerId=NULL
data_titanic$Ticket=NULL
data_titanic$Cabin=NULL
data_titanic$Embarked=NULL

#data_titanic<-data_titanic[1:50,]
#data_titanic$Embarked=NULL


#cuenta el numero de na por columna
#sampply(data_titanic, funcion(x) sum(is.na(x)))



#validacion cruzada
set.seed(200)
d_size<-dim(data_titanic)[1]
dtest_size <- ceiling(0.2*d_size)
samples<-sample(d_size, d_size, replace=FALSE)
indexes<-samples[1:dtest_size]
dtrain<-data_titanic[-indexes,]
dtest<-data_titanic[indexes,]

#Perceptron
perceptronTitanic<- nnet (Survived ~. , data=dtrain,size=2 )
matrizconfusion<-table( predict(perceptronTitanic ,dtest ),  dtest$Survived)
accuracy<-sum(diag(matrizconfusion))/sum(matrizconfusion)
accuracy
#rpart
rpartTitanic<-rpart(Survived ~. , data=dtrain, control = rpart.control(minbucket  = 5))
matrizconfusion<-table( predict(rpartTitanic ,dtest ),  dtest$Survived)
accuracy<-sum(diag(matrizconfusion))/sum(matrizconfusion)
accuracy

indiceminxerror <- which.min(rpartTitanic$cptable[,"xerror"])
cptitanic <- rpartTitanic$cptable[indiceminxerror, "CP"]
rpartTitanicConCP<-prune (rpartTitanic,  cp  = cptitanic)
matrizconfusion<-table( predict(rpartTitanicConCP ,dtest ),  dtest$Survived)
accuracy<-sum(diag(matrizconfusion))/sum(matrizconfusion)
accuracy



rpart.fit1 <- rpart(Survived ~ ., data = dtrain)
matrizconfusion_rpart.fit1 <- table(predict(rpart.fit1, newdata = dtest), dtest$Survived)
accuracy_rpart.fit1 <- sum(diag(matrizconfusion_rpart.fit1))/sum(matrizconfusion_rpart.fit1)
accuracy_rpart.fit1 # 0.7412587

rpart.fit1$cptable # Hay 6 cp
# cp
rpart.tune <- tune.rpart(Survived ~. , data = dtrain, 
                         cp = c(rpart.fit1$cptable[1],rpart.fit1$cptable[2],rpart.fit1$cptable[3],rpart.fit1$cptable[4],rpart.fit1$cptable[5],rpart.fit1$cptable[6]))
rpart.tune$best.model # Mejor modelo
matrizconfusion_rpart.tune <- table(predict(rpart.tune$best.model,dtest, type = "vector"), dtest$Survived, dnn=c("Prediction", "Actual"))
accuracy_rpart.tune <- sum(diag(matrizconfusion_rpart.tune))/sum(matrizconfusion_rpart.tune)
accuracy_rpart.tune # 0.7272727
# Hacer poda
opt <- which.min(rpart.fit1$cptable[,"xerror"])
cp1 <- rpart.fit1$cptable[opt, "CP"]
rpart.poda<-prune (rpart.fit1, cp=cp1)
matrizconfusion_rpart.poda <- table(predict(rpart.poda, newdata = dtest, type = "vector"), dtest$Survived)
accuracy_rpart.poda <- sum(diag(matrizconfusion_rpart.poda))/sum(matrizconfusion_rpart.poda)
accuracy_rpart.poda # 0.7412587
