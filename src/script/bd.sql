create database if not exists rticket;

USE rticket;

DROP TABLE IF EXISTS compra;
DROP TABLE IF EXISTS entrada;
DROP TABLE IF EXISTS usuarios_roles;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS rol;
DROP TABLE IF EXISTS hincha;
DROP TABLE IF EXISTS sector;
DROP TABLE IF EXISTS partido;


CREATE TABLE usuario (
  usr_username varchar(20) NOT NULL,
  usr_password varchar(50) NOT NULL,
  usr_rut int(10) NOT NULL,
  usr_dv  varchar(1) NOT NULL,
  usr_nombre varchar(50) NOT NULL,
  usr_apellidos varchar(100) NOT NULL,  
  PRIMARY KEY  (usr_username)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

insert into usuario values('esteban','202cb962ac59075b964b07152d234b70',13859176,'K','ESTEBAN','GUTIERREZ ABARZUA');
insert into usuario values('gabriel','202cb962ac59075b964b07152d234b70',24972726,'1','GABRIEL','GUTIERREZ BARRIGA');
insert into usuario values('antonia','202cb962ac59075b964b07152d234b70',23235193,'4','ANTONIA','GUTIERREZ BARRIGA');


CREATE TABLE rol (
  rol_id int(2) NOT NULL,
  rol_desc varchar(50) NOT NULL,
  PRIMARY KEY  (rol_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

insert into rol values(1,'ADMIN');
insert into rol values(2,'VENDEDOR');


CREATE TABLE usuarios_roles (
  usr_username  varchar(20) NOT NULL,
  rol_id int(2) NOT NULL,
  PRIMARY KEY  (rol_id,usr_username),
  FOREIGN KEY (usr_username) REFERENCES usuario(usr_username),
  FOREIGN KEY (rol_id) REFERENCES rol(rol_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

insert into usuarios_roles values('esteban',1);
insert into usuarios_roles values('esteban',2);
insert into usuarios_roles values('gabriel',1);
insert into usuarios_roles values('antonia',1);



CREATE TABLE hincha (
  hin_rut int(10) NOT NULL,
  hin_dv  varchar(1) NOT NULL,
  hin_nombres varchar(50) NOT NULL,
  hin_apellidos varchar(70) NOT NULL,
  hin_direccion varchar(100) NOT NULL,
  hin_genero varchar(1) NOT NULL,
  hin_telefono varchar(9) NOT NULL,
  hin_email varchar(100) NOT NULL,
  hin_fecha_nac date NOT NULL,
  hin_fecha_ingreso date NOT NULL,
  hin_categoria varchar(2) NOT NULL,
  PRIMARY KEY  (hin_rut)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

insert into hincha values (13797397,'9','MELISA','BARRIGA SALAZAR','TEGUALDA 2050-A','F', '994583020','melisa.barriga@gmail.com',STR_TO_DATE('13-06-1980', '%d-%m-%Y'),NOW(),'A');


CREATE TABLE sector (
  sec_id int(2) NOT NULL,
  sec_desc varchar(50) NOT NULL, 
  PRIMARY KEY  (sec_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

insert into sector values(1,'ANDES');
insert into sector values(2,'PACIFICO');
insert into sector values(3,'NORTE');
insert into sector values(4,'SUR');


CREATE TABLE partido (
  par_id int(2) NOT NULL,
  par_desc varchar(50) NOT NULL, 
  par_fecha date NOT NULL,
  PRIMARY KEY  (par_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

insert into partido values(1,'BARCELONA F.C',STR_TO_DATE('13-06-2017', '%d-%m-%Y'));
insert into partido values(2,'JUVENTUS',STR_TO_DATE('22-06-2017', '%d-%m-%Y'));
insert into partido values(3,'ARSENAL',STR_TO_DATE('13-10-2017', '%d-%m-%Y'));


CREATE TABLE entrada (
  ent_id int  NOT NULL AUTO_INCREMENT,
  sec_id int(3) NOT NULL,
  par_id int(3) NOT NULL,
  ent_precio int(6) NOT NULL,
  ent_fecha_creacion date NOT NULL,
  PRIMARY KEY  (ent_id),
  FOREIGN KEY (sec_id) REFERENCES sector(sec_id),
  FOREIGN KEY (par_id) REFERENCES partido(par_id)
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE compra (
  com_id int  NOT NULL AUTO_INCREMENT,
  ent_id int(3) NOT NULL,
  hin_rut int(10) NOT NULL,
  usr_username varchar(20) NOT NULL,
  com_monto int(6) NOT NULL,
  com_token varchar(12) NOT NULL,
  com_fecha date NOT NULL,
  PRIMARY KEY  (com_id),
  FOREIGN KEY (ent_id) REFERENCES entrada(ent_id),
  FOREIGN KEY (hin_rut) REFERENCES hincha(hin_rut),
  FOREIGN KEY (usr_username) REFERENCES usuario(usr_username) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


