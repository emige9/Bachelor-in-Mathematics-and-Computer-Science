## EXAMEN APRENDIZAJE COMPUTACIONAL 11-12-2024

# NOMBRE: Emilio Gómez Esteban



# PROBLEMA 3


# Cargar las librerías necesarias
library(randomForest)
library(caret)   
library(dplyr)   

# Cargar el dataset
data <- read.csv("C:/Users/Usuario/Desktop/Universidad/Friday-WorkingHours-Afternoon-DDos.pcap_ISCX.csv", stringsAsFactors = TRUE)

# Eliminar datos faltantes
data <- na.omit(data)

# APARTADO 1

# Muestra representativa para evitar problemas de memoria
set.seed(200)
sample_size <- 10000 
data <- data[sample(nrow(data), sample_size), ]

# Dividir en conjunto de entrenamiento y prueba
d_size <- dim(data)[1]
dtest_size <- ceiling(0.2 * d_size)
samples <- sample(d_size, d_size, replace = FALSE)
indexes <- samples[1:dtest_size]
dtrain <- data[-indexes, ]
dtest <- data[indexes, ]

# Identificar columnas problemáticas
problematic_columns <- sapply(dtrain, function(x) any(is.na(x) | is.nan(x) | is.infinite(x)))
if (any(problematic_columns)) {
  print(paste("Columnas problemáticas:", names(dtrain)[problematic_columns]))
}

# Reemplazar valores problemáticos en columnas numéricas
for (col in names(dtrain)[problematic_columns]) {
  if (is.numeric(dtrain[[col]])) {
    dtrain[[col]][is.na(dtrain[[col]]) | is.nan(dtrain[[col]]) | is.infinite(dtrain[[col]])] <- median(dtrain[[col]], na.rm = TRUE)
  }
}

# Convertir columnas categóricas a factores
categorical_columns <- sapply(dtrain, is.character)
dtrain[categorical_columns] <- lapply(dtrain[categorical_columns], as.factor)
dtest[categorical_columns] <- lapply(dtest[categorical_columns], as.factor)

# Convertir la variable objetivo (Label) a factor
dtrain$Label <- as.factor(dtrain$Label)
dtest$Label <- as.factor(dtest$Label)

# Entrenar modelo Random Forest
library(randomForest)
set.seed(200)
rf <- randomForest(Label ~ ., data = dtrain, ntree = 50, mtry = 3, proximity = TRUE)

# Evaluar el modelo
predictions <- predict(rf, dtest)
conf_matrix <- table(Prediction = predictions, Actual = dtest$Label)
accuracy <- sum(diag(conf_matrix)) / sum(conf_matrix)

print(conf_matrix)
print(paste("Accuracy:", accuracy))


# APARTADO 2

# Definir la configuración de la validación cruzada: 10 pliegues (folds)
train_control <- trainControl(method = "cv", number = 10, 
                              savePredictions = "all", 
                              classProbs = TRUE) 

# Entrenar el modelo con validación cruzada
rf_model_cv <- train(Label ~ ., data = dtrain, method = "rf", 
                     trControl = train_control,
                     tuneGrid = expand.grid(mtry = c(2, 3, 4, 5, 6)), 
                     ntree = 5, 
                     importance = TRUE)

# Mostrar los mejores parámetros y el rendimiento del modelo
print(rf_model_cv$bestTune)

# Predicciones y evaluación de la validación cruzada
# Evaluar las predicciones realizadas en cada pliegue
cv_predictions <- rf_model_cv$pred

# Mostrar el rendimiento de la validación cruzada en términos de precisión, recall, F1, etc.
cv_metrics <- confusionMatrix(cv_predictions$pred, cv_predictions$obs)
print(cv_metrics)

# También puedes calcular otras métricas como el F1-score y la precisión promedio
cv_performance <- data.frame(
  Precision = posPredValue(cv_predictions$pred, cv_predictions$obs),
  Recall = sensitivity(cv_predictions$pred, cv_predictions$obs),
  F1_Score = (2 * posPredValue(cv_predictions$pred, cv_predictions$obs) * sensitivity(cv_predictions$pred, cv_predictions$obs)) / 
    (posPredValue(cv_predictions$pred, cv_predictions$obs) + sensitivity(cv_predictions$pred, cv_predictions$obs))
)

print(cv_performance)

# APARTADO 3


# Importancia de las variables

importance_values <- importance(rf_model_cv$finalModel)
print(importance_values)


# APARTADO 4

rf_model_original <- randomForest(Label ~ ., data = dtrain, ntree = 50, mtry = 3, proximity = TRUE)


importance_values <- importance(rf_model_original)

# Filtrar las variables cuyo MeanDecreaseGini > 7
selected_variables <- rownames(importance_values)[importance_values[, "MeanDecreaseGini"] > 7]

# Entrenar un nuevo modelo con solo las variables seleccionadas
dtrain_selected <- dtrain[, c(selected_variables, "Label")]
dtest_selected <- dtest[, c(selected_variables, "Label")]
rf_model_selected <- randomForest(Label ~ ., data = dtrain_selected, ntree = 50, mtry = 3, proximity = TRUE)

# Predicciones del modelo original
predictions_original <- predict(rf_model_original, dtest)
conf_matrix_original <- table(Prediction = predictions_original, Actual = dtest$Label)
accuracy_original <- sum(diag(conf_matrix_original)) / sum(conf_matrix_original)
print("Matriz de confusión del modelo original:")
print(conf_matrix_original)
print(paste("Accuracy del modelo original:", accuracy_original))

# Predicciones del modelo con variables seleccionadas
predictions_selected <- predict(rf_model_selected, dtest_selected)
conf_matrix_selected <- table(Prediction = predictions_selected, Actual = dtest$Label)
accuracy_selected <- sum(diag(conf_matrix_selected)) / sum(conf_matrix_selected)
print("Matriz de confusión del modelo con variables seleccionadas:")
print(conf_matrix_selected)
print(paste("Accuracy del modelo con variables seleccionadas:", accuracy_selected))

# Comparar los dos modelos
if (accuracy_selected > accuracy_original) {
  print("El modelo con variables seleccionadas es mejor.")
} else {
  print("El modelo original es mejor.")
}
