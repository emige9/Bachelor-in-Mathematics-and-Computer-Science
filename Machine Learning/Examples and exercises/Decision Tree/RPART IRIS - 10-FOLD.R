library(rpart)
set.seed(1234)
myFormula <- Species ~ Sepal.Length + Sepal.Width + Petal.Length + Petal.Width

#rpart

ind <- sample(150,150)
accuracy<-vector()

#Perform 10 fold cross validation
for(i in 1:10){
  iinicial <- 15*(i-1)+1
  ifinal <- 15*i
  idt <- ind[iinicial : ifinal]
  dtrain <- iris[-idt,]
  dtest <- iris[idt,]
  fit <- rpart(myFormula, dtrain)
  testPred <- predict(fit, newdata = dtest, type="class")
  matrizconfusion<-table(testPred, dtest$Species)
  accuracy[i]<-sum(diag(matrizconfusion))/sum(matrizconfusion)
  
}
cat("mean rpart: ",mean (accuracy))
cat("min rpart: ",min (accuracy))
cat("max rpart: ",max (accuracy))
