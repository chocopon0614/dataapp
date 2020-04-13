drop table userinformation;
drop table userdata;

create table userinformation(
  id integer generated by default as identity, 
  username varchar(50) not null, 
  password varchar(256) not null, 
  create_time timestamp DEFAULT CURRENT_TIMESTAMP, 
  modified_time timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE(username)
);

create table userdata(
  id integer generated by default as identity, 
  userid integer not null, 
  height integer, 
  weight integer, 
  create_time timestamp DEFAULT CURRENT_TIMESTAMP, 
  modified_time timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY(userid) REFERENCES userinformation(id) ON DELETE cascade
);


--sampledata
INSERT INTO USERINFORMATION (USERNAME, PASSWORD) values ('test1', 'password');

INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,181.3, 72.3, '2012-12-23 09:00:23', '2012-12-23 09:00:23');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,180.9, 73.4, '2012-12-23 14:32:43', '2012-12-23 14:32:43');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,183.0, 71.8, '2012-12-26 20:21:03', '2012-12-26 20:21:03');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,183.3, 72.3, '2012-12-24 09:00:23', '2012-12-24 09:00:23');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,184.9, 73.4, '2012-12-25 14:32:43', '2012-12-25 14:32:43');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,186.0, 75.8, '2012-12-27 20:21:03', '2012-12-27 20:21:03');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,185.3, 72.9, '2012-12-27 10:00:23', '2012-12-28 09:00:23');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,184.3, 73.2, '2012-12-28 14:32:43', '2012-12-29 14:32:43');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,176.0, 74.8, '2012-12-29 20:21:03', '2012-12-30 20:21:03');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,183.9, 72.3, '2012-12-30 11:00:23', '2012-12-30 22:00:23');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,184.9, 73.4, '2012-12-30 12:32:43', '2012-12-30 14:32:43');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,182.0, 65.8, '2013-01-27 20:21:03', '2013-12-26 20:21:03');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,187.3, 73.3, '2013-02-27 13:00:23', '2013-12-23 09:00:23');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,183.3, 77.4, '2013-03-28 14:32:43', '2013-12-23 14:32:43');
INSERT INTO USERDATA (USERID, HEIGHT, WEIGHT, CREATE_TIME, MODIFIED_TIME) values (1,179.0, 75.9, '2013-03-29 20:21:03', '2013-12-26 20:21:03');
