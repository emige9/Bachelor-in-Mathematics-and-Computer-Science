def cifradoCesarAlfabetoInglesMAY(cadena, k):
    """
    Devuelve un cifrado Cesar tradicional (+3)
    """
    # Definir la nueva cadena resultado
    resultado = ''
    # Realizar el "cifrado", sabiendo que A = 65, Z = 90, a = 97, z = 122
    i = 0
    while i < len(cadena):
        # Recoge el caracter a cifrar
        ordenClaro = ord(cadena[i])
        #ordenCifrado = 0 No es necesario inicializarlo aqui, ya que lo hara dentro del if
        # Cambia el caracter a cifrar
        if (ordenClaro >= 65 and ordenClaro <= 90):
			# ==COMPLETAR para guardar en ordenCifrado el cifrado de ordenClaro==
            ordenCifrado = 0
            ordenCifrado = (((ordenClaro -65)+ k) % 26) + 65
            resultado = resultado + chr(ordenCifrado)
        elif(ordenClaro>=97 and ordenClaro<=122):
            ordenCifrado = 0
            ordenCifrado = (((ordenClaro -97)+ k) % 26) + 97
            resultado = resultado + chr(ordenCifrado)
        else:
            resultado = resultado + chr(ordenClaro)
        # Añade el caracter cifrado al resultado
        i = i + 1
    # devuelve el resultado
    return resultado

def descifradoCesarAlfabetoInglesMAY(cadena, k):
    """
    Devuelve un c<desifrado Cesar tradicional (+3)
    """
    # Definir la nueva cadena resultado
    resultado = ''
    # Realizar el "descifrado", sabiendo que A = 65, Z = 90, a = 97, z = 122
    i = 0
    while i < len(cadena):
        # Recoge el caracter a cifrar
        ordenClaro = ord(cadena[i])
        #ordenCifrado = 0 No es necesario inicializarlo aqui, ya que lo hara dentro del if
        # Cambia el caracter a cifrar
        if (ordenClaro >= 65 and ordenClaro <= 90):
			# ==COMPLETAR para guardar en ordenCifrado el cifrado de ordenClaro==
            ordenDescifrado = 0
            ordenDescifrado = (((ordenClaro - 65)- k) % 26) + 65
            resultado = resultado + chr(ordenDescifrado)
        elif(ordenClaro>=97 and ordenClaro<=122):
            ordenDescifrado = 0
            ordenDescifrado = (((ordenClaro -97) - k) % 26) + 97
            resultado = resultado + chr(ordenDescifrado)
        else:
            resultado = resultado + chr(ordenClaro)
        # Añade el caracter cifrado al resultado
        i = i + 1
    # devuelve el resultado
    return resultado

claroCESARMAY = '¿¿¿ !!! VENI VIDI VINCI AURIA veni vidi vinci auria'
print(claroCESARMAY)
cifradoCESARMAY = cifradoCesarAlfabetoInglesMAY(claroCESARMAY, 3) 
print(cifradoCESARMAY)
descifradoCESARMAY = descifradoCesarAlfabetoInglesMAY(cifradoCESARMAY, 3)
print(descifradoCESARMAY)