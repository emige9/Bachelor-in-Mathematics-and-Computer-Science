
-- Ejercicio 1

create table MENSAJES(
codigo NUMBER(20) PRIMARY KEY,
texto VARCHAR2(200)
);

create table AUDITA_MENSAJES(
Quien VARCHAR2(200),
COMO VARCHAR(200),
CUANDO date
);

drop trigger info_mensajes;

create or replace TRIGGER INFO_MENSAJES
AFTER INSERT OR DELETE OR UPDATE on MENSAJES
BEGIN 
    IF INSERTING THEN
        INSERT INTO AUDITA_MENSAJES(quien,como,cuando)
        VALUES (USER,'INSERT',SYSDATE);
    ELSIF DELETING THEN
        INSERT INTO AUDITA_MENSAJES(quien,como,cuando)
        VALUES (USER,'DELETE',SYSDATE);
    ELSE
        INSERT INTO AUDITA_MENSAJES(quien,como,cuando)
        VALUES (USER,'UPDATE',SYSDATE);
    END IF;

END INFO_MENSAJES;
/



INSERT INTO MENSAJES VALUES (12345,'No tiene permiso para operar en este formulario');


--Ejercicio 2

alter table MENSAJES add (TIPO VARCHAR(20));

ALTER TABLE MENSAJES
ADD CONSTRAINT tipo_check CHECK (TIPO IN ('INFORMACION', 'RESTRICCION', 'ERROR', 'AVISO', 'AYUDA'));

INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (1, 'Este es un mensaje de información 1', 'INFORMACION');

INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (11, 'Este es un mensaje de información 11', 'INFORMACION');


INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (2, 'Este es un mensaje de restricción 2', 'RESTRICCION');

INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (22, 'Este es un mensaje de restricción 22', 'RESTRICCION');


INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (3, 'Este es un mensaje de restricción 3', 'ERROR');

INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (33, 'Este es un mensaje de restricción 33', 'ERROR');

INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (4, 'Este es un mensaje de restricción 4', 'AVISO');

INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (44, 'Este es un mensaje de restricción 44', 'AVISO');

INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (5, 'Este es un mensaje de restricción 5', 'AYUDA');

INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (57, 'Este es un mensaje de restricción 57', 'AYUDA');



CREATE TABLE MENSAJES_INFO(
TIPO VARCHAR2(30) PRIMARY KEY,
CUANTOS_MENSAJES NUMBER(2),
ULTIMO VARCHAR2(200)
);


CREATE OR REPLACE TRIGGER trg_after_insert_messages
AFTER INSERT ON MENSAJES
FOR EACH ROW
BEGIN
    UPDATE MENSAJES_Info
    SET Cuantos_Mensajes = Cuantos_Mensajes + 1,
        Ultimo = :NEW.Texto
    WHERE Tipo = :NEW.Tipo;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Si el tipo no existe en MENSAJES_Info, insertarlo
        INSERT INTO MENSAJES_Info (Tipo, Cuantos_Mensajes, Ultimo)
        VALUES (:NEW.Tipo, 1, :NEW.Texto);
END trg_after_insert_messages;
/

CREATE OR REPLACE TRIGGER trg_after_delete_messages
AFTER DELETE ON MENSAJES
FOR EACH ROW
BEGIN
    -- Decrementar Cuantos_Mensajes y poner a NULL Ultimo
    UPDATE MENSAJES_Info
    SET Cuantos_Mensajes = Cuantos_Mensajes - 1,
        Ultimo = NULL
    WHERE Tipo = :OLD.Tipo;
END trg_after_delete_messages;
/

CREATE OR REPLACE TRIGGER trg_after_update_messages
AFTER UPDATE OF Tipo ON MENSAJES
FOR EACH ROW
BEGIN
    -- Decrementar Cuantos_Mensajes y poner a NULL Ultimo para el tipo antiguo
    UPDATE MENSAJES_Info
    SET Cuantos_Mensajes = Cuantos_Mensajes - 1,
        Ultimo = NULL
    WHERE Tipo = :OLD.Tipo;
    
    -- Incrementar Cuantos_Mensajes y actualizar Ultimo para el tipo nuevo
    BEGIN
        UPDATE MENSAJES_Info
        SET Cuantos_Mensajes = Cuantos_Mensajes + 1,
            Ultimo = :NEW.Texto
        WHERE Tipo = :NEW.Tipo;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- Si el tipo no existe en MENSAJES_Info, insertarlo
            INSERT INTO MENSAJES_Info (Tipo, Cuantos_Mensajes, Ultimo)
            VALUES (:NEW.Tipo, 1, :NEW.Texto);
    END;
END trg_after_update_messages;
/


INSERT INTO MENSAJES_INFO(Tipo, cuantos_mensajes, ultimo)
values ('INFORMACION', 1, 'Este es un mensaje de información 1');

INSERT INTO MENSAJES (Codigo, Texto, Tipo)
VALUES (111, 'Este es un mensaje de información 111', 'INFORMACION');

DELETE FROM MENSAJES where codigo=111;

DELETE FROM MENSAJES where codigo=12345;


CREATE TABLE MENSAJES_TEXTO (
    Codigo NUMBER(20) PRIMARY KEY,
    Texto VARCHAR2(200)
);

CREATE TABLE MENSAJES_TIPO (
    Codigo NUMBER(20),
    Tipo VARCHAR2(30),
    PRIMARY KEY (Codigo, Tipo),
    FOREIGN KEY (Codigo) REFERENCES MENSAJES_TEXTO(Codigo)
);


-- Insertar datos en MENSAJES_TEXTO
INSERT INTO MENSAJES_TEXTO (Codigo, Texto)
SELECT Codigo, Texto FROM MENSAJES;

-- Insertar datos en MENSAJES_TIPO
INSERT INTO MENSAJES_TIPO (Codigo, Tipo)
SELECT Codigo, Tipo FROM MENSAJES;

drop table mensajes;

CREATE VIEW MENSAJES AS
SELECT MT.Codigo, MT.Texto, MTI.Tipo
FROM MENSAJES_TEXTO MT
JOIN MENSAJES_TIPO MTI ON MT.Codigo = MTI.Codigo;

SELECT * FROM MENSAJES;


CREATE TABLE MENSAJES_BORRADOS (
    Codigo NUMBER(20) PRIMARY KEY,
    Texto VARCHAR2(200)
);

CREATE OR REPLACE TRIGGER trg_after_delete_mensajes_texto
AFTER DELETE ON MENSAJES_TEXTO
FOR EACH ROW
BEGIN
    -- Insertar el mensaje borrado en MENSAJES_BORRADOS
    INSERT INTO MENSAJES_BORRADOS (Codigo, Texto)
    VALUES (:OLD.Codigo, :OLD.Texto);
END;
/



-- No ejecutado, revisar
CREATE OR REPLACE PROCEDURE purgar_mensajes_borrados IS
BEGIN
    DELETE FROM MENSAJES_BORRADOS
    WHERE SYSDATE - 2/(24*60) >= fecha_borrado;
    -- Esto eliminará los mensajes borrados hace más de 2 minutos
END;
/

BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
        job_name        => 'PURGE_DELETED_MESSAGES_JOB',
        job_type        => 'PLSQL_BLOCK',
        job_action      => 'BEGIN purgar_mensajes_borrados; END;',
        start_date      => SYSTIMESTAMP,
        repeat_interval => 'FREQ=MINUTELY; INTERVAL=2',
        enabled         => TRUE
    );
END;
/


