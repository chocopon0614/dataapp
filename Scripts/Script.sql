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
