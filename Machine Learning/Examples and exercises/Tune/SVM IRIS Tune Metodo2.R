library(e1071)
plot(iris)
plot(iris$Petal.Length, iris$Petal.Width, col=iris$Species)
s <-sample(150, 100)
col<-c("Petal.Length", "Petal.Width", "Species")
iris_train <-iris[s,col]
iris_test <-iris[-s,col]

svmfit <-svm(Species ~., data=iris_train, kernel="linear", cost=0.1, scale=F)
print(svmfit)
plot(svmfit, iris_train[,col])
tuned <-tune(svm, Species~., data=iris_train, kernel="linear", ranges=list(cost=c(0.001, 0.01, 0.1, 1,10,100)))
summary(tuned)