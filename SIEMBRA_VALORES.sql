CREATE DATABASE SIEMBRA_VALORES;
USE SIEMBRA_VALORES;

-- Tabla Colonia
CREATE TABLE COLONIA (
    ID_COL INT IDENTITY(1,1) PRIMARY KEY,
    NOMBRE VARCHAR(100) NOT NULL,
    CIUDAD VARCHAR(100) NOT NULL,
    COD_POSTAL VARCHAR(10) NOT NULL,
    FECHA_REGISTRO DATETIME DEFAULT GETDATE(),
    CAPACIDAD_DISP INT
);

-- Tabla Escuela
CREATE TABLE ESCUELA (
    ID_ESCUELA INT IDENTITY(1,1) PRIMARY KEY,
    NOMBRE VARCHAR(100) NOT NULL,
    DIRECCION VARCHAR(150) NOT NULL,
    NIVEL_EDUCATIVO VARCHAR(50) NOT NULL,
    NUMERO_ARBOLES INT DEFAULT 0,
    FECHA_REGISTRO DATETIME DEFAULT GETDATE(),
    ID_COL INT NULL,
    FOREIGN KEY (ID_COL) REFERENCES COLONIA(ID_COL) ON DELETE SET NULL
);

-- Tabla Usuario
CREATE TABLE USUARIO (
    ID_US INT IDENTITY(1,1) PRIMARY KEY,
    FECHA_REGISTRO DATETIME DEFAULT GETDATE(),
    NOMBRE VARCHAR(50) NOT NULL,
    AP_P VARCHAR(50) NOT NULL,
    AP_M VARCHAR(50) NULL,
    CORREO VARCHAR(100) UNIQUE NOT NULL,
    CELULAR VARCHAR(15) NOT NULL,
    FECHA_NAC DATE NOT NULL,
    CONTRASENA VARCHAR(255) NOT NULL,
    ACTIVO BIT DEFAULT 1,
    PUNTOS INT DEFAULT 0,
    ID_ESCUELA INT NULL,
    TIPO VARCHAR(50) NOT NULL CHECK (TIPO IN ('Alumno', 'Administrador', 'Otro')),
    FOREIGN KEY (ID_ESCUELA) REFERENCES ESCUELA(ID_ESCUELA) ON DELETE SET NULL
);


-- Tabla Valores
CREATE TABLE VALORES (
    ID_VALOR INT IDENTITY(1,1) PRIMARY KEY,
    VALOR VARCHAR(50) NOT NULL
);


-- Tabla Árbol
CREATE TABLE ARBOL (
    ID_ARBOL INT IDENTITY(1,1) PRIMARY KEY,
    NOMBRE_CIENTIFICO VARCHAR(100) NOT NULL,
    FECHA_PLANTADO DATE NOT NULL,
    FECHA_BAJA DATE NULL,
    ID_VALOR INT NULL,
    VIVO BIT DEFAULT 1,
    ID_COL INT NULL,
    FECHA_REGISTRO DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ID_VALOR) REFERENCES VALORES(ID_VALOR) ON DELETE SET NULL,
    FOREIGN KEY (ID_COL) REFERENCES COLONIA(ID_COL) ON DELETE SET NULL
);


-- Tabla Cuidados
CREATE TABLE CUIDADOS (
    ID_CUIDADOS INT IDENTITY(1,1) PRIMARY KEY,
    TIPO VARCHAR(100) NOT NULL,
    NOMBRE VARCHAR(100) NOT NULL,
    NOMBRE_CIENTIFICO VARCHAR(100) NOT NULL,
    RIEGO INT DEFAULT 0,
    PODA INT DEFAULT 0,
    FERTILIZACION INT DEFAULT 0,
    MEDICION INT DEFAULT 0,
    FUMIGACION INT DEFAULT 0,
    ID_ARBOL INT NOT NULL,
    FOREIGN KEY (ID_ARBOL) REFERENCES ARBOL(ID_ARBOL) ON DELETE CASCADE
);


-- Tabla Adopción
CREATE TABLE ADOPTA (
    ID_ADOPTA INT IDENTITY(1,1) PRIMARY KEY,
    ID_US INT NOT NULL,                
    ID_ARBOL INT NOT NULL,            
    FECHA_ADOPCION_INICIO DATETIME DEFAULT GETDATE(),
    FECHA_ADOPCION_FIN DATETIME NULL,
    FOREIGN KEY (ID_US) REFERENCES USUARIO(ID_US) ON DELETE CASCADE,
    FOREIGN KEY (ID_ARBOL) REFERENCES ARBOL(ID_ARBOL) ON DELETE CASCADE
);


-- Tabla Servicios
CREATE TABLE SERVICIOS (
    ID_SERVICIO INT IDENTITY(1,1) PRIMARY KEY,
    TIPO VARCHAR(50) NOT NULL
);


-- Tabla Brindan
CREATE TABLE BRINDAN (
    ID_BRINDAN INT IDENTITY(1,1) PRIMARY KEY,
    ID_ARBOL INT NOT NULL,
    ID_SERVICIO INT NOT NULL,
    FECHA_SERVICIO DATETIME DEFAULT GETDATE(),
    COMENTARIOS VARCHAR(500) NULL,
    FOREIGN KEY (ID_ARBOL) REFERENCES ARBOL(ID_ARBOL) ON DELETE CASCADE,
    FOREIGN KEY (ID_SERVICIO) REFERENCES SERVICIOS(ID_SERVICIO) ON DELETE CASCADE
);


-- Tabla Notificaciones
CREATE TABLE NOTIFICACIONES (
    ID_NOTIFICACION INT IDENTITY(1,1) PRIMARY KEY,
    ID_US INT NOT NULL,
    ID_ARBOL INT NOT NULL,
    MENSAJE VARCHAR(255) NOT NULL,
    FECHA_ENVIO DATETIME DEFAULT GETDATE(),
    LEIDO BIT DEFAULT 0,
    FOREIGN KEY (ID_US) REFERENCES USUARIO(ID_US) ON DELETE CASCADE,
    FOREIGN KEY (ID_ARBOL) REFERENCES ARBOL(ID_ARBOL) ON DELETE CASCADE
);

-- Tabla Medición
CREATE TABLE MEDICION (
    ID_MED INT IDENTITY(1,1) PRIMARY KEY,
    FECHA DATE NOT NULL,
    ALTURA INT NOT NULL,
    CIRCUNFERENCIA FLOAT NOT NULL,
    FOTO VARCHAR(100) NULL,
    ID_ARBOL INT NOT NULL,
    FOREIGN KEY (ID_ARBOL) REFERENCES ARBOL(ID_ARBOL) ON DELETE CASCADE
);

