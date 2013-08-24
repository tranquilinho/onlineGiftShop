  
 create table articulos_pedido(
 	articulo varchar(50) references articulo,
        pedido integer references pedido,
        cantidad integer,
        estado text check (value in ('embalaje','entrega','entregado')),
 	primary key (articulo,pedido)
 );
