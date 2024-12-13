## EXAMEN APRENDIZAJE COMPUTACIONAL 11-12-2024

# NOMBRE: Emilio Gómez Esteban



# PROBLEMA 2 -------------------------------------------------------------------


# APARTADO 2

# Importar las librerías necesarias
library(kernlab)

# Crear el dataframe con los datos de las setas
dataA <- data.frame(
  cap_shape = c("convex", "convex", "bell", "convex", "convex", "bell", "convex"),
  cap_color = c("brown", "yellow", "white", "white", "yellow", "white", "white"),
  gill_size = c("narrow", "broad", "broad", "narrow", "broad", "broad", "narrow"),
  gill_color = c("black", "black", "brown", "brown", "brown", "brown", "pink"),
  class = c("poisonous", "edible", "edible", "poisonous", "edible", "edible", "poisonous")
)

# Convertir variables categóricas a numéricas
dataA$cap_shape <- as.numeric(factor(dataA$cap_shape))
dataA$cap_color <- as.numeric(factor(dataA$cap_color))
dataA$gill_size <- as.numeric(factor(dataA$gill_size))
dataA$gill_color <- as.numeric(factor(dataA$gill_color))
dataA$class <- as.numeric(factor(dataA$class))


# Crear el modelo SVM
model <- ksvm(class ~ ., data = dataA, type = "C-svc", kernel = "vanilladot")

# Vectores de soporte: no se piden explicitamente, pero nos ayuda a calcular el ancho de banda
vsModel <- dataA[alphaindex(model)[[1]], 1:2]

w <- colSums(coef(model)[[1]] * as.matrix(vsModel))
cat("Vector de pesos normal al hiperplano w:", w, "\n")

ancho_del_canal <- 2 / sqrt(sum(w^2))
cat("Ancho del canal:", ancho_del_canal, "\n") #null

b <- -b(model)
cat("b:", b, "\n")


ecuacion_hiperplano <- paste("0 = ", w[1], "*x1 + ", w[2], "*x2 + ", b)
cat("Ecuación del hiperplano:\n", ecuacion_hiperplano, "\n")

ecuacion_plano_positivo <- paste("1 = ", w[1], "*x1 + ", w[2], "*x2 + ", b)
cat("Ecuación del hiperplano:\n", ecuacion_plano_positivo, "\n")

ecuacion_plano_negativo <- paste("-1 = ", w[1], "*x1 + ", w[2], "*x2 + ", b)
cat("Ecuación del hiperplano:\n", ecuacion_plano_negativo, "\n")

# Realizar la predicción para una seta con las características dadas
nueva_seta <- data.frame(
  cap_shape = "bell",
  cap_color = "white",
  gill_size = "broad",
  gill_color = "black"
)

# Convertir variables categóricas a numéricas
nueva_seta$cap_shape <- as.numeric(factor(nueva_seta$cap_shape))
nueva_seta$cap_color <- as.numeric(factor(nueva_seta$cap_color))
nueva_seta$gill_size <- as.numeric(factor(nueva_seta$gill_size))
nueva_seta$gill_color <- as.numeric(factor(nueva_seta$gill_color))

# Realizar la predicción
prediccion <- predict(model, newdata = nueva_seta)
cat("Clasificación de la seta:", ifelse(prediccion > 0, "edible", "poisonous"), "\n")

# La seta es clasificada como "edible" (comestible)
