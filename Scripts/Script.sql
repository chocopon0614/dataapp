create table USERINFORMATION(
  id int auto_increment not null primary key, 
  username varchar(50) not null unique, 
  password varchar(256) not null,
  create_time timestamp default CURRENT_TIMESTAMP, 
  modified_time timestamp default CURRENT_TIMESTAMP
);


create table USERDATA(
  id int auto_increment not null primary key, 
  userid int not null, 
  height double, 
  weight double, 
  create_time timestamp default CURRENT_TIMESTAMP, 
  modified_time timestamp default CURRENT_TIMESTAMP,
  foreign key fk_userid(userid) references USERINFORMATION(id) on delete cascade on update cascade
);


create table USERDATABLOOD(
  id int auto_increment not null primary key, 
  userid integer not null, 
  GTP double, 
  HDL double, 
  LDL double, 
  TG double, 
  FPG double, 
  create_time timestamp DEFAULT CURRENT_TIMESTAMP, 
  modified_time timestamp DEFAULT CURRENT_TIMESTAMP,
  foreign key fk_userid(userid) references USERINFORMATION(id) on delete cascade on update cascade
);

