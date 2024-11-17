CREATE TABLE employee (
first_name VARCHAR2(128),
last_name VARCHAR2(128),
empID NUMBER,
salary NUMBER(6) ENCRYPT,
usuario VARCHAR2(128) );

drop table employee;

select * from employee;

grant update(FIRST_NAME)ON employee to roberto3;

create or replace function vpd_function(p_schema varchar2, p_obj varchar2)
  Return varchar2
is
  Vusuario VARCHAR2(100);
Begin
  Vusuario := SYS_CONTEXT('userenv', 'SESSION_USER');
  return 'UPPER(usuario) = ''' || Vusuario || '''';
End;
/



