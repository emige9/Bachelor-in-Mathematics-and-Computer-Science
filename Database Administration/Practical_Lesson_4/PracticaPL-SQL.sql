SET SERVEROUTPUT ON SIZE;

-- Ejercicio 1:

DECLARE
    v_table_name USER_TABLES.TABLE_NAME%TYPE;
    v_schema_name USER_TABLES.TABLESPACE_NAME%TYPE;
    
    CURSOR table_cursor IS
        SELECT TABLE_NAME, TABLESPACE_NAME
        FROM USER_TABLES;
BEGIN
    OPEN table_cursor;
    
    LOOP
        FETCH table_cursor INTO v_table_name, v_schema_name;
        EXIT WHEN table_cursor%NOTFOUND;
        
        DBMS_OUTPUT.PUT_LINE('La tabla ' || v_table_name || ' pertenece al esquema ' || v_schema_name);
    END LOOP;
    
    CLOSE table_cursor;
END;
/

-------------------------------------------------------------------
-- Ejercicio 2:

DECLARE
    v_table_name ALL_TABLES.TABLE_NAME%TYPE;
    v_schema_name ALL_TABLES.OWNER%TYPE;
    
    CURSOR table_cursor IS
        SELECT TABLE_NAME, OWNER
        FROM ALL_TABLES;
BEGIN
    OPEN table_cursor;
    
    LOOP
        FETCH table_cursor INTO v_table_name, v_schema_name;
        EXIT WHEN table_cursor%NOTFOUND;
        
        DBMS_OUTPUT.PUT_LINE('La tabla ' || v_table_name || ' pertenece al esquema ' || v_schema_name);
    END LOOP;
    
    CLOSE table_cursor;
END;
/


-------------------------------------------------------------------
-- Ejercicio 3:
-- Para obtener las tablas a las que tienes permiso en otros esquemas de usuario, 
-- necesitaremos usar la vista ALL_TABLES en lugar de USER_TABLES, ya que 
-- USER_TABLES solo muestra las tablas en tu propio esquema. 

--Este script utiliza la vista ALL_TABLES y filtra las tablas cuyo propietario no sea el mismo que el usuario actual.
-- Por tanto, también hemos tenido que filtrar para obtener tablas de otros esquemas,
-- cambiando la acción y especificando con el elemento OWNER

-------------------------------------------------------------------
-- Ejercicio 4:

DECLARE
    v_table_name ALL_TABLES.TABLE_NAME%TYPE;
    v_schema_name ALL_TABLES.OWNER%TYPE;
    
    CURSOR table_cursor IS
        SELECT TABLE_NAME, OWNER
        FROM ALL_TABLES
        WHERE OWNER=USER;
BEGIN
    OPEN table_cursor;
    
    LOOP
        FETCH table_cursor INTO v_table_name, v_schema_name;
        EXIT WHEN table_cursor%NOTFOUND;
        
        DBMS_OUTPUT.PUT_LINE('La tabla ' || v_table_name || ' pertenece al esquema ' || v_schema_name);
    END LOOP;
    
    CLOSE table_cursor;
END;
/

/*La diferencia principal entre los dos scripts radica en el origen de los datos:

Primer Script (Utilizando USER_TABLES):

Este script utiliza la vista USER_TABLES, que proporciona información solo sobre las tablas del esquema del usuario que ejecuta el script.
No incluye tablas de otros esquemas, incluso si el usuario actual tiene permisos para acceder a esas tablas.

Segundo Script (Utilizando ALL_TABLES):

Este script utiliza la vista ALL_TABLES, que proporciona información sobre todas las tablas accesibles para el usuario actual.
Incluye tanto las tablas del esquema del usuario como las tablas de otros esquemas, siempre que el usuario tenga los permisos adecuados para acceder a esas tablas.


Por lo tanto, el primer script es más limitado en términos de alcance, ya que solo muestra las tablas dentro del esquema del usuario,
mientras que el segundo script proporciona una visión más amplia, mostrando todas las tablas a las que el usuario tiene acceso, 
independientemente del propietario de la tabla.*/

-------------------------------------------------------------------
-- Ejercicio 5:

CREATE OR REPLACE PROCEDURE RECORRE_TABLAS(P_MODE IN NUMBER DEFAULT NULL) IS
    v_table_name ALL_TABLES.TABLE_NAME%TYPE;
    v_schema_name ALL_TABLES.OWNER%TYPE;
    v_owner_condition VARCHAR2(100);
    
    CURSOR table_cursor IS
        SELECT TABLE_NAME, OWNER
        FROM ALL_TABLES
        WHERE NVL(DECODE(P_MODE, 0, NULL, OWNER), USER) = USER;
BEGIN
    IF P_MODE IS NULL THEN
        DBMS_OUTPUT.PUT_LINE('Este procedimiento lista todas las tablas a las que tienes permiso.');
        DBMS_OUTPUT.PUT_LINE('Para listar solo las propias del usuario, proporciona 0 como valor de P_MODE.');
        RETURN;
    END IF;
    
    IF P_MODE = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Listando todas las tablas a las que tienes permiso:');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Listando tus propias tablas:');
    END IF;
    
    OPEN table_cursor;
    
    LOOP
        FETCH table_cursor INTO v_table_name, v_schema_name;
        EXIT WHEN table_cursor%NOTFOUND;
        
        DBMS_OUTPUT.PUT_LINE('La tabla ' || v_table_name || ' pertenece al esquema ' || v_schema_name);
    END LOOP;
    
    CLOSE table_cursor;
END RECORRE_TABLAS;
/

BEGIN
    RECORRE_TABLAS(0);
END;
/

BEGIN
    RECORRE_TABLAS(NULL);
END;
/

BEGIN
    RECORRE_TABLAS(1);
END;
/
