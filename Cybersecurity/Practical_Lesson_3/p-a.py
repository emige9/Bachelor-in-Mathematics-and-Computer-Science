
from Crypto.Hash import SHA256, HMAC
import base64
import json
import sys
from socket_class import SOCKET_SIMPLE_TCP
import funciones_aes
from Crypto.Random import get_random_bytes

# Paso 0: Inicializacion
########################

# Lee clave KAT
KAT = open("KAT.bin", "rb").read()

# (A realizar por el alumno/a...)

# Paso 3) A->T: KAT(Alice, Na) en AES-GCM
#########################################

#Crear el socket de conexión con T (5551)
print("Creando conexión con T...")
socket = SOCKET_SIMPLE_TCP('127.0.0.1', 5551)
socket.conectar()

#Crea los campos del mensaje
t_n_origen = get_random_bytes(16)

# Codifica el contenido (los campos binarios en una cadena) y contruyo el mensaje JSON
msg_TE = []
msg_TE.append("Alice")
msg_TE.append(t_n_origen.hex())
json_ET = json.dumps(msg_TE)
print("A -> T (descifrado): " + json_ET)

# Cifra los datos con AES GCM
aes_engine = funciones_aes.iniciarAES_GCM(KAT)
cifrado, cifrado_mac, cifrado_nonce = funciones_aes.cifrarAES_GCM(aes_engine,json_ET.encode("utf-8"))

# Envia los datos
socket.enviar(cifrado)
socket.enviar(cifrado_mac)
socket.enviar(cifrado_nonce)

# Paso 4) T->A: KAT(K1, K2, Na) en AES-GCM
##########################################

cifradoA = socket.recibir()
cifrado_macA = socket.recibir()
cifrado_nonceA = socket.recibir()
datos_claro = funciones_aes.descifrarAES_GCM(KAT, cifrado_nonceA, cifradoA, cifrado_macA)

#Decodifica el contenido: Alice, Na
json_TA = datos_claro.decode("utf-8", "ignore")
print("T -> A (cifrado): " + json_TA)
msg_TA = json.loads(json_TA)

#Extraigo el contenido
K1, K2, t_na = msg_TA
K1 = bytearray.fromhex(K1)
K2 = bytearray.fromhex(K2)
t_na = bytearray.fromhex(t_na)

if(t_na == t_n_origen):
    print("El nonce es el mismo")
else:
    print("El nonce no es el mismo")
    exit

# Cerramos el socket entre A y T, no lo utilizaremos mas
socket.cerrar() 

# Paso 5) A->B: KAB(Nombre) en AES-CTR con HMAC
###############################################

# Crear el socket de conexion con B (5553)
print("Creando conexion con Bob...")
socket = SOCKET_SIMPLE_TCP('127.0.0.1', 5553)
socket.conectar()

# Creo el texto cifrado
nombre = "Nombre"
aes_cifrado, nonce_16_ini = funciones_aes.iniciarAES_CTR_cifrado(K1)
datos_cifrado = funciones_aes.cifrarAES_CTR(aes_cifrado, nombre.encode("utf-8"))

# Creo el hmac
hsend = HMAC.new(K2, msg = nombre.encode("utf-8"), digestmod=SHA256)
mac = hsend.digest()

# Codifica el contenido (los campos binarios en una cadena) y contruyo el mensaje JSON
mensaje = []
mensaje.append(datos_cifrado.hex())
mensaje.append(nonce_16_ini.hex())
mensaje.append(mac.hex())
json_paquete = json.dumps(mensaje)
socket.enviar(json_paquete.encode("utf-8"))



# Paso 6) B->A: KAB(Apellido) en AES-CTR con HMAC
#################################################

#Recibe el apellido
paquete = socket.recibir()

#Decodifica el contenido:
json_AB = paquete.decode("utf-8", "ignore")
print("B -> A (cifrado): " + json_AB)
msg_AB = json.loads(json_AB)

#Extraigo el contenido
datos_cifrado, nonce, mac = msg_AB
datos_cifrado = bytearray.fromhex(datos_cifrado)
nonce = bytearray.fromhex(nonce)

#Lo descifro
aes_descifrado = funciones_aes.iniciarAES_CTR_descifrado(K1, nonce)
datos_claro = funciones_aes.descifrarAES_CTR(aes_descifrado, datos_cifrado)
mensaje_claro_json = datos_claro.decode("utf-8")
print("B -> A (descifrado): " + mensaje_claro_json)

hmacA = HMAC.new(K2, digestmod=SHA256)
hmacA.update(mensaje_claro_json.encode("utf-8"))
try:
    hmacA.hexverify(mac)
    print("Mensaje correcto")
except ValueError:
    print("Mensaje manipulado")
    socket.cerrar()
    exit()


# Paso 7) A->B: KAB(END) en AES-CTR con HMAC
############################################

# Creo el texto cifrado
end = "END"
aes_cifrado, nonce_16_ini = funciones_aes.iniciarAES_CTR_cifrado(K1)
datos_cifrado = funciones_aes.cifrarAES_CTR(aes_cifrado, end.encode("utf-8"))

# Creo el hmac
hsend = HMAC.new(K2, msg = end.encode("utf-8"), digestmod=SHA256)
mac = hsend.digest()

# Codifica el contenido (los campos binarios en una cadena) y contruyo el mensaje JSON
mensaje = []
mensaje.append(datos_cifrado.hex())
mensaje.append(nonce_16_ini.hex())
mensaje.append(mac.hex())
json_paquete = json.dumps(mensaje)
socket.enviar(json_paquete.encode("utf-8"))
socket.cerrar()
