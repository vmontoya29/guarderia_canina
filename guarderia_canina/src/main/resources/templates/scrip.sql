CREATE DATABASE guarderia_canina;
USE guarderia_canina;


CREATE TABLE dueno (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(255),
    correo_electronico VARCHAR(100)
);


CREATE TABLE perro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    raza VARCHAR(50),
    edad INT,
    peso DOUBLE,
    dueno_id BIGINT,
    FOREIGN KEY (dueno_id) REFERENCES dueno(id) ON DELETE CASCADE
);


CREATE TABLE servicio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_servicio VARCHAR(100),
    costo DOUBLE,
    duracion VARCHAR(50),
    perro_id BIGINT,
    FOREIGN KEY (perro_id) REFERENCES perro(id) ON DELETE CASCADE
);


CREATE TABLE cuidador (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    experiencia VARCHAR(255),
    especializacion VARCHAR(255),
    contacto VARCHAR(50)
);


CREATE TABLE cuidador_dueno (
    cuidador_id BIGINT,
    dueno_id BIGINT,
    PRIMARY KEY (cuidador_id, dueno_id),
    FOREIGN KEY (cuidador_id) REFERENCES cuidador(id) ON DELETE CASCADE,
    FOREIGN KEY (dueno_id) REFERENCES dueno(id) ON DELETE CASCADE
);
