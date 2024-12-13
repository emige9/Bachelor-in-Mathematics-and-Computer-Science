library(nnet)
library(pROC)

datos_icb <- read.csv("C:/Users/Usuario_UMA/Downloads/datos_icb.txt", sep="", stringsAsFactors=TRUE)

ind <- sample(500,500)

i<-1

ii <- 50*(i-1)+1
is <- 50*i
idt <- ind[ii:is]
dtrain <- datos_icb[-idt,]
dtest <- datos_icb[idt,]
numeroPerceptrones=10
perceptron <- nnet(recid~., data=dtrain, size=5, maxit=500, decay=1, trace=FALSE)
listaperceptrones<-list(perceptron)
for (i in 1:numeroPerceptrones-1){
ind <- sample(500,500)  
dtrain1 <- datos_icb[-idt,]
perceptron <- nnet(recid~., data=dtrain, size=5, maxit=500, decay=1, trace=FALSE)
listaperceptrones<- c(listaperceptrones,list(perceptron) )
}
lnoes<-c()
lsies<-c()
for (i in 1:numeroPerceptrones){
  pred <- predict(listaperceptrones[[i]],dtest,type="class")
  noes=0
  sies=0
  for (j in 1: length(pred)){
    if (pred[j]=="NO"){
      noes=noes+1
    }else{
      sies=sies+1
    }
    
  }
  lnoes<-c(lnoes, noes)
  lsies<-c(lsies, sies)
}
sum(lnoes)
sum(lsies)
if (sum(lnoes)>sum(lsies)){
  print("NO")
}else{
  print("SI")
}