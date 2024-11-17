import funciones_rsa
import funciones_aes
import socket_class
from Crypto.Random import get_random_bytes

k1 = funciones_aes.crear_AESKey()
print(k1)

clave_priv_Alice = funciones_rsa.cargar_RSAKey_Privada("kpriv_Alice.txt", "alice03102023")
clave_pub_Bob = funciones_rsa.cargar_RSAKey_Publica("kpub_Bob.txt")

k1_cifrado = funciones_rsa.cifrarRSA_OAEP(k1, clave_pub_Bob)
k1_firmado = funciones_rsa.firmarRSA_PSS(k1, clave_priv_Alice)

socketclient = socket_class.SOCKET_SIMPLE_TCP('127.0.0.1', 5551)
socketclient.conectar()
socketclient.enviar(k1_cifrado)
socketclient.enviar(k1_firmado)

nonceBob = socketclient.recibir()
cadena_cifrada = socketclient.recibir()
cadena_firmadaB = socketclient.recibir()

aes_descifrado = funciones_aes.iniciarAES_CTR_descifrado(k1, nonceBob)
texto_claro_Bob = funciones_aes.descifrarAES_CTR(aes_descifrado, cadena_cifrada)
esBob = funciones_rsa.comprobarRSA_PSS(texto_claro_Bob, cadena_firmadaB, clave_pub_Bob)
print("Cadena de caracteres recibida: ", texto_claro_Bob) 
print("Es bob: ", esBob)

cadenaA = "Hola Bob"
(aes_cifradoA, nonceA) = funciones_aes.iniciarAES_CTR_cifrado(k1)
cadena_cifradaA = funciones_aes.cifrarAES_CTR(aes_cifradoA, cadenaA.encode("utf-8"))
cadena_firmadaA = funciones_rsa.firmarRSA_PSS(cadenaA.encode("utf-8"), clave_priv_Alice)

socketclient.enviar(nonceA)
socketclient.enviar(cadena_cifradaA)
socketclient.enviar(cadena_firmadaA)

socketclient.cerrar()
