

from Crypto.Hash import SHA256, HMAC
import base64
import json
import sys
from socket_class import SOCKET_SIMPLE_TCP
import funciones_aes
from Crypto.Random import get_random_bytes

# Paso 0: Inicializacion
########################

# Lee clave KBT
KBT = open("KBT.bin", "rb").read()

# Paso 1) B->T: KBT(Bob, Nb) en AES-GCM
#######################################

# Crear el socket de conexion con T (5551)
print("Creando conexion con T...")
socket = SOCKET_SIMPLE_TCP('127.0.0.1', 5551)
socket.conectar()

# Crea los campos del mensaje
t_n_origen = get_random_bytes(16)

# Codifica el contenido (los campos binarios en una cadena) y contruyo el mensaje JSON
msg_TE = []
msg_TE.append("Bob")
msg_TE.append(t_n_origen.hex())
json_ET = json.dumps(msg_TE)
print("B -> T (descifrado): " + json_ET)

# Cifra los datos con AES GCM
aes_engine = funciones_aes.iniciarAES_GCM(KBT)
cifrado, cifrado_mac, cifrado_nonce = funciones_aes.cifrarAES_GCM(aes_engine,json_ET.encode("utf-8"))

# Envia los datos
socket.enviar(cifrado)
socket.enviar(cifrado_mac)
socket.enviar(cifrado_nonce)

# Paso 2) T->B: KBT(K1, K2, Nb) en AES-GCM
##########################################

cifradoB = socket.recibir()
cifrado_macB = socket.recibir()
cifrado_nonceB = socket.recibir()
datos_claro = funciones_aes.descifrarAES_GCM(KBT, cifrado_nonceB, cifradoB, cifrado_macB)

#Decodifica el contenido: Bob, Nb
json_TB = datos_claro.decode("utf-8", "ignore")
print("T -> B (cifrado): " + json_TB)
msg_TB = json.loads(json_TB)

#Extraigo el contenido
K1, K2, t_nb = msg_TB
K1 = bytearray.fromhex(K1)
K2 = bytearray.fromhex(K2)
t_nb = bytearray.fromhex(t_nb)

if(t_nb == t_n_origen):
    print("El nonce es el mismo")
else:
    print("El nonce no es el mismo")
    exit


# Cerramos el socket entre B y T, no lo utilizaremos mas
socket.cerrar() 

# Paso 5) A->B: KAB(Nombre) en AES-CTR con HMAC
###############################################

# Crear el socket de escucha de Alice (5551)
print("Esperando a Alice...")
socket= SOCKET_SIMPLE_TCP('127.0.0.1', 5553)
socket.escuchar()

#Recibe el nombre
paquete = socket.recibir()

#Decodifica el contenido:
json_AB = paquete.decode("utf-8", "ignore")
print("A -> B (cifrado): " + json_AB)
msg_AB = json.loads(json_AB)

#Extraigo el contenido
datos_cifrado, nonce, mac = msg_AB
datos_cifrado = bytearray.fromhex(datos_cifrado)
nonce = bytearray.fromhex(nonce)

#Lo descifro
aes_descifrado = funciones_aes.iniciarAES_CTR_descifrado(K1, nonce)
datos_claro = funciones_aes.descifrarAES_CTR(aes_descifrado, datos_cifrado)
mensaje_claro_json = datos_claro.decode("utf-8")
print("A -> B (descifrado): " + mensaje_claro_json)

hmacB = HMAC.new(K2, digestmod=SHA256)
hmacB.update(mensaje_claro_json.encode("utf-8"))
try:
    hmacB.hexverify(mac)
    print("Mensaje correcto")
except ValueError:
    print("Mensaje manipulado")
    socket.cerrar()
    exit()


# Paso 6) B->A: KAB(Apellido) en AES-CTR con HMAC
#################################################

# Creo el texto cifrado
apellido = "Apellido"
aes_cifrado, nonce_16_ini = funciones_aes.iniciarAES_CTR_cifrado(K1)
datos_cifrado = funciones_aes.cifrarAES_CTR(aes_cifrado, apellido.encode("utf-8"))

# Creo el hmac
hsend = HMAC.new(K2, msg = apellido.encode("utf-8"), digestmod=SHA256)
mac = hsend.digest()

# Codifica el contenido (los campos binarios en una cadena) y contruyo el mensaje JSON
mensaje = []
mensaje.append(datos_cifrado.hex())
mensaje.append(nonce_16_ini.hex())
mensaje.append(mac.hex())
json_paquete = json.dumps(mensaje)
socket.enviar(json_paquete.encode("utf-8"))


# Paso 7) A->B: KAB(END) en AES-CTR con HMAC
############################################

#Recibe el end
paquete = socket.recibir()

#Decodifica el contenido:
json_AB = paquete.decode("utf-8", "ignore")
print("A -> B (cifrado): " + json_AB)
msg_AB = json.loads(json_AB)

#Extraigo el contenido
datos_cifrado, nonce, mac = msg_AB
datos_cifrado = bytearray.fromhex(datos_cifrado)
nonce = bytearray.fromhex(nonce)

#Lo descifro
aes_descifrado = funciones_aes.iniciarAES_CTR_descifrado(K1, nonce)
datos_claro = funciones_aes.descifrarAES_CTR(aes_descifrado, datos_cifrado)
mensaje_claro_json = datos_claro.decode("utf-8")
print("A -> B (descifrado): " + mensaje_claro_json)

hmacB = HMAC.new(K2, digestmod=SHA256)
hmacB.update(mensaje_claro_json.encode("utf-8"))
try:
    hmacB.hexverify(mac)
    print("Mensaje correcto")
except ValueError:
    print("Mensaje manipulado")
    socket.cerrar()
    exit()

print("Tras enviar el end se cierran las conexiones")
socket.cerrar()