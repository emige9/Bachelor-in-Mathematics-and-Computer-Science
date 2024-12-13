# Laboratorio 1 - Cart Splitting
# Emilio Gómez Esteban

library(mboost)
library(rpart)
set.seed(123)

x1 <- c(2.771244718, 1.728571309, 3.678319846, 3.961043357, 2.999208922, 7.497545867, 9.00220326, 7.444542326, 10.12493903, 6.642287351)
x2 <- c(1.784783929, 1.169761413, 2.81281357, 2.61995032, 2.209014212, 3.162953546, 3.339047188, 0.476683375, 3.234550982, 3.319983761)
y <- c(0, 0, 0, 0, 0, 1, 1, 1, 1, 1)
datos <- data.frame(x1, x2, y)
print(datos)

# Apartado (a): El primer objetivo de la practica es teniendo en cuenta el algoritmo anterior realizar la primera
# división del árbol y encontrar la variable y el valor de dicha división. El algoritmo mostrara la
# variable y valor mínima. Ademas, tendrá un versión Verbose que mostrara para cada par
# variable/valor la cantidad que minimiza. El algoritmo continua de forma recursiva por cada región encontrada.

# Buscamos la condicion: x_i < s como nodo raíz del árbol
# Para ello, vamos a ver qué variable restringimos y a qué valor restringimos.

# Función para calcular la suma de la varianza en una región
calcular_varianza <- function(region){
  if(length(region) == 0){
    return(0)
  }
  mean_y <- mean(region$y)
  suma_varianza <- sum((region$y - mean_y)^2)
} 

# Función para encontrar la variable y el valor de división óptimos
encontrar_division_optima <- function(data){
  numColDatos <- 2
  mejor_variable_xi <- NULL
  mejor_valor_s <- NULL 
  mejor_varianza_total <- Inf
  
  for(variable in names(data)[1:numColDatos]){
    valores_unicos <- unique(data[,variable])
    
    for(valor in valores_unicos){
      region1 <- data[data[, variable] < valor, ]
      region2 <- data[data[, variable] >= valor, ]
      
      varianza_total <- calcular_varianza(region1) + calcular_varianza(region2)
      
      if(varianza_total < mejor_varianza_total){
        mejor_varianza_total <- varianza_total
        mejor_variable_xi <- variable
        mejor_valor_s <- valor
      }
    }
  }
  
  return(list(variable = mejor_variable_xi, valor = mejor_valor_s, varianza_total = mejor_varianza_total))
}

# Encontrar la primera división del árbol
primera_division <- encontrar_division_optima(datos)
cat("Variable óptima para la primera division: ", primera_division$variable, "\n")
cat("Valor óptimo para la primera división: ", primera_division$valor, "\n")
cat("Varianza total mínima:", primera_division$varianza_total, "\n")


# Función para construir un árbol de decisión de forma recursiva
construir_arbol_recursivo <- function(data, nivel){
  # Determinar la variable y el valor de división óptimos
  division_optima <- encontrar_division_optima(data)
  
  # Imprimir información del nodo actual
  cat(rep("  ", nivel), "Nivel", nivel, "\n")
  cat(rep("  ", nivel), "Nodo", nivel, ": ", division_optima$variable, "<", division_optima$valor, "\n")
  
  # Dividir el conjunto de datos en subconjuntos R1 y R2
  R1 <- data[data[, division_optima$variable] < division_optima$valor, ]
  R2 <- data[data[, division_optima$variable] >= division_optima$valor, ]
  
  # Determinar si se debe detener la recursión
  if (nivel >= 2) {
    cat(rep("  ", nivel), "Detenido en el nivel", nivel, "\n")
    return()
  }
  
  # Llamar recursivamente a la función para los subconjuntos
  if (nrow(R1) > 0) {
    cat(rep("  ", nivel), "Izquierda\n")
    construir_arbol_recursivo(R1, nivel + 1)
  }
  if (nrow(R2) > 0) {
    cat(rep("  ", nivel), "Derecha\n")
    construir_arbol_recursivo(R2, nivel + 1)
  }
}

# Llamar a la función recursiva para construir el árbol
cat("Árbol de decisión recursivo:\n")
construir_arbol_recursivo(datos, nivel = 0)



# Función para encontrar la variable y el valor de división óptimos con versión verbose
encontrar_division_optima_verbose <- function(data){
  numColDatos <- 2
  mejor_variable_xi <- NULL
  mejor_valor_s <- NULL 
  mejor_varianza_total <- Inf
  
  for(variable in names(data)[1:numColDatos]){
    valores_unicos <- unique(data[, variable])
    
    for(valor in valores_unicos){
      region1 <- data[data[, variable] < valor, ]
      region2 <- data[data[, variable] >= valor, ]
      
      varianza_total <- calcular_varianza(region1) + calcular_varianza(region2)
      
      # Imprimir la varianza total para cada par variable/valor (verbose)
      cat("Evaluando variable:", variable, "valor:", valor, "varianza total:", varianza_total, "\n")
      
      if(varianza_total < mejor_varianza_total){
        mejor_varianza_total <- varianza_total
        mejor_variable_xi <- variable
        mejor_valor_s <- valor
      }
    }
  }
  
  return(list(variable = mejor_variable_xi, valor = mejor_valor_s, varianza_total = mejor_varianza_total))
}

# Encontrar la primera división del árbol con versión verbose
primera_division_verbose <- encontrar_division_optima_verbose(datos)
cat("Variable óptima para la primera división: ", primera_division_verbose$variable, "\n")
cat("Valor óptimo para la primera división: ", primera_division_verbose$valor, "\n")
cat("Varianza total mínima:", primera_division_verbose$varianza_total, "\n")


#---------------------------------------------------------------
#---------------------------------------------------------------
#---------------------------------------------------------------


# Apartado (b): El segundo objetivo del algoritmo es determinar dos árboles T1 y T2 de dos niveles tal que T1
# explore la región derecha (es decir R2) con el algoritmo descrito anteriormente, mientra que T2
# explora la izquierda . Por ultimo, predecimos el conjunto completo de resultados usando ambos
# arboles. Nos quedaremos con el árbol que mejor predicción realice.

# Dividir el conjunto de datos en dos regiones R1 y R2
R1 <- datos[datos$x1 < primera_division$valor, ]
R2 <- datos[datos$x1 >= primera_division$valor, ]

# Construir árbol T1 para explotar R2
# Utilizamos la función encontrar_division_optima definida anteriormente
division_t1 <- encontrar_division_optima(R2)

# Construir árbol T2 para explotar R1
division_t2 <- encontrar_division_optima(R1)

# Evaluar precisión de T1 y T2
predicciones_T1 <- ifelse(R1[, division_t1$variable] < division_t1$valor, 0, 1)
accuracy_T1 <- mean(predicciones_T1 == R2$y)

predicciones_T2 <- ifelse(R2[, division_t2$variable] < division_t2$valor, 0, 1)
accuracy_T2 <- mean(predicciones_T2 == R1$y)

# Determinar el mejor árbol
mejor_arbol <- ifelse(accuracy_T1 > accuracy_T2, "T1", "T2")

# Imprimir resultados
ArbolT1 <- paste(
  "Arbol 1.\n",
  "Nivel 0\n",
  paste("Nodo 1(o raiz):", division_t1$variable, "<", division_t1$valor, "\n"),
  "Nivel 1\n",
  "Derecha\n",
  paste("Nodo 2:", division_t1$variable, "<", division_t1$valor, "\n"),
  "etiqueta nodo derecha - etiqueta nodo izquierda\n",
  "Izquierda\n",
  "etiqueta nodo\n"
)

cat(ArbolT1)

ArbolT2 <- paste(
  "Arbol 2.\n",
  "Nivel 0\n",
  paste("Nodo 1(o raíz):", division_t2$variable, "<", division_t2$valor, "\n"),
  "Nivel 1\n",
  "Derecha\n",
  "etiqueta nodo\n",
  "Izquierda\n",
  paste("Nodo 2:", division_t2$variable, "<", division_t2$valor, "\n"),
  "etiqueta nodo derecha - etiqueta nodo izquierda\n"
)

cat(ArbolT2)

cat("Predicción.\n")
cat(paste("T1: accuracy del árbol T1 =", accuracy_T1, "\n"))
cat(paste("T2: accuracy del árbol T2 =", accuracy_T2, "\n"))
cat(paste("Mejor árbol: ", mejor_arbol, "\n"))



#---------------------------------------------------------------
#---------------------------------------------------------------
#---------------------------------------------------------------

# Apartado (c): Explica el criterio que seguirías para finalizar el árbol. Pon un ejemplo.

# El criterio para finalizar un árbol de decisión se basa en condiciones que indican cuándo 
# no se debe seguir dividiendo más el conjunto de datos. Estos criterios están relacionados 
# con la profundidad del árbol o con las características del conjunto de datos. En tu implementación, 
# la recursión de construcción del árbol de decisión se detiene cuando se alcanza un nivel determinado 
# (en este caso, el nivel 2). Al finalizar el árbol, se busca que el modelo sea lo suficientemente preciso, 
# pero sin crear una estructura innecesariamente compleja. Esto se puede lograr de varias maneras:


# 1. Profundidad máxima: Se puede establecer un límite en la profundidad del árbol 
# (en nuestro código se limita a un máximo de 2 niveles). Esto previene el sobreajuste y mantiene el modelo más sencillo.

# 2. Tamaño mínimo de la región: Si una región es lo suficientemente pequeña (por ejemplo, contiene pocos puntos), 
# no tiene sentido dividirla más, ya que la división no podría mejorar significativamente la precisión del modelo.

# 3. Varianza pequeña: Si la varianza dentro de la región es suficientemente baja (es decir, los valores de y dentro 
# de esa región son similares), no es necesario seguir dividiendo la región.

# 4. Mejora en la precisión: Si no hay mejora en la precisión o la minimización de la varianza al dividir más, 
# se puede detener la recursión.


# Ejemplo de como finalizar el árbol: Condición de parada: Si en cualquier nivel encontramos que los subconjuntos 
# son lo suficientemente homogéneos (por ejemplo, todos los valores de y en un subconjunto son iguales) o si hemos 
# alcanzado el nivel máximo (nivel 2, como hemos implementado en nuestro código), entonces no realizamos más divisiones.




