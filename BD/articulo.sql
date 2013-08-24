  
 create table articulo(
 	nombre varchar(50),
        tipo text,
        precio float ,
        publico text check (value in ('infantil','femenino','masculino','ancianos')),
 	primary key (nombre)
 );
