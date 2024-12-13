library(pROC)
library(rpart)
library(rpart.plot)

train <- read.csv("C:/Users/Usuario/Desktop/Universidad/5º CURSO (2024-2025)/Asignaturas/Primer Cuatrimestre/Aprendizaje Computacional/train.csv", header=T, quote="\"")
train$PassengerId<-NULL
train$Name<-NULL
train$Ticket<-NULL
train$Embarked<-NULL
train$Cabin<-NULL
train<-na.omit(train)

set.seed(123)
ind <- sample(714,714)
idt <- ind[1:50]
dtrain <- train[-idt,]
dtest <- train[idt,]
titanic.fit <- rpart(Survived~., data=dtrain)

rpart.plot(titanic.fit)

opt <- which.min(titanic.fit$cptable[,"xerror"])
cpTITANIC <- titanic.fit$cptable[opt,"CP"]
titanic_podado <- prune(titanic.fit, cp = cpTITANIC)
rpart.plot(titanic_podado)

titanic.pred <- predict(titanic.fit,dtest,type="prob")

