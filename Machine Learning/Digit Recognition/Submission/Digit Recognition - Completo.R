# Cargar las librerías necesarias
library(randomForest) 
library(kernlab)
library(caret)
library(rpart.plot)
library(rattle)
library(RColorBrewer)
library(adabag) 
# ----------------------------------------------
# Cargar los datos
train <- read.csv("train.csv") # Datos de entrenamiento
test <- read.csv("test.csv")   # Datos de prueba (sin etiquetas)

# ----------------------------------------------
# Inspección de los datos
# Calcular la proporción de cada dígito en el conjunto de entrenamiento (para detectar desequilibrios)
prop.table(table(train$label)) * 100

# Convertir la columna de etiquetas en factor para usar modelos categóricos
train$label <- factor(train$label)

# ----------------------------------------------
# División de los datos en entrenamiento y prueba
d_size <- nrow(train)               # Número total de ejemplos en el conjunto de entrenamiento
dtest_size <- ceiling(0.2 * d_size) # 20% de los datos para prueba
samples <- sample(1:d_size, d_size, replace = FALSE) # Índices aleatorios sin reemplazo
indexes <- samples[1:dtest_size]    # Seleccionar los índices para el conjunto de prueba

# Crear los conjuntos de datos de entrenamiento y prueba
dtrain <- train[-indexes,] # 80% para entrenamiento
dtest <- train[indexes,]   # 20% para validación/prueba

################################################### RPART ##################################
start_time <- Sys.time()
tree = rpart(label~., data = dtrain, method = "class")
end_time <- Sys.time()

matrizconfusiontree <- table(predict(tree, newdata = dtest, type = "class"), dtest$label)
accuracytree <- sum(diag(matrizconfusiontree)) / sum(matrizconfusiontree)

fancyRpartPlot(tree)

png("fancy_tree.png", width = 2000, height = 1500, res = 300)
fancyRpartPlot(tree)
dev.off()

barplot(tree$variable.importance)


# Mostrar resultados
print(matrizconfusiontree)
cat("Accuracy del modelo:", accuracytree, "\n") 
cat("Tiempo de ejecución RPART:", end_time-start_time, "\n")

# ----------------------------------------------
# Resultados :
# Accuracy 0.6333, Tiempo 53.2402 sec

################################################################################################

############################# RANDOM FOREST ####################################################
# Entrenar y evaluar Random Forest con diferentes números de árboles
# Random Forest con 5 árboles
start_time <- Sys.time() # Registrar tiempo inicial
rf.model <- randomForest(label ~ ., data = dtrain, ntree = 5) # Entrenar el modelo
end_time <- Sys.time()   # Registrar tiempo final
save(rf.model, file="rf5.rda") # Guardar el modelo entrenado en un archivo
end_time - start_time     # Calcular tiempo de entrenamiento

# Predecir etiquetas en el conjunto de prueba
rf.predict <- predict(rf.model, dtest)
# Crear matriz de confusión para evaluar el modelo
matrizconfusionRF <- table(rf.predict, dtest$label)
# Calcular precisión (accuracy)
accuracyRF <- sum(diag(matrizconfusionRF)) / sum(matrizconfusionRF)
accuracyRF # Mostrar precisión

# ----------------------------------------------
# Random Forest con 50 árboles
start_time <- Sys.time() # Tiempo inicial
rf.model <- randomForest(label ~ ., data = dtrain, ntree = 50) # Entrenar con 50 árboles
end_time <- Sys.time()   # Tiempo final
save(rf.model, file="rf50.rda") # Guardar modelo
end_time - start_time     # Calcular tiempo de entrenamiento

# Predecir etiquetas y evaluar precisión
rf.predict <- predict(rf.model, dtest)
matrizconfusionRF <- table(rf.predict, dtest$label)
accuracyRF <- sum(diag(matrizconfusionRF)) / sum(matrizconfusionRF)
accuracyRF # Mostrar precisión

# ----------------------------------------------
# Random Forest con 100 árboles
start_time <- Sys.time() # Tiempo inicial
rf.model <- randomForest(label ~ ., data = dtrain, ntree = 100) # Entrenar con 100 árboles
end_time <- Sys.time()   # Tiempo final
save(rf.model, file="rf100.rda") # Guardar modelo
end_time - start_time     # Calcular tiempo de entrenamiento

# Predecir etiquetas y evaluar precisión
rf.predict <- predict(rf.model, dtest)
matrizconfusionRF <- table(rf.predict, dtest$label)
accuracyRF <- sum(diag(matrizconfusionRF)) / sum(matrizconfusionRF)
accuracyRF # Mostrar precisión

# ----------------------------------------------
# Resultados :
# 5 arboles - Accuracy 0.9104, Tiempo 24.9980 sec
# 50 arboles - Accuracy 0.9632, Tiempo 4.4706 min
# 100 arboles - Accuracy 0.9663, Tiempo 16.855 min 

######################################################################################################

############################## SVM ###################################################

start_time <- Sys.time()

# KSVM: Kernel = polydot
filter <- ksvm(label ~ ., data = dtrain, kernel = "polydot", kpar = list(degree = 3), cross = 3)

end_time <- Sys.time()

matrizconfusionKSVMpol<- table(predict(filter, dtest), dtest$label)
accuracyksvmpol <- sum(diag(matrizconfusionKSVMpol)) / sum(matrizconfusionKSVMpol)

# Mostrar resultados
print(matrizconfusionKSVMpol)
cat("Accuracy del modelo:", accuracyksvmpol, "\n") 
cat("Tiempo de ejecución SVM:", end_time-start_time, "\n")

# Resultados :
# Accuracy 0.9748, Tiempo 35.1554 min
#####################################################################################
################################# BAGGING #####################################
start_time <- Sys.time()
Modelo_AdaBag  <- bagging(label~., 
                          data=dtrain, 
                          na.action = na.omit,
                          mfinal=9,
                          control=rpart.control(cp = 0.001, minsplit=7))
end_time <- Sys.time()

matrizconfusionBag <- table(dtest[, "label"],predict(Modelo_AdaBag, newdata = dtest, type = "class")$class)
accuracyBag <- sum(diag(matrizconfusionBag)) / sum(matrizconfusionBag)

print(matrizconfusionBag)

cat("Accuracy del modelo:", accuracyBag, "\n")  
cat("Tiempo de ejecución Bagging:", end_time-start_time, "\n") 
# Resultados :
# Accuracy 0.8594, Tiempo  29.1157 min
