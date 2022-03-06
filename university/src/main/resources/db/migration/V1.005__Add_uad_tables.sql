create table course_aud (id int4 not null, rev int4 not null, revtype int2, name varchar(255), primary key (id, rev));
create table course_students_aud (rev int4 not null, courses_id int4 not null, students_id int4 not null, revtype int2, primary key (rev, courses_id, students_id));
create table course_teachers_aud (rev int4 not null, courses_id int4 not null, teachers_id int4 not null, revtype int2, primary key (rev, courses_id, teachers_id));
create table revinfo (rev int4 not null, revtstmp int8, primary key (rev));
create table student_aud (id int4 not null, rev int4 not null, revtype int2, birthdate date, edu_id int4, name varchar(255), free_semesters int4, semester int4, primary key (id, rev));
create table teacher_aud (id int4 not null, rev int4 not null, revtype int2, birthdate date, name varchar(255), primary key (id, rev));
alter table if exists course_aud add constraint FK7_course_aud_rev foreign key (rev) references revinfo;
alter table if exists course_students_aud add constraint FK_course_studenst_aud_rev foreign key (rev) references revinfo;
alter table if exists course_teachers_aud add constraint FK_course_teachers_aud_rev foreign key (rev) references revinfo;
alter table if exists student_aud add constraint FK_student_aud_rev foreign key (rev) references revinfo;
alter table if exists teacher_aud add constraint FK_teacher_aud_rev foreign key (rev) references revinfo;
