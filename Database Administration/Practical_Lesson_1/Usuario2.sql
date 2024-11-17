-- 13.
-- Sí, funciona (usar commit)
exec usuario1.pr_inserta_tabla2(3);
commit;

-- 14. 
-- En el usuario1 porque el procedimiento inserta los datos como dicho usuario

-- 17.
-- Sí, funciona 
exec usuario1.pr_inserta_tabla2(7);
commit;

-- 21. 
-- Ahora si se ejecuta sin problemas, ya que al darle permisos al usuario1, se le
-- otorgan los permisos al procedimiento, luego aunque el usuario2 no tenga permisos, 
-- a través de los permisos del procedimiento puede crearlas sin problemas
exec usuario1.pr_crea_tabla('OTRATABLA2', 'CODIGO2');