-- Dentro de Usuario1 no podemos ejecutar el comando para crear el procedimiento,
-- ya que Usuario1 no tiene privilegios suficientes

-- 11.
-- Sí, funciona (usar commit)
exec pr_inserta_tabla2(1);
commit;

-- 12.
grant execute on pr_inserta_tabla2 to usuario2;

-- 16. 
-- Sí, funciona
exec usuario1.pr_inserta_tabla2(4);
commit;

-- 19. 
-- No funciona, ya que no tiene permiso el procedimiento para crear tablas
exec pr_crea_tabla('OTRATABLA', 'CODIGO');
commit;

-- 20. 
grant execute on pr_crea_tabla to usuario2;