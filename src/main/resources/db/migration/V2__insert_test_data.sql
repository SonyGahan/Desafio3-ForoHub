-- Insertar usuario solo si no existe
INSERT INTO usuario (id, nombre, correo_electronico, contraseña)
SELECT 1, 'Juan Pérez', 'juan@example.com', '$2a$10$E9bAeW.eQwTC/7IYxn2jbe9z0sTz7W8/BvblMVdyAhv/xFRds.A8i'
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE id = 1);

-- Insertar curso solo si no existe
INSERT INTO curso (id, nombre, categoria)
SELECT 1, 'Java Básico', 'Programación'
WHERE NOT EXISTS (SELECT 1 FROM curso WHERE id = 1);
