CREATE DATABASE Sistema_Seminario

USE Sistema_Seminario

CREATE TABLE usuarios (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
);

CREATE TABLE instalaciones (
    id_instalacion INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    capacidad INT NOT NULL,
);

CREATE TABLE reservas (
    id_reserva INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_instalacion INT NOT NULL,
    fecha_reserva DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_instalacion) REFERENCES instalaciones(id_instalacion)
);

CREATE TABLE horarios (
    id_horario INT IDENTITY(1,1) PRIMARY KEY,
    id_instalacion INT NOT NULL,
    dia_semana VARCHAR(50) NOT NULL,
    hora_apertura TIME NOT NULL,
    hora_cierre TIME NOT NULL,
    FOREIGN KEY (id_instalacion) REFERENCES instalaciones(id_instalacion)
);


INSERT INTO usuarios (nombre, email) VALUES 
('Francisco', 'franbalcarce21@gmail.com'),


INSERT INTO instalaciones (nombre, tipo, capacidad) VALUES 
('IsWellness', 'Gimnasio', 10),
('Tiempo extra', 'Cancha de futbol', '10')

INSERT INTO reservas (id_usuario, id_instalacion, fecha_reserva, hora_inicio, hora_fin) VALUES 
(1, 1, '2024-06-30', '08:00:00', '22:00:00'),


INSERT INTO horarios (id_instalacion, dia_semana, hora_apertura, hora_cierre) VALUES 
(1, 'Lunes a Sabados', '08:00:00', '22:00:00'),


SELECT * FROM usuarios

SELECT * FROM instalaciones

SELECT * FROM horarios

SELECT * FROM reservas


DELETE FROM reservas WHERE id_reserva IN (1, 2, 3);
DELETE FROM horarios WHERE id_horario IN (1, 2, 3);
DELETE FROM instalaciones WHERE id_instalacion IN (1, 2, 3);
DELETE FROM usuarios WHERE id_usuario IN (1, 2, 3);

SELECT COUNT(*) AS TotalReservas FROM reservas;
SELECT COUNT(*) AS TotalHorarios FROM horarios;
SELECT COUNT(*) AS TotalInstalaciones FROM instalaciones;
SELECT COUNT(*) AS TotalUsuarios FROM usuarios;


