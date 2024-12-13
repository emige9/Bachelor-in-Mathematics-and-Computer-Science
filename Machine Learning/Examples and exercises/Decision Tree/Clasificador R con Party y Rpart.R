
library(rpart)
library(party)
library(pROC)
summary(cu.summary)
str(cu.summary)
ind <- sample(117,117)
idt <- ind[1:20]
dtrain <- cu.summary[-idt,]
dtest <- cu.summary[idt,]
#rpart
fit <- rpart(Type ~ Mileage + Price + Country, dtrain)
summary(fit)
plot(fit, compress=TRUE)
text(fit, use.n=TRUE)

testPred <- predict(fit, newdata = dtest, type="class")

table(testPred, dtest$Type)

#party
fit2 <- ctree(Type ~ Mileage + Price + Country, dtrain)
summary(fit2)
plot(fit2, compress=TRUE)

testPred <- predict(fit2, newdata = dtest)
table(testPred, dtest$Type)

