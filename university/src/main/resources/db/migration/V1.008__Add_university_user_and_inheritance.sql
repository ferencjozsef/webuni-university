alter table student drop column name, drop column birthdate;
alter table teacher drop column name, drop column birthdate;

create table university_user (id int4 not null, birthdate date, name varchar(255), password varchar(255), username varchar(255), primary key (id));
alter table if exists student add constraint FK_student_university_user foreign key (id) references university_user;
alter table if exists teacher add constraint FK_teacher_university_user foreign key (id) references university_user;

create table university_user_aud (id int4 not null, rev int4 not null, revtype int2, birthdate date, name varchar(255), password varchar(255), username varchar(255), primary key (id, rev));
alter table if exists student_aud add constraint FK_student_aud_university_user_aud foreign key (id, rev) references university_user_aud;
alter table if exists teacher_aud add constraint FK_teacher_aud_university_user_aud foreign key (id, rev) references university_user_aud;

alter table if exists university_user_aud add constraint FK_university_user_aud_rev foreign key (rev) references revinfo;


