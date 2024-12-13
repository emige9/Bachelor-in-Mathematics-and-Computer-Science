library (kernlab)
# Definir datos de entrada (XOR)
x <- matrix(c(0, 0, 1, 1, 1, 0, 0, 1), ncol=2, byrow=TRUE)
y <- c(-1, -1, 1, 1)  # Etiquetas
# Calcular matriz H
H <- (y %*% t(y)) * (x %*% t(x))

# Vector c
c <- rep(-1, length(y))

# Restricciones
A <- t(y)
b <- 0
l <- rep(0, length(y))
u <- rep(1e6, length(y))

# Parámetro de tolerancia para el optimizador
r <- 0.1
# Resolver el problema de optimización con ipop
resultado <- ipop(c = c, H = H, A = A, b = b, l = l, u = u, r = r)

# Extraer los multiplicadores de Lagrange (alfa)
alphas <- primal(resultado)

# Imprimir los resultados
print(alphas)
# Calcular el vector de pesos w
w <- t(alphas * y) %*% x

# Calcular el sesgo b usando los vectores de soporte
support_vectors <- which(alphas > 1e-5)  # Filtrar valores de alphas cercanos a cero
b <- mean(y[support_vectors] - x[support_vectors, ] %*% w)

# Imprimir w y b
print(w)
print(b)
