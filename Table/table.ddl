drop table userinformation;
drop table userdata;
drop table userdatablood;

create table userinformation(
  id int auto_increment not null primary key, 
  username varchar(50) not null unique, 
  password varchar(256) not null,
  create_time timestamp default CURRENT_TIMESTAMP, 
  modified_time timestamp default CURRENT_TIMESTAMP
);


create table userdata(
  id int auto_increment not null primary key, 
  userid int not null, 
  height double, 
  weight double, 
  create_time timestamp default CURRENT_TIMESTAMP, 
  modified_time timestamp default CURRENT_TIMESTAMP,
  foreign key fk_userid(userid) references userinformation(id) on delete cascade on update cascade
);


create table userdatablood(
  id int auto_increment not null primary key, 
  userid integer not null, 
  GTP double, 
  HDL double, 
  LDL double, 
  TG double, 
  FPG double, 
  create_time timestamp DEFAULT CURRENT_TIMESTAMP, 
  modified_time timestamp DEFAULT CURRENT_TIMESTAMP,
  foreign key fk_userid(userid) references userinformation(id) on delete cascade on update cascade
);


--sampledata
INSERT INTO userinformation (USERNAME, PASSWORD) values ('test1', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');

INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,181.3, 72.3, '2012-12-23 09:00:23', '2012-12-23 09:00:23');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,180.9, 73.4, '2012-12-23 14:32:43', '2012-12-23 14:32:43');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,183.0, 71.8, '2012-12-26 20:21:03', '2012-12-26 20:21:03');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,183.3, 72.3, '2012-12-24 09:00:23', '2012-12-24 09:00:23');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,184.9, 73.4, '2012-12-25 14:32:43', '2012-12-25 14:32:43');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,186.0, 75.8, '2012-12-27 20:21:03', '2012-12-27 20:21:03');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,185.3, 72.9, '2012-12-27 10:00:23', '2012-12-28 09:00:23');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,184.3, 73.2, '2012-12-28 14:32:43', '2012-12-29 14:32:43');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,176.0, 74.8, '2012-12-29 20:21:03', '2012-12-30 20:21:03');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,183.9, 72.3, '2012-12-30 11:00:23', '2012-12-30 22:00:23');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,184.9, 73.4, '2012-12-30 12:32:43', '2012-12-30 14:32:43');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,182.0, 65.8, '2013-01-27 20:21:03', '2013-12-26 20:21:03');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,187.3, 73.3, '2013-02-27 13:00:23', '2013-12-23 09:00:23');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,183.3, 77.4, '2013-03-28 14:32:43', '2013-12-23 14:32:43');
INSERT INTO userdata  (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,179.0, 75.9, '2013-03-29 20:21:03', '2013-12-26 20:21:03');


INSERT INTO userdatablood (USERID, GTP, HDL, LDL, TG, FPG, CREATE_TIME, MODIFIED_TIME) values (1,56.3, 71.9, 60.1, 80.2, 87.3, '2013-03-29 20:21:03', '2013-12-26 20:21:03');
