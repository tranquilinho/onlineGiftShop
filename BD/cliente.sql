 create table cliente(
 	login varchar(16) references usuario on delete cascade,
 	email text,
 	numero_tarjeta text,
 	direccion integer references direccion,
 	primary key (login)
 );
