
train <- read.csv("C:/Users/Usuario_UMA/Downloads/train.csv", stringsAsFactors=TRUE)
train$label<-NULL
train <-train[1:100,]

library(MBCbook)
train[8,1]
img <- matrix(as.vector(train[8,-1]), nrow = 28, ncol = 28, byrow = T)
img <- apply(img, 1:2, as.numeric)
imshow(img)


imagen2828<-train[8,-1]

imagen1414<-c(1:195)
for (i in 0:19){
  imagen1414[i]<-mean (imagen2828[[4*i+1]]+imagen2828[[4*i+2]]+imagen2828 [[4*i+3]]+imagen2828[[4*i+4]])
}
