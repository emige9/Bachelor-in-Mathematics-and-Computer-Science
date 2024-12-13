library(neuralnet)


datos_icb <- read.table("C:/Users/graci/Downloads/datos_icb.txt",header=T)

m <- model.matrix( 
  ~ recid+edad + tam + grado + gang + feno + quim + horm, 
  data = datos_icb 
)

ind <- sample(500,500)
aucs <- sample(10,10)
i<-1

ii <- 50*(i-1)+1
is <- 50*i
idt <- ind[ii:is]
dtrain <- datos_icb[-idt,]
dtest <- datos_icb[idt,]

m1 <- model.matrix( 
  ~ recid+ edad + tam+ grado+gang+feno+quim+horm , 
  data = dtrain 
)
m2 <- model.matrix( 
  ~ recid+ edad + tam+ grado+gang+feno+quim+horm , 
  data = dtest 
)

neuralnet.fit <- neuralnet(recidSI~ edad + tam+ gradoG2+gradoG3+gang++quimYes+hormYes  , data=m1, hidden=c(2,2))
plot(neuralnet.fit, rep="best")
neuralnet.fit <- neuralnet(recidSI~ edad + tam+ gradoG2+gradoG3+gang+"fenoHER2 enriched"+"fenoLuminal-HER2"+"fenoLuminal A"+"fenoLuminal B"+"fenoTN no-basal "+quimYes+hormYes  , data=m1, hidden=5)

lr.pred <- predict(lr.fit,dtest,type="raw")
aucs <- auc((dtest$recid=="SI")*1, lr.pred)
obj.roc<-roc((dtest$recid=="SI")*1,lr.pred )
plot(obj.roc)
aucs


XOR <- c(0,1,1,0)
xor.data <- data.frame(expand.grid(c(0,1), c(0,1)), XOR)
print(net.xor <- neuralnet( XOR~Var1+Var2, xor.data, hidden=c(2,3), rep=5))
plot(net.xor, rep="best")


d <- read.csv("C:/Users/graci/Downloads/train.csv")
m <- model.matrix( 
  ~ Survived + Pclass + Sex + Age + SibSp + Parch + Fare + Embarked, 
  data = d 
)
head(m)
library(neuralnet)
r <- neuralnet( 
  Survived ~ Pclass + Sexmale + Age + SibSp + Parch + Fare + EmbarkedC + EmbarkedQ + EmbarkedS, 
  data=m, hidden=10, threshold=0.01
)





~ recid+ edad + tam+ grado+gang+feno+quim+horm 
