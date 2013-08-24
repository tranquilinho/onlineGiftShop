DROP DATABASE IF EXISTS visa;
CREATE DATABASE visa;
USE visa;

CREATE TABLE tarjetas(
	numero char(19),	-- Número de tarjeta, 16 dígitos
	titular varchar(30),	-- Titular de la tarjeta
	validaDesde char(5),	-- Inicio de la validez
	validaHasta char(5),	-- Fin de la validez
        limite FLOAT,           -- maximo gasto permitido (limite de credito)
	primary key (numero)
);

INSERT INTO tarjetas VALUES ('1234 5678 9012 3456','Juan Castellano','10/04','10/06',500);
INSERT INTO tarjetas VALUES ('5678 9012 3456 7890','Antonio Gallego','9/03','9/06',30);

CREATE TABLE cargos(
    id_cargo INT AUTO_INCREMENT PRIMARY KEY,
    importe FLOAT,
    numero_tarjeta CHAR(19) REFERENCES tarjetas,
    establecimiento CHAR(50),
    fecha DATE
);
