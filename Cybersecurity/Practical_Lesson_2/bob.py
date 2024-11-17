import funciones_rsa
import funciones_aes
import socket_class

clave_priv_Bob = funciones_rsa.cargar_RSAKey_Privada("kpriv_Bob.txt", "bob03102023")
clave_pub_Alice = funciones_rsa.cargar_RSAKey_Publica("kpub_Alice.txt")

socketserver = socket_class.SOCKET_SIMPLE_TCP('127.0.0.1', 5551)
socketserver.escuchar()
k1_cifrado = socketserver.recibir()
k1_firmado = socketserver.recibir()

mensaje_des_k1 = funciones_rsa.descifrarRSA_OAEP(k1_cifrado, clave_priv_Bob)
print(mensaje_des_k1)
es_Alice = funciones_rsa.comprobarRSA_PSS(mensaje_des_k1, k1_firmado, clave_pub_Alice)
print("Es Alice = ", es_Alice)

cadena = "Hola Alice"
(aes_cifrado, nonce) = funciones_aes.iniciarAES_CTR_cifrado(mensaje_des_k1)
cadena_cifrada = funciones_aes.cifrarAES_CTR(aes_cifrado, cadena.encode("utf-8"))
cadena_firmada = funciones_rsa.firmarRSA_PSS(cadena.encode("utf-8"), clave_priv_Bob)

socketserver.enviar(nonce)
socketserver.enviar(cadena_cifrada)
socketserver.enviar(cadena_firmada)

nonceAlice = socketserver.recibir()
cadena_cifradaA = socketserver.recibir()
cadena_firmadaA = socketserver.recibir()

aes_descifradoA = funciones_aes.iniciarAES_CTR_descifrado(mensaje_des_k1, nonceAlice)
texto_claro_Alice = funciones_aes.descifrarAES_CTR(aes_descifradoA, cadena_cifradaA)
esAlice = funciones_rsa.comprobarRSA_PSS(texto_claro_Alice, cadena_firmadaA, clave_pub_Alice)
print("Cadena de caracteres recibida: ", texto_claro_Alice)
print("Es Alice: ", esAlice)

socketserver.cerrar()


