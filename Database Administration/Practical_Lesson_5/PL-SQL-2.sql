-- Ejercicio 1:

create table tb_objetos(
NOMBRE VARCHAR2(500),
CODIGO NUMBER,
FECHA_CREACION DATE,
FECHA_MODIFICACION DATE,
TIPO VARCHAR2(50),
ESQUEMA_ORIGINAL VARCHAR2(50));

SELECT OBJECT_NAME, OBJECT_ID, CREATED, LAST_DDL_TIME, OBJECT_TYPE, OWNER FROM ALL_OBJECTS where owner like 'UBD%' OR  owner = 'DOCENCIA';


DECLARE 
CURSOR CURSOR_RECORRE IS SELECT OBJECT_NAME, OBJECT_ID, CREATED, LAST_DDL_TIME, OBJECT_TYPE ,OWNER
FROM ALL_OBJECTS 
where owner like 'UBD%' OR  owner not like 'PUBLIC';
BEGIN 
FOR FILA IN CURSOR_RECORRE LOOP 
    INSERT INTO TB_OBJETOS VALUES FILA;
END LOOP;
END;
/


-- Ejercicio 2:

create table tb_estilo(
tipo_objeto varchar2(50), 
prefijo varchar2(50));

INSERT INTO TB_ESTILO (TIPO_OBJETO, PREFIJO) VALUES ('PROCEDURE', 'PR_');

-- Ejercicio 3

ALTER TABLE TB_OBJETOS
ADD (ESTADO VARCHAR2(10),
     NOMBRE_CORRECTO VARCHAR2(128));
     
     
CREATE OR REPLACE PROCEDURE PR_COMPROBAR(ESQUEMA IN VARCHAR2) AS
    v_esquema TB_OBJETOS.ESQUEMA_ORIGINAL%TYPE;
    v_prefijo TB_ESTILO.PREFIJO%TYPE;
    v_nuevo_nombre VARCHAR2(128);
BEGIN
    FOR obj IN (SELECT * FROM TB_OBJETOS WHERE ESQUEMA_ORIGINAL = NVL(ESQUEMA, ESQUEMA_ORIGINAL)) LOOP
        -- Obtener el prefijo correspondiente al tipo de objeto desde TB_ESTILO
        SELECT PREFIJO INTO v_prefijo FROM TB_ESTILO WHERE TIPO_OBJETO = obj.TIPO;

        -- Si se encuentra un prefijo válido, se actualiza el estado y el nuevo nombre
        IF v_prefijo IS NOT NULL THEN
            v_nuevo_nombre := SUBSTR(v_prefijo || obj.NOMBRE, 1, 128);
            IF LENGTH(v_nuevo_nombre) > 128 THEN
                v_nuevo_nombre := SUBSTR(v_nuevo_nombre, 1, 128);
            END IF;

            UPDATE TB_OBJETOS 
            SET ESTADO = 'CORRECTO',
                NOMBRE_CORRECTO = v_nuevo_nombre
            WHERE NOMBRE = obj.NOMBRE;
        ELSE
            -- Si no se encuentra un prefijo válido, se marca como incorrecto
            v_nuevo_nombre := obj.NOMBRE;
            UPDATE TB_OBJETOS 
            SET ESTADO = 'INCORRECTO',
                NOMBRE_CORRECTO = v_nuevo_nombre
            WHERE NOMBRE = obj.NOMBRE;
        END IF;
    END LOOP;
END;
/



