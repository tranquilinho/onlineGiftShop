  
 create table pedido(
 	ID integer auto_increment,
        fecha date,
        direccion_pedido integer references direccion,
 	primary key (ID)
 );
