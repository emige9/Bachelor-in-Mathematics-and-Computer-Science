library (kernlab)
# Matriz H para el término cuadrático (debe ser positiva semidefinida)
H <- matrix(c(1, 0, 0, 1), nrow=2, byrow=TRUE)

# Vector c para el término lineal
c <- c(-2, -6)

# Matriz de restricciones A
A <- matrix(c(1, 1, -1, 2, 2, 1), nrow=3, byrow=TRUE)

# Vector b del lado derecho de las restricciones
b <- c(2, 2, 3)

# Límites inferiores y superiores
l <- rep(0, length(y))
u <- rep(1e6, length(y))

# Parámetro de tolerancia
r <- 0.1

# Ejecutar ipop para resolver el problema
resultado <- ipop(c = c, H = H, A = A, b = b, l = l, u = u, r = r)

# Imprimir el resultado
print(resultado)
