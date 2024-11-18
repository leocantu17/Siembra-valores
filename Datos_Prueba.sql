USE SIEMBRA_VALORES;

INSERT INTO COLONIA (NOMBRE, CIUDAD, COD_POSTAL, FECHA_REGISTRO, META, MINIMO_ARBOLES) VALUES
('Colonia Verde', 'Santiago', '8320000', GETDATE(), 500, 50),
('Colonia Esperanza', 'Valpara�so', '2340000', GETDATE(), 300, 30),
('Colonia Naturaleza', 'Concepci�n', '4030000', GETDATE(), 600, 40),
('Colonia del Sol', 'La Serena', '1700000', GETDATE(), 400, 20);

INSERT INTO USUARIO (FECHA_REGISTRO, NOMBRE, AP_P, AP_M, CORREO, CELULAR, FECHA_NAC, CONTRASENA, ACTIVO, ID_COL) VALUES
(GETDATE(), 'Juan', 'P�rez', 'Gonz�lez', 'juan.perez@example.com', '987654321', '2010-05-01', 'pass123', 1, 5),
(GETDATE(), 'Ana', 'L�pez', 'Mart�nez', 'ana.lopez@example.com', '912345678', '2011-06-12', 'pass456', 1, 6),
(GETDATE(), 'Pedro', 'G�mez', 'Ram�rez', 'pedro.gomez@example.com', '934567890', '2009-07-22', 'pass789', 1, 7),
(GETDATE(), 'Luc�a', 'Fern�ndez', 'Soto', 'lucia.fernandez@example.com', '945678901', '2012-08-30', 'pass000', 1, 8);

INSERT INTO USUARIO (FECHA_REGISTRO, NOMBRE, AP_P, AP_M, CORREO, CELULAR, FECHA_NAC, CONTRASENA, ACTIVO, ID_COL) VALUES
(GETDATE(), 'Juan', 'P�rez', 'Gonz�lez', 'juan.perez@example.com', '987654321', '2010-05-01', 'pass123', 1, 1),
(GETDATE(), 'Ana', 'L�pez', 'Mart�nez', 'ana.lopez@example.com', '912345678', '2011-06-12', 'pass456', 1, 2),
(GETDATE(), 'Pedro', 'G�mez', 'Ram�rez', 'pedro.gomez@example.com', '934567890', '2009-07-22', 'pass789', 1, 3),
(GETDATE(), 'Luc�a', 'Fern�ndez', 'Soto', 'lucia.fernandez@example.com', '945678901', '2012-08-30', 'pass000', 1, 4);

INSERT INTO CUIDADOS (NOMBRE, DESCRIPCION, FRECUENCIA) VALUES
('Riego', 'Proveer agua a los �rboles en periodos secos.', 'Semanal'),
('Fertilizaci�n', 'Aplicar fertilizantes para el crecimiento saludable.', 'Mensual'),
('Poda', 'Eliminar ramas muertas o enfermas para mejorar la salud del �rbol.', 'Anual'),
('Control de plagas', 'Aplicar tratamientos para evitar plagas.', 'Bimensual');

INSERT INTO VALORES (VALOR) VALUES
('Alto'),
('Medio'),
('Bajo'),
('Cr�tico');

INSERT INTO ARBOL (NOMBRE_CIENTIFICO, FECHA_PLANTADO, ENDEMICO, ID_VALOR, ID_CUIDADOS, ID_COL, FECHA_REGISTRO) VALUES
('Quillay', '2022-01-15', 1, 1, 5, 6, GETDATE()),
('Pino Oreg�n', '2021-03-22', 0, 2, 5, 7, GETDATE()),
('Cipr�s de la cordillera', '2020-06-10', 1, 3, 5, 8, GETDATE())

INSERT INTO ADMINISTRADOR (ID_US, ARBOLES_ASIGNADOS, ULTIMA_CONEXION, NIVEL) VALUES
(19, 15, GETDATE(), 'Supervisor')
INSERT INTO ESCUELA (NOMBRE, DIRECCION, NIVEL_EDUCATIVO, NUMERO_ARBOLES, ENCARGADO, FECHA_REGISTRO, ID_COL) VALUES
('Escuela Primaria A', 'Av. Libertador 123', 'Primaria', 10, 'Carlos Torres', GETDATE(), 5),
('Escuela Secundaria B', 'Calle 45 456', 'Secundaria', 20, 'Mar�a Garc�a', GETDATE(), 6),
('Escuela T�cnica C', 'Calle 89 789', 'T�cnico', 15, 'Jos� Mart�nez', GETDATE(), 7),
('Escuela Integral D', 'Calle 100 321', 'Integral', 12, 'Luc�a Torres', GETDATE(), 8);

INSERT INTO ALUMNO (ID_US, PUNTOS, ID_ESCUELA) VALUES
(11, 50, 6),
(12, 75, 7),
(13, 100, 8),
(14, 80, 9);

INSERT INTO ADOPTA (ID_US, ID_ARBOL, FECHA_ADOPCION_INICIO, FECHA_ADOPCION_FIN, ALTURA, CIRCUNFERENCIA) VALUES
(11, 8, GETDATE(), DATEADD(YEAR, 1, GETDATE()), 1.2, 30),
(12, 9, GETDATE(), DATEADD(YEAR, 1, GETDATE()), 1.5, 25),
(13, 10, GETDATE(), DATEADD(YEAR, 1, GETDATE()), 0.8, 20)

INSERT INTO REPORTES (FECHA, PROGRESO, ID_ARBOL) VALUES
(GETDATE(), 'Primer riego realizado', 1),
(GETDATE(), 'Fertilizaci�n completada', 2),
(GETDATE(), 'Poda programada', 3),
(GETDATE(), 'Control de plagas en curso', 4);

INSERT INTO SERVICIOS (TIPO) VALUES
('Riego'),
('Fertilizaci�n'),
('Poda'),
('Control de plagas');

INSERT INTO BRINDAN (ID_ARBOL, ID_SERVICIO, FECHA_SERVICIO, COMENTARIOS, ESTADO) VALUES
(1, 1, GETDATE(), 'Se realiz� el riego en la tarde.', 'Completado'),
(2, 2, GETDATE(), 'Fertilizaci�n aplicada.', 'Completado'),
(3, 3, GETDATE(), 'Poda realizada con �xito.', 'Completado'),
(4, 4, GETDATE(), 'Control de plagas programado.', 'Pendiente');

INSERT INTO NOTIFICACIONES (ID_US, ID_ARBOL, MENSAJE, FECHA_ENVIO) VALUES
(1, 1, 'Recuerda realizar el riego del �rbol.', GETDATE()),
(2, 2, 'Es hora de fertilizar el �rbol.', GETDATE()),
(3, 3, 'Pr�xima poda programada.', GETDATE()),
(4, 4, 'Control de plagas debe ser revisado.', GETDATE());

DELETE FROM BRINDAN;
DELETE FROM ADOPTA;
DELETE FROM REPORTES;
DELETE FROM NOTIFICACIONES;
DELETE FROM ARBOL;
DELETE FROM CUIDADOS;
DELETE FROM SERVICIOS;
DELETE FROM ADMINISTRADOR;
DELETE FROM ALUMNO;
DELETE FROM ESCUELA;
DELETE FROM USUARIO;
DELETE FROM COLONIA;
