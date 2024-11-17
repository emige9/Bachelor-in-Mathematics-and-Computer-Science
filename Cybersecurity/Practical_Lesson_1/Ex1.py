from Crypto.Random import get_random_bytes
from Crypto.Cipher import DES, AES
from Crypto.Util.Padding import pad,unpad
from Crypto.Util import Counter

# Datos necesarios
key = get_random_bytes(16) # Clave aleatoria de 128 bits
IV = get_random_bytes(16)  # IV aleatorio de 64 bits
BLOCK_SIZE_AES = 16 # Bloque de 128 bits
data1 = "Hola amigos de la seguridad".encode("utf-8") # Datos a cifrar
print(data1)
data2 = "Hola amigas de la seguridad".encode("utf-8") # Datos a cifrar
print(data2)

# CIFRADO ##########################################################################

# Creamos un mecanismo de cifrado DES en modo CBC con un vector de inicialización IV
cipher1 = AES.new(key, AES.MODE_CBC, IV)
cipher2 = AES.new(key, AES.MODE_CBC, IV)

# Ciframos, haciendo que la variable “data” sea múltiplo del tamaño de bloque
ciphertext1 = cipher1.encrypt(pad(data1,BLOCK_SIZE_AES))
ciphertext2 = cipher2.encrypt(pad(data2,BLOCK_SIZE_AES))

# Mostramos el cifrado por pantalla en modo binario
print(ciphertext1)
print(ciphertext2)

# DESCIFRADO #######################################################################

# Creamos un mecanismo de (des)cifrado DES en modo CBC con una inicializacion IV
# Ambos, cifrado y descifrado, se crean de la misma forma
decipher_aes1 = AES.new(key, AES.MODE_CBC, IV)
decipher_aes2 = AES.new(key, AES.MODE_CBC, IV)

# Desciframos, eliminamos el padding, y recuperamos la cadena
new_data2 = unpad(decipher_aes2.decrypt(ciphertext2), BLOCK_SIZE_AES).decode("utf-8", "ignore")
new_data1 = unpad(decipher_aes1.decrypt(ciphertext1), BLOCK_SIZE_AES).decode("utf-8", "ignore")


# Imprimimos los datos descifrados
print(new_data2)
print(new_data1)
