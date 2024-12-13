## EXAMEN APRENDIZAJE COMPUTACIONAL 11-12-2024

# NOMBRE: Emilio Gómez Esteban



# PROBLEMA 1 ------------------------------------------------------------------- 


# APARTADO 1
# Se va a explicar de forma general cómo se va a resovler el problema:

# El objetivo es modelar un tablero de 3x3, en el que cada celda viene representada
# por un atributo numerado del 1 al 9.

# Cada celda puede tomar uno de los siguientes valores: X, si la casilla está marcada por el jugador.
# O, si la casilla está marcada por el oponente y, Empty, si la casilla está vacía.


# El atributo de clasificación indica en qué celda el oponente debe colocar su ficha en la situación actual del juego.



# APARTADO 2

# A continuación, vamos a crear los Dataframes para entrenar y probar (test) el modelo

# Definir el conjunto de datos de entrenamiento (datos_entrenamiento)
datos_entrenamiento <- data.frame(
  C1 = c("-", "X", "O", "-", "X", "O", "X", "-", "X", "-", "-", "X", "-", "O", "-"),
  C2 = c("-", "O", "X", "-", "X", "-", "X", "O", "X", "X", "O", "-", "-", "X", "X"),
  C3 = c("-", "-", "-", "O", "X", "X", "O", "-", "-", "O", "X", "X", "-", "O", "O"),
  C4 = c("-", "-", "-", "X", "-", "X", "O", "-", "-", "-", "X", "O", "X", "-", "-"),
  C5 = c("-", "X", "-", "-", "X", "O", "-", "X", "-", "X", "-", "-", "O", "-", "X"),
  C6 = c("-", "-", "-", "O", "O", "-", "-", "O", "-", "-", "O", "X", "-", "X", "-"),
  C7 = c("-", "-", "-", "-", "-", "-", "-", "X", "X", "-", "-", "-", "O", "-", "X"),
  C8 = c("-", "-", "-", "-", "-", "-", "-", "-", "X", "-", "X", "O", "-", "X", "-"),
  C9 = c("-", "-", "-", "-", "-", "-", "-", "-", "-", "X", "O", "-", "X", "O", "-"),
  Clasificacion = as.factor(c(5, 3, 9, 6, 7, 9, 5, 1, 7, 4, 3, 9, 8, 5, 1)) # Movimientos óptimos
)

# Crear un conjunto de datos de prueba
datos_prueba <- data.frame(
  C1 = c("X", "O", "X", "-", "O"),
  C2 = c("O", "-", "X", "X", "X"),
  C3 = c("-", "-", "-", "O", "-"),
  C4 = c("-", "X", "-", "X", "O"),
  C5 = c("X", "O", "X", "O", "X"),
  C6 = c("-", "X", "O", "X", "-"),
  C7 = c("-", "-", "O", "X", "-"),
  C8 = c("-", "-", "X", "-", "-"),
  C9 = c("O", "-", "-", "O", "-"),
  Clasificacion = as.factor(c(9, 5, 6, 7, 8)) # Movimientos óptimos
)


# APARTADO 3

# Ahora vamos a resolver el problema mediante diferentes modelos y determinaremos cual es más adecuado.

library(rpart)    # Para árboles de decisión CART
library(nnet)     # Para perceptrón
library(e1071)    # Para SVM

# Modelo CART (árbol de decisión)
t_cart <- proc.time()
arbol_rpart <- rpart(Clasificacion ~ ., data = datos_entrenamiento, method = "class")
tiempo_cart <- proc.time() - t_cart

matrizconfusion<- table(predict(arbol_rpart, datos_prueba, type="class"), datos_prueba$Clasificacion)
accuracy_cart<-sum(diag(matrizconfusion))/sum(matrizconfusion)
cat("\nTiempo cart:", tiempo_cart[1],"\n")
cat("\nAccuracy cart:", accuracy_cart,"\n")

# Perceptrón
t_perceptron <- proc.time() 
modelo_perceptron <- nnet(Clasificacion ~ ., data = datos_entrenamiento, size = 2)
tiempo_perceptron <- proc.time() - t_perceptron 

# Realizar la predicción
predicciones <- predict(modelo_perceptron, datos_prueba, type = "class")

# Verificar las dimensiones
cat("Dimensiones de datos_prueba:", dim(datos_prueba), "\n")
cat("Dimensiones de las predicciones:", length(predicciones), "\n")

# Ajustar las longitudes si no coinciden
if (length(predicciones) != nrow(datos_prueba)) {
  # Si las longitudes no coinciden, ajustamos (por ejemplo, tomar las primeras filas)
  min_length <- min(length(predicciones), nrow(datos_prueba))
  predicciones <- predicciones[1:min_length]
  datos_prueba <- datos_prueba[1:min_length, ]
  cat("Se ajustaron las longitudes para que coincidan.\n")
}

# Ahora, calculamos la matriz de confusión y accuracy
matrizconfusion <- table(predicciones, datos_prueba$Clasificacion)
accuracy_perc <- sum(diag(matrizconfusion)) / sum(matrizconfusion)

cat("\nTiempo perceptron:", tiempo_perceptron[1], "\n")
cat("\nAccuracy perceptron:", accuracy_perc, "\n")

# SVM (máquinas de soporte vectorial)
t_svm <- proc.time()
modelo_svm <- svm(Clasificacion ~ ., data = datos_entrenamiento, kernel = "radial")
tiempo_svm <- proc.time() - t_svm 

matrizconfusionSVM<-table(predict(modelo_svm,datos_prueba), datos_prueba$Clasificacion , dnn=c("Prediction", "Actual"))
accuracySVMrad<- sum(diag(matrizconfusionSVM))/sum(matrizconfusionSVM)
cat("\nTiempo svm:", tiempo_svm[1],"\n")
cat("\nAccuracy SVM:", accuracySVMrad,"\n")

# Por tanto, por los datos obtenidos y comparados, determinamos que el modelo más adecuado es el perceptron.

# APARTADO 4

# Guardar el modelo entrenado en un archivo
save(modelo_perceptron, file = "modelo_perceptron.RData")

# Confirmar que el modelo ha sido guardado
cat("El modelo Perceptrón ha sido guardado como 'modelo_perceptron.RData'\n")


# APARTADO 5


# Programa para cargar el modelo y predecir
predecir_movimiento <- function(tablero) {
  # Cargar el modelo entrenado
  load("modelo_perceptron.RData")
  
  # Verificar el formato del tablero
  if (!is.data.frame(tablero) || ncol(tablero) != 9) {
    stop("El tablero debe ser un data.frame con exactamente 9 columnas.")
  }
  
  # Realizar la predicción
  prediccion <- predict(modelo_perceptron, tablero, type = "class")
  return(prediccion)
}

# Ejemplo de uso: Tablero vacío
tablero_vacio <- data.frame(
  C1 = "-", C2 = "-", C3 = "-", 
  C4 = "-", C5 = "-", C6 = "-", 
  C7 = "-", C8 = "-", C9 = "-"
)

# Predicción para el tablero vacío
movimiento <- predecir_movimiento(tablero_vacio)
cat("El movimiento recomendado es colocar la ficha en la celda:", movimiento, "\n")


# Función para predecir el movimiento con opción de modo verbose
predecir_movimiento <- function(tablero, verbose = FALSE) {
  # Cargar el modelo entrenado
  load("modelo_perceptron.RData")
  
  # Verificar el formato del tablero
  if (!is.data.frame(tablero) || ncol(tablero) != 9) {
    stop("El tablero debe ser un data.frame con exactamente 9 columnas.")
  }
  
  # Si verbose es TRUE, mostrar el estado del tablero
  if (verbose) {
    cat("Estado actual del tablero:\n")
    print(tablero)
  }
  
  # Realizar la predicción
  prediccion <- predict(modelo_perceptron, tablero, type = "class")
  
  # Mostrar el movimiento recomendado si verbose es TRUE
  if (verbose) {
    cat("Movimiento recomendado (clasificación):", prediccion, "\n")
  }
  
  return(prediccion)
}

# Ejemplo de uso con modo verbose activado
tablero_vacio <- data.frame(
  C1 = "-", C2 = "-", C3 = "-", 
  C4 = "-", C5 = "-", C6 = "-", 
  C7 = "-", C8 = "-", C9 = "-"
)

# Predicción con verbose activado
movimiento <- predecir_movimiento(tablero_vacio, verbose = TRUE)
cat("El movimiento recomendado es colocar la ficha en la celda:", movimiento, "\n")

