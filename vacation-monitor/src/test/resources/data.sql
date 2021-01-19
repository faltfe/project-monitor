insert into person (name, email) values ('plainperson', 'plain@person.de');

insert into person (name, email) values ('fullperson', 'full@person.de');
insert into vacation_quota (year, total, carryover, person_id) values (YEAR(CURRENT_DATE()), 30, 0, select id from person where name = 'fullperson');
