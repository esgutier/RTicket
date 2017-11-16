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
DROP TABLE IF EXISTS lista_negra;
DROP TABLE IF EXISTS abonados_sector;
DROP TABLE IF EXISTS acceso_estadio;

CREATE TABLE equipo (
  equ_id int(2) NOT NULL,
  equ_nombre varchar(100) NOT NULL,
  PRIMARY KEY  (equ_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE usuario (
  equ_id int(2) NOT NULL,
  usr_username varchar(20) NOT NULL,
  usr_password varchar(50) NOT NULL,
  usr_rut int(10) NOT NULL,
  usr_dv  varchar(1) NOT NULL,
  usr_nombre varchar(50) NOT NULL,
  usr_apellidos varchar(100) NOT NULL,  
  PRIMARY KEY  (equ_id, usr_username),
  FOREIGN KEY (equ_id) REFERENCES equipo(equ_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

/*insert into usuario values('ticket','202cb962ac59075b964b07152d234b70',13859176,'K','TICKET','.');*/


CREATE TABLE rol (
  rol_id int(2) NOT NULL AUTO_INCREMENT,
  rol_desc varchar(50) NOT NULL,
  PRIMARY KEY  (rol_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

/*
insert into rol values(null,'ADMIN');
insert into rol values(null,'VENDEDOR');
*/

CREATE TABLE usuarios_roles (
  equ_id int(2) NOT NULL,
  usr_username  varchar(20) NOT NULL,
  rol_id int(2) NOT NULL,
  PRIMARY KEY  (equ_id, usr_username, rol_id),
  FOREIGN KEY (equ_id, usr_username) REFERENCES usuario(equ_id, usr_username),
  FOREIGN KEY (rol_id) REFERENCES rol(rol_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


/*insert into usuarios_roles values('ticket',2);*/

CREATE TABLE hincha (
  equ_id int(2) NOT NULL,
  hin_rut int(10) NOT NULL,
  hin_dv  varchar(1) NOT NULL,
  hin_nombres varchar(50) NOT NULL,
  hin_apellidos varchar(70),
  hin_direccion varchar(100) NOT NULL,
  hin_genero varchar(1),
  hin_telefono varchar(9) NOT NULL,
  hin_email varchar(100) NOT NULL,
  hin_fecha_nac date,
  hin_fecha_ingreso datetime NOT NULL,
  hin_categoria varchar(2)  NOT NULL,
  PRIMARY KEY  (equ_id, hin_rut),
  FOREIGN KEY (equ_id) REFERENCES equipo(equ_id) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


/*insert into hincha values (11111111,'9','ENTRADA','NORMAL','-------','-', '-------','---------',STR_TO_DATE('13-06-1980', '%d-%m-%Y'),NOW(),'A');*/


CREATE TABLE sector (
  equ_id int(2) NOT NULL,
  sec_id int(3) NOT NULL AUTO_INCREMENT, 
  sec_desc varchar(50) NOT NULL,  
  PRIMARY KEY  (sec_id),
  FOREIGN KEY (equ_id) REFERENCES equipo(equ_id) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE partido (
  equ_id int(2) NOT NULL,
  par_id int(4) NOT NULL AUTO_INCREMENT,  
  par_desc varchar(50) NOT NULL, 
  par_fecha datetime NOT NULL,
  PRIMARY KEY  (par_id),
  FOREIGN KEY (equ_id) REFERENCES equipo(equ_id) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE entrada (  
  equ_id int(2) NOT NULL,
  ent_id int  NOT NULL AUTO_INCREMENT,
  sec_id int(3) NOT NULL,
  par_id int(3) NOT NULL,  
  ent_precio int(6) NOT NULL,
  ent_fecha_creacion datetime NOT NULL,
  ent_comentario varchar(100),
  ent_maximo int NOT NULL,
  PRIMARY KEY  (ent_id),
  FOREIGN KEY (sec_id) REFERENCES sector(sec_id),
  FOREIGN KEY (par_id) REFERENCES partido(par_id),
  FOREIGN KEY (equ_id) REFERENCES equipo(equ_id)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE compra ( 
  equ_id int(2) NOT NULL,
  com_id int  NOT NULL AUTO_INCREMENT,
  par_id int NOT NULL,
  sec_id int  NOT NULL,
  ent_id int(3) NOT NULL,
  hin_rut int(10) NOT NULL,
  usr_username varchar(20) NOT NULL,  
  com_monto int(6) NOT NULL,
  com_token varchar(20) NOT NULL,
  com_tipo varchar(1) NOT NULL,
  com_anulada varchar(1) NOT NULL,
  com_fecha datetime NOT NULL,
  PRIMARY KEY  (com_id),
  FOREIGN KEY (ent_id) REFERENCES entrada(ent_id),
  FOREIGN KEY (equ_id, usr_username) REFERENCES usuario(equ_id, usr_username),
  FOREIGN KEY (sec_id) REFERENCES sector(sec_id),
  FOREIGN KEY (par_id) REFERENCES partido(par_id)
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
ALTER TABLE compra ADD UNIQUE (com_token);
ALTER TABLE compra ADD INDEX (equ_id, par_id, sec_id, hin_rut);


CREATE TABLE lista_negra (
  
  lne_rut int(10) NOT NULL,
  lne_dv  varchar(1) NOT NULL,
  lne_nombre varchar(100) NOT NULL , 
  PRIMARY KEY  (lne_rut) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE abonados_sector (
  equ_id int(2) NOT NULL,
  hin_rut int(10) NOT NULL,
  sec_id int(3) NOT NULL , 
  abs_vigencia date NOT NULL,
  PRIMARY KEY  (equ_id, hin_rut),
  FOREIGN KEY (equ_id, sec_id) REFERENCES sector(equ_id, sec_id),
  FOREIGN KEY (equ_id, hin_rut) REFERENCES hincha(equ_id, hin_rut)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE acceso_estadio (
  equ_id int(2) NOT NULL,
  id  varchar(15) NOT NULL,
  par_id int(3) NOT NULL,
  sec_id int(3) NOT NULL ,  
  fecha datetime NOT NULL,
  PRIMARY KEY  (equ_id, id, sec_id,par_id)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


insert into rol values(null,'ADMIN');
insert into rol values(null,'VENDEDOR');
insert into rol values(null,'CONTROL');

insert into equipo values(1,'MANCHESTER CITY');
insert into equipo values(2,'ARSENAL F.C.');
insert into usuario values(1,'mcity','202cb962ac59075b964b07152d234b70',13859176,'K','TICKET','.');
insert into usuario values(1,'con-mcity','202cb962ac59075b964b07152d234b70',13859176,'K','TICKET','.');
insert into usuario values(2,'arsenal','202cb962ac59075b964b07152d234b70',13859176,'K','TICKET','.');

insert into usuarios_roles values(1,'mcity',2);
insert into usuarios_roles values(1,'con-mcity',3);
insert into usuarios_roles values(2,'arsenal',2);
