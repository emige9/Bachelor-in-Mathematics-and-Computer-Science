library(kernlab)
x<-c(0,4)
y<-c(0,4)
z<-c(1,-1)
datosEjemploA<-data.frame(x,y,z)
maquinaSVMejemploA<-ksvm(z~., datosEjemploA, type="C-svc", C=100,kernel="vanilladot")
w <- colSums(coef(maquinaSVMejemploA)[[1]] * datosEjemploA[SVindex(maquinaSVMejemploA),])

b<-b(maquinaSVMejemploA)

cat (-w[1]/w[2],"*x+",-b/w[2],"=1")
cat (-w[1]/w[2],"*x+",-b/w[2],"=-1")
cat (-w[1]/w[2],"*x+",-b/w[2],"=0")

plot (maquinaSVMejemploA, data=datosEjemploA)
abline(-b/w[2], -w[1]/w[2] )
abline((1-b)/w[2], -w[1]/w[2] )
abline((-1-b)/w[2], -w[1]/w[2] )
