  
 create table temporadas_articulo(
 	articulo varchar(50) references articulo,
        temporada varchar(50) references temporada,
 	primary key (articulo,temporada)
 );
