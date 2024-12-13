library(caret)
library(rpart.plot)
library(rattle)
library(RColorBrewer)

# Importar los datos
train <- read.csv("C:/Users/Usuario/Documents/R/train.csv")
test <- read.csv("C:/Users/Usuario/Documents/R/test.csv")

# Muestra la proporción de etiquetas en el conjunto de datos original
prop.table(table(train$label)) * 100

# Convierte la etiqueta en un factor
train$label <- factor(train$label)

## Validación Cruzada

# Tamaño del conjunto de datos: train: 42000, test: 28000
d_size <- nrow(train) # 42000
dtest_size <- ceiling(0.2 * d_size) # Tamaño del conjunto de prueba (20%)
samples <- sample(1:d_size, d_size, replace = FALSE) # Muestra aleatoria de índices
indexes <- samples[1:dtest_size] # Índices para el conjunto de prueba

dtrain <- train[-indexes,] # Conjunto de entrenamiento
dtest <- train[indexes,] # Conjunto de prueba


set.seed(1234)


#### RPART ---------------------------------------------------------------------
start_time <- Sys.time()
tree = rpart(label~., data = dtrain, method = "class")
end_time <- Sys.time()

matrizconfusiontree <- table(predict(tree, newdata = dtest, type = "class"), dtest$label)
accuracytree <- sum(diag(matrizconfusiontree)) / sum(matrizconfusiontree)

fancyRpartPlot(tree)

barplot(tree$variable.importance)


# Mostrar resultados
print(matrizconfusiontree)
cat("Accuracy del modelo:", accuracytree, "\n") 
cat("Tiempo de ejecución RPART:", end_time-start_time, "\n")
