library(e1071)
set.seed(100)
ind <- sample(150,150)
idt <- ind[1:10]
dtrain <- iris[-idt,]
dtest <- iris[idt,]
m1 <- svm(Species ~ ., data = dtrain)
matrizconfusionSVM<-table(predict(m1,dtest), dtest$Species, dnn=c("Prediction", "Actual"))
accuracySVM<- sum(diag(matrizconfusionSVM))/sum(matrizconfusionSVM)
svm_cv <- tune("svm", Species ~., data = dtrain, kernel = 'radial',
               ranges = list(cost = c(0.001, 0.01, 0.1, 1, 5, 10, 20),
                             gamma = c(0.5, 1, 2, 3, 4, 5, 10)))
svm_cvpolinomial <- tune("svm", Species ~., data = dtrain, kernel = 'polynomial',
               ranges = list(cost = c(1, 5, 10, 20),
                             degree = c( 1, 2, 3, 4)), gamma=1, coef0=1)
svm_cv$best.parameters
svm_cvpolinomial$best.parameters
mejorSVM<-svm_cv$best.model
matrizconfusionSVMBetter<-table(predict(mejorSVM,dtest), dtest$Species, dnn=c("Prediction", "Actual"))
accuracySVMBetter<- sum(diag(matrizconfusionSVMBetter))/sum(matrizconfusionSVMBetter)
matrizconfusionSVM
matrizconfusionSVMBetter
accuracySVMBetter
accuracySVM
