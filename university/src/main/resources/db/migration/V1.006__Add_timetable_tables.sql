create table special_day_aud (id int4 not null, rev int4 not null, revtype int2, source_day date, target_day date, primary key (id, rev));
create table special_day (id int4 not null, source_day date, target_day date, primary key (id));

create table time_table_item_aud (id int4 not null, rev int4 not null, revtype int2, day_of_week int4, end_lesson time, start_lesson time, course_id int4, primary key (id, rev));
create table time_table_item (id int4 not null, day_of_week int4 not null, end_lesson time, start_lesson time, course_id int4, primary key (id));

alter table course add column semester_type int4 default 0, add column year int4 default 2022;
alter table course_aud add column semester_type int4 default 0, add column year int4 default 2022;

alter table if exists special_day_aud add constraint FK_special_day_aud_rev foreign key (rev) references revinfo;
alter table if exists time_table_item_aud add constraint FKtime_table_item_aud_rev foreign key (rev) references revinfo;
alter table if exists time_table_item add constraint FKtime_table_item_course foreign key (course_id) references course;