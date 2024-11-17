import funciones_rsa

claves_Alice = funciones_rsa.crear_RSAKey()

funciones_rsa.guardar_RSAKey_Publica("kpub_Alice.txt", claves_Alice)
funciones_rsa.guardar_RSAKey_Privada("kpriv_Alice.txt", claves_Alice, "alice03102023")

claves_Bob = funciones_rsa.crear_RSAKey()

funciones_rsa.guardar_RSAKey_Publica("kpub_Bob.txt", claves_Bob)
funciones_rsa.guardar_RSAKey_Privada("kpriv_Bob.txt", claves_Bob, "bob03102023")