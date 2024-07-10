create database POOPARCIAL3
drop database POOPARCIAL3

use POOPARCIAL3

create table FACILITADOR(
    id int primary key identity,
    nombreFacilitador varchar(30)
)

create table TIPO_TARJETA(
id int primary key identity,
nombreTipoTarjeta varchar(7)
)


create table CLIENTE(
    id int primary key identity,
    nombre_completo varchar(100),
    direccion varchar(200),
    telefono varchar (20)
)

create table TARJETA(
id int primary key identity,
    numero_tarjeta varchar(20),
    fecha_expiracion date,
    FK_id_facilitador int foreign key (FK_id_facilitador) references FACILITADOR(id),
    FK_id_cliente int foreign key (FK_id_cliente) references CLIENTE (id),
FK_id_tipo_tarjeta int foreign key (FK_id_tipo_tarjeta) references TIPO_TARJETA(id)
);

create table COMPRA(
    id int primary key identity,
    fecha_compra date,
    dinero_gastado money,
    descripcion varchar(150),
    FK_id_tarjeta int foreign key (FK_id_tarjeta) references TARJETA (id)
)


INSERT INTO CLIENTE (nombre_completo, direccion, telefono) VALUES
('Juan Perez', 'Calle Falsa 123', '+34 555-1234'),
('Maria Lopez', 'Avenida Siempre Viva 742', '+503 555-5678'),
('Carlos Garcia', 'Boulevard de los Sueños 999', '+503 555-9101'),
('Ana Torres', 'Calle Luna 456', '+34 555-2345'),
('Luis Ramirez', 'Calle Sol 789', '+34 555-3456'),
('Patricia Fernandez', 'Avenida Libertador 101', '+503 555-6789'),
('Miguel Rodriguez', 'Calle Estrella 202', '+34 555-4567'),
('Lucia Martinez', 'Calle del Rio 303', '+503 555-7890'),
('Javier Hernandez', 'Avenida del Mar 404', '+34 555-5678'),
('Sofia Morales', 'Boulevard Central 505', '+503 555-8901');

INSERT INTO TIPO_TARJETA (nombreTipoTarjeta) VALUES
('Credito'),
('Debito');

INSERT INTO FACILITADOR (nombreFacilitador) VALUES
('Visa'),
('MasterCard'),
('American Express'),
('Discover'),
('Other');

INSERT INTO TARJETA (numero_tarjeta, fecha_expiracion, FK_id_facilitador, FK_id_cliente, FK_id_tipo_tarjeta) VALUES
('5126834737267989', '2025-12-31', 2, 2, 1),
('5574945921710795', '2025-11-30', 2, 5, 2),
('5307268396055420', '2026-01-15', 2, 2, 2),
('5259303426376138', '2028-09-20', 2, 3, 1),
('5259805993179687', '2026-09-20', 2, 4, 2),
('4863593396646061', '2026-09-20', 1, 9, 2),
('4670894538972545', '2026-09-20', 1, 2, 1),
('4010194742175392', '2026-09-20', 1, 4, 1),
('4543800299726165', '2026-09-20', 1, 9, 2),
('4543800299726165', '2026-09-20', 3, 8, 2),
('4543800299726165', '2026-09-20', 3, 7, 1),
('4543800299726165', '2026-09-20', 3, 7, 1),
('4543800299726165', '2026-09-20', 4, 6, 1),
('4543800299726165', '2026-09-20', 4, 8, 2);
SELECT t.id,t.numero_tarjeta,t.fecha_expiracion, f.nombre, t.FK_id_cliente 
FROM TARJETA as t inner join FACILITADOR as f on t.FK_id_facilitador = f.id 
inner join 
WHERE t.FK_id_cliente = 2
select * from TARJETA

select * from TARJETA

SELECT numero_tarjeta FROM TARJETA WHERE id = 2




drop table COMPRA
drop table TARJETA
drop table FACILITADOR
drop table TIPO_TARJETA
drop table CLIENTE
