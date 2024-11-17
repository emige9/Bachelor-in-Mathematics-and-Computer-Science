from Crypto.Random import get_random_bytes
from Crypto.Cipher import DES, AES
from Crypto.Util.Padding import pad,unpad
from Crypto.Util import Counter

# Datos necesarios
key = get_random_bytes(16) # Clave aleatoria de 128 bits
BLOCK_SIZE_AES = 16 # Bloque de 128 bits

#Apartado ECB
data = "Hola Amigos de la seguridad".encode("utf-8") # Datos a cifrar
print(data)


cipher_ECB = AES.new(key, AES.MODE_ECB)
ciphertext_ECB = cipher_ECB.encrypt(pad(data,BLOCK_SIZE_AES))
print(ciphertext_ECB.decode("utf-8", "ignore"))
decipher_aes_ECB = AES.new(key, AES.MODE_ECB)
new_data_ECB = unpad(decipher_aes_ECB.decrypt(ciphertext_ECB), BLOCK_SIZE_AES).decode("utf-8", "ignore")
print(new_data_ECB)

print('\n')

#Apartado CTR
data = "Hola Amigos de la seguridad".encode("utf-8") # Datos a cifrar
print(data)

nonce = get_random_bytes(8)

cipher_CTR = AES.new(key, AES.MODE_CTR, nonce = nonce)
ciphertext_CTR = cipher_CTR.encrypt(pad(data,BLOCK_SIZE_AES))
print(ciphertext_CTR.decode("utf-8", "ignore"))
decipher_aes_CTR = AES.new(key, AES.MODE_CTR, nonce = nonce)
new_data_CTR = unpad(decipher_aes_CTR.decrypt(ciphertext_CTR), BLOCK_SIZE_AES).decode("utf-8", "ignore")
print(new_data_CTR)

print('\n')

#Apartado OFB
data = "Hola Amigos de la seguridad".encode("utf-8") # Datos a cifrar
print(data)

IV = get_random_bytes(16)

cipher_OFB = AES.new(key, AES.MODE_OFB, IV)
ciphertext_OFB = cipher_OFB.encrypt(pad(data,BLOCK_SIZE_AES))
print(ciphertext_OFB.decode("utf-8", "ignore"))
decipher_aes_OFB = AES.new(key, AES.MODE_OFB, IV)
new_data_OFB = unpad(decipher_aes_OFB.decrypt(ciphertext_OFB), BLOCK_SIZE_AES).decode("utf-8", "ignore")
print(new_data_OFB)

print('\n')


#Apartado CFB
data = "Hola Amigos de la seguridad".encode("utf-8") # Datos a cifrar
print(data)

IV = get_random_bytes(16)

cipher_CFB = AES.new(key, AES.MODE_CFB, IV)
ciphertext_CFB = cipher_CFB.encrypt(pad(data,BLOCK_SIZE_AES))
print(ciphertext_CFB.decode("utf-8", "ignore"))
decipher_aes_CFB = AES.new(key, AES.MODE_CFB, IV)
new_data_CFB = unpad(decipher_aes_CFB.decrypt(ciphertext_CFB), BLOCK_SIZE_AES).decode("utf-8", "ignore")
print(new_data_CFB)

print('\n')


#Apartado GCM
data = "Hola Amigos de la seguridad".encode("utf-8") # Datos a cifrar
print(data)

nonce = get_random_bytes(8) #podemos utilizar 16
mac_size = 16

cipher = AES.new(key, AES.MODE_GCM, nonce = nonce, mac_len = mac_size)
ciphertext, mac_cifrado = cipher.encrypt_and_digest(pad(data,BLOCK_SIZE_AES))
print(ciphertext.decode("utf-8", "ignore"))

try:
    decipher_aes = AES.new(key, AES.MODE_GCM, nonce = nonce)
    new_data = unpad(decipher_aes.decrypt_and_verify(ciphertext, mac_cifrado), BLOCK_SIZE_AES).decode("utf-8", "ignore")
    print(new_data)

except (ValueError, KeyError) as e:
    print("ERROR de Integridad")

print('\n')