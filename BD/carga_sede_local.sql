insert into usuario values ('empleado','Empleado','empleado',true,false);

insert into temporada values('dia de la madre');
insert into temporada values('navidad');

insert into articulo values('Pendiente','Joyas',20,'femenino');
insert into articulo values('Channel #5','Perfumes',40,'femenino');
insert into articulo values('Cuento','Libros',10,'infantil');
insert into articulo values('Pelota','Juguetes',10,'infantil');

insert into temporadas_articulo values('Pendiente','dia de la madre');
insert into temporadas_articulo values('Channel #5','dia de la madre');
insert into temporadas_articulo values('Cuento','navidad');
insert into temporadas_articulo values('Pelota','navidad');

insert into direccion values(1,'Atocha','Madrid','28080');

insert into pedido values (1,'2006/05/11',1);
insert into pedido values (2,'2006/05/10',1);

insert into articulos_pedido values('Pendiente',1,2,'embalaje');
insert into articulos_pedido values('Channel #5',1,1,'embalaje');
insert into articulos_pedido values('Cuento',2,3,'entrega');
insert into articulos_pedido values('Pelota',2,1,'entrega');