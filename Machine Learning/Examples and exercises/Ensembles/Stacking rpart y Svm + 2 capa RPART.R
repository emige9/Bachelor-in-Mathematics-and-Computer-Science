library(e1071)
library (rpart)

data_titanic <- read.csv("C:/Users/Usuario_UMA/Downloads/train.csv", stringsAsFactors=TRUE)
data_titanic<-data_titanic[1:10,]
#eliminar datos faltantes
data_titanic<-na.omit(data_titanic)

#eliminar columnas no necesarias
data_titanic$Name=NULL
data_titanic$PassengerId=NULL
data_titanic$Ticket=NULL
data_titanic$Cabin=NULL
data_titanic$Embarked=NULL


#validacion cruzada
set.seed(200)
d_size<-dim(data_titanic)[1]
dtest_size <- ceiling(0.2*d_size)
samples<-sample(d_size, d_size, replace=FALSE)
indexes<-samples[1:dtest_size]
dtrain<-data_titanic[-indexes,]
dtest<-data_titanic[indexes,]

#entreno RPART
rpartTitanic<-rpart(Survived ~. , data=dtrain, control = rpart.control(minbucket  = 5))
prediccionRpart<-predict(rpartTitanic ,dtest, type="vector" )
#entreno SVM
svmTitanic <- svm(Survived ~. , data=dtrain)
prediccionSVM<-predict(svmTitanic,dtest, type="class")

#entreno RPART (2 capa)
dtest$Survived<-NULL

entrada2Capa<-rbind(dtest,dtest)
entrada2Capa<-data.frame(entrada2Capa,Survived=c(prediccionRpart ,prediccionSVM) )


rpartTitanic2Capa<-rpart(Survived ~. , data=entrada2Capa, control = rpart.control(minbucket  = 5))
d_t <- read.csv("C:/Users/Usuario_UMA/Downloads/train.csv", stringsAsFactors=TRUE)
predecirViajero25<-d_t[3,]




prediccionRpart<-predict(rpartTitanic2Capa ,predecirViajero25, type="vector" )

