ALTER SYSTEM SET "WALLET_ROOT"='C:\app\alumnos\admin\orcl\xdb_wallet' scope=SPFILE;


ALTER SYSTEM SET TDE_CONFIGURATION="KEYSTORE_CONFIGURATION=FILE" scope=both;


/*ADMINISTER KEY MANAGEMENT CREATE KEYSTORE IDENTIFIED BY usuario;

ADMINISTER KEY MANAGEMENT CREATE AUTO_LOGIN KEYSTORE FROM KEYSTORE IDENTIFIED BY usuario;

ADMINISTER KEY MANAGEMENT SET KEY force keystore identified by usuario with backup;*/




alter system flush buffer_cache;

select * from dba_encrypted_columns;

select * from v$datafile;


create or replace function vpd_function(p_schema varchar2, p_obj varchar2)
  Return varchar2
is
  Vusuario VARCHAR2(100);
Begin
  Vusuario := SYS_CONTEXT('userenv', 'SESSION_USER');
  return 'UPPER(usuario) = ''' || Vusuario || '''';
End;
/

create user roberto3 identified by roberto3;

grant create session to roberto3;

grant create PROCEDURE to usuario1;

grant select on usuario1.employee to roberto3

begin dbms_rls.add_policy (object_schema =>'usuario1',
object_name =>'EMPLOYEE',
policy_name =>'POL_EMPLOYEE_1',
function_schema =>'usuario1',
policy_function => 'vpd_function',
statement_types => 'SELECT, UPDATE, DELETE' ); end;
/

begin

 DBMS_RLS.ENABLE_POLICY (        object_schema=>'usuario1',    object_name=>'EMPLOYEE',

policy_name=>'POL_EMPLOYEE_11', enable=>false);

end;
/

begin

dbms_rls.drop_policy (

  object_schema=>'usuario1',

      object_name=>'EMPLOYEE',

  policy_name=>'POL_EMPLOYEE_1' );

end;
/

