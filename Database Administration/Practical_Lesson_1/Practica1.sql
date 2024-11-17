
-- 3.
select * from dba_tablespaces;

create TABLESPACE ts_LIFEFIT datafile 'lifefit.dbf' size 10M autoextend on;

-- 4.
create profile perf_administrativo limit 
FAILED_LOGIN_ATTEMPTS 3 
IDLE_TIME 5;

-- 5.
create profile perf_usuario limit 
SESSIONS_PER_USER 4 
PASSWORD_LIFE_TIME 30;

-- 6.
show parameter resource_limit;  
/*Comentarios: Después de ejecutar este comando vemos que está activo el parámetro
luego nuestras limitaciones serán efectivas. Y si por un casual no estuviera a TRUE, 
se podría usar el comando alter system set resource_limit = true; */

-- 7.
create role r_administrador_super;
grant connect, create table to r_administrador_super;

-- 8.
create user usuario1 identified by usuario profile perf_administrativo 
quota 1M on ts_lifefit default tablespace ts_lifefit;
grant r_administrador_super to usuario1;
create user usuario2 identified by usuario profile perf_administrativo 
quota 1M on ts_lifefit default tablespace ts_lifefit;
grant r_administrador_super to usuario2;

select * from dba_users;
select * from dba_users where username like 'U%';

-- 9.
create table usuario1.TABLA2 (  CODIGO NUMBER   );
create table usuario2.TABLA2 (  CODIGO NUMBER   );

--10.
CREATE OR REPLACE PROCEDURE USUARIO1.PR_INSERTA_TABLA2 (
                                P_CODIGO IN NUMBER) AS
 BEGIN
      INSERT INTO TABLA2 VALUES (P_CODIGO);
 END PR_INSERTA_TABLA2;
 /
 
 -- 18.
 CREATE OR REPLACE PROCEDURE usuario1.PR_CREA_TABLA (
  P_TABLA IN VARCHAR2, P_ATRIBUTO IN VARCHAR2) AS
BEGIN
  EXECUTE IMMEDIATE 'CREATE TABLE '||P_TABLA||'('||P_ATRIBUTO||' NUMBER(9))';
 END PR_CREA_TABLA;
/

-- 20.
grant create table to usuario1;

-- 22.
select * from DBA_USERS_WITH_DEFPWD;
--Solo encontramos una contraseña por defecto
/* Hay tantas cuentas porque son de tipo administrativas o no administrativas, 
necesarias para el sistema oracle para poder funcionar correctamente.
No, no es insegura, oracle crea varias cuentas predefinidas con una contraseña aleatoria
o crea las cuentas bloqueadas y con expiracion, el administrador de la base de datos
es el encargado de realizar los cambios necesarios para securizar estos usuarios despues de 
la instalación*/

-- 23.
select * from DBA_PROFILES;

-- a.
select resource_name from dba_profiles where profile = 'DEFAULT' order by resource_name;

-- b.
alter profile default limit FAILED_LOGIN_ATTEMPTS 4;
alter profile default limit PASSWORD_GRACE_TIME 5; 

-- c.
-- Tras el último intento, cuando vayamos a intentar por quinta vez, la cuenta aparece
-- bloqueada y aunque uses su contraseña original no puedes acceder a ella

-- d.
alter user usuario1 account unlock;

-- e.
show parameter sec_max_failed_login_attempts ;
-- Aparece que el parámetro de iniciación es 3. Son diferentes, el primero es apra controlar el numero
-- de accesos de un usuario y en cambio el segundo parámetro está pensado para proteger ante
-- ataques en el que el atacante intenta acceder a varios usuarios poniendo contraseñas incorrectas

-- f.
-- Sí, es posible eliminar dichos perfiles usando el procedimiento drop_sql_profile
-- No es posible eliminar todos los perfiles, ya que oracle necesita tener un perfil por defecto
-- Es mejor asignarle otro perfil a los usuarios que el perfil que queremos eliminar antes de eliminarlo

-- Última pregunta.
/* Los parámetros dinámicos son parámetros que se pueden cambiar en el servidor Oracle en vivo,
es decir, cuanto este se encuentra funcionando. En cambio, los parámetros estáticos se cambian antes de arrancar el 
servidor oracle
*/
