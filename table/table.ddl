drop table USERDATA;
drop table USERDATABLOOD;
drop table USERINFORMATION;
drop table AWSCONFIG;

create table USERINFORMATION(
  id int auto_increment not null primary key, 
  username varchar(50) not null unique, 
  password varchar(256) not null,
  create_time timestamp not null, 
  modified_time timestamp not null
);


create table USERDATA(
  id int auto_increment not null primary key, 
  userid int not null, 
  height double not null DEFAULT 0.0,
  weight double not null DEFAULT 0.0, 
  create_time timestamp not null, 
  modified_time timestamp not null,
  foreign key fk_userid(userid) references USERINFORMATION(id) on delete cascade on update cascade
);


create table USERDATABLOOD(
  id int auto_increment not null primary key, 
  userid integer not null, 
  GTP double not null DEFAULT 0.0, 
  HDL double not null DEFAULT 0.0, 
  LDL double not null DEFAULT 0.0, 
  TG  double not null DEFAULT 0.0, 
  FPG double not null DEFAULT 0.0, 
  create_time timestamp not null, 
  modified_time timestamp not null,
  foreign key fk_userid(userid) references USERINFORMATION(id) on delete cascade on update cascade
);

create table AWSCONFIG(
  id int auto_increment not null primary key, 
  userpoolid varchar(256) not null,
  clientid varchar(256) not null,
  accesskey varchar(256) not null, 
  secretkey varchar(256) not null,
  create_time timestamp not null, 
  modified_time timestamp not null
);


