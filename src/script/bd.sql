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

SET lc_time_names = 'es_MX';

CREATE TABLE usuario (
  usr_username varchar(20) NOT NULL,
  usr_password varchar(50) NOT NULL,
  usr_rut int(10) NOT NULL,
  usr_dv  varchar(1) NOT NULL,
  usr_nombre varchar(50) NOT NULL,
  usr_apellidos varchar(100) NOT NULL,  
  PRIMARY KEY  (usr_username)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

insert into usuario values('ticket','202cb962ac59075b964b07152d234b70',13859176,'K','TICKET','.');


CREATE TABLE rol (
  rol_id int(2) NOT NULL AUTO_INCREMENT,
  rol_desc varchar(50) NOT NULL,
  PRIMARY KEY  (rol_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

insert into rol values(null,'ADMIN');
insert into rol values(null,'VENDEDOR');


CREATE TABLE usuarios_roles (
  usr_username  varchar(20) NOT NULL,
  rol_id int(2) NOT NULL,
  PRIMARY KEY  (rol_id,usr_username),
  FOREIGN KEY (usr_username) REFERENCES usuario(usr_username),
  FOREIGN KEY (rol_id) REFERENCES rol(rol_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


insert into usuarios_roles values('ticket',2);

CREATE TABLE hincha (
  
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
  PRIMARY KEY  (hin_rut) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


insert into hincha values (11111111,'9','ENTRADA','NORMAL','-------','-', '-------','---------',STR_TO_DATE('13-06-1980', '%d-%m-%Y'),NOW(),'A');


CREATE TABLE sector (
  
  sec_id int(2) NOT NULL AUTO_INCREMENT, 
  sec_desc varchar(50) NOT NULL,  
  PRIMARY KEY  (sec_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE partido (
  
  par_id int(4) NOT NULL AUTO_INCREMENT,  
  par_desc varchar(50) NOT NULL, 
  par_fecha datetime NOT NULL,
  PRIMARY KEY  (par_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE entrada (  
  ent_id int  NOT NULL AUTO_INCREMENT,
  sec_id int(3) NOT NULL,
  par_id int(3) NOT NULL,  
  ent_precio int(6) NOT NULL,
  ent_fecha_creacion datetime NOT NULL,
  ent_comentario varchar(100),
  ent_maximo int NOT NULL,
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
  com_token varchar(20) NOT NULL,
  com_tipo varchar(1) NOT NULL,
  com_anulada varchar(1) NOT NULL,
  com_fecha datetime NOT NULL,
  PRIMARY KEY  (com_id),
  FOREIGN KEY (ent_id) REFERENCES entrada(ent_id),
  FOREIGN KEY (hin_rut) REFERENCES hincha(hin_rut),
  FOREIGN KEY (usr_username) REFERENCES usuario(usr_username)

) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
ALTER TABLE compra ADD UNIQUE (com_token);


CREATE TABLE lista_negra (
  
  lne_rut int(10) NOT NULL,
  lne_dv  varchar(1) NOT NULL,
  lne_nombre varchar(100) NOT NULL , 
  PRIMARY KEY  (lne_rut) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE abonados_sector (
  
  hin_rut int(10) NOT NULL,
  sec_id int(3) NOT NULL , 
  abs_vigencia date NOT NULL,
  PRIMARY KEY  (hin_rut),
  FOREIGN KEY (sec_id) REFERENCES sector(sec_id),
  FOREIGN KEY (hin_rut) REFERENCES hincha(hin_rut)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;


CREATE TABLE acceso_estadio (
  id  varchar(15) NOT NULL,
  par_id int(3) NOT NULL,
  sec_id int(3) NOT NULL ,  
  fecha datetime NOT NULL,
  PRIMARY KEY  (id, sec_id,par_id)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
