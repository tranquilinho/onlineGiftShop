  
 create table articulos_pedido(
 	articulo varchar(50),
        pedido integer references pedido,
        cantidad integer,
	sede integer references sede,
 	primary key (articulo,pedido,sede)
 );
