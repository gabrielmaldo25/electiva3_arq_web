-- INSERTAR CLIENTES
INSERT INTO cliente(apellido,email,fecha_nac,nombre,nro_documento,telefono)
VALUES('ortiz', 'aortiz@gmail.com','24-03-2000','alexis','1234567','098644783');

INSERT INTO cliente(apellido,email,fecha_nac,nombre,nro_documento,telefono)
VALUES('maldonado', 'amaldonado@gmail.com','23-05-2000','angel','1234567','098644783');
SELECT * FROM cliente;

-- ELIMINAR TABLAS
DO $$ DECLARE
  r RECORD;
BEGIN
  FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
    EXECUTE 'DROP TABLE ' || quote_ident(r.tablename) || ' CASCADE';
  END LOOP;
END $$;