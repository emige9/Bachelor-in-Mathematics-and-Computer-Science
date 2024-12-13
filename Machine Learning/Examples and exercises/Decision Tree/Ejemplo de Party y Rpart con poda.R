#install.packages("party")
#install.packages("rpart")
library(party)
library(rpart)
set.seed(1234)
ind <- sample(2, nrow(iris), replace=TRUE, prob=c(0.7, 0.3))
trainData <- iris[ind==1,]
testData <- iris[ind==2,]
#Party
iris_ctree <- ctree( Species ~ ., data=trainData)
matrizconfusion=table(predict(iris_ctree), trainData$Species)
accuracy=sum(diag(matrizconfusion))/sum(matrizconfusion)
accuracy
print(iris_ctree)
plot(iris_ctree)
#rpart
iris_rpart<-rpart( Species ~ ., data = trainData)
plot(iris_rpart)
text(iris_rpart, use.n=TRUE)
matrizconfusion=table(predict(iris_rpart, newdata =trainData,type="claas"), trainData$Species)
accuracy=sum(diag(matrizconfusion))/sum(matrizconfusion)
accuracy
opt <- which.min(iris_rpart$cptable[,"xerror"])
cpiris <- iris_rpart$cptable[opt, "CP"]
iris_rpart_podado <- prune(iris_rpart, cp= 0.5277778)
plot(iris_rpart_podado)
text(iris_rpart_podado, use.n=TRUE)
