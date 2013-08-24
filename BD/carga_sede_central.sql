insert into usuario values ('cliente','Cliente','cliente',false,true);
insert into cliente values ('cliente','correo1@correo.com','1234 5678 9012 3456',1);
insert into usuario values ('empleado','Empleado','empleado',true,false);

insert into sede values (1,'sede1','Madrid','http://localhost:8080/articulos-jaxrpc/articulos');
insert into sede values (2,'sede2','Barcelona','http://localhost:8080/articulos2-jaxrpc/articulos');

insert into direccion values(1,'Atocha','Madrid','28080');
insert into direccion values(2,'Ramblas','Barcelona','08080');

insert into pedido values (1,'2006/05/11',1,'cliente');
insert into pedido values (2,'2006/05/10',1,'cliente');

insert into articulos_pedido values('Pendiente',1,2,1);
insert into articulos_pedido values('Channel #5',1,1,1);
insert into articulos_pedido values('Cuento',2,3,1);
insert into articulos_pedido values('Pelota',2,1,1);
