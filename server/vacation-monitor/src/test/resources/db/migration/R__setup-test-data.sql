-- Persons
insert
into person (name, email)
values ('plainperson', 'plain@person.de');

insert into person (name, email)
values ('Max Full', 'full@person.de');

-- Vacation Quota
insert into vacation_quota (year, total, carryover, person_id)
values (date_part('year', current_date), 30, 0,
        (select id
         from person
         where name = 'Max Full'));

-- Projects
insert into project (title)
values ('Test project');

-- Companies
insert into company (federal_state, name)
values ('Niedersachsen', 'Test company');

-- OfficialHolidays 11 (+ 1 NI)
insert into public_holiday (date, description, federal_state)
VALUES ('2019-01-01', 'Neujahr', null),
       ('2019-01-02', 'Fake', 'NI'),
       ('2019-04-19', 'Karfreitag', null),
       ('2019-04-21', 'Ostersonntag', null),
       ('2019-04-22', 'Ostermontag', null),
       ('2019-05-01', 'Tag der Arbeit', null),
       ('2019-05-30', 'Christi Himmelfahrt', null),
       ('2019-06-09', 'Pfingstsonntag', null),
       ('2019-06-10', 'Pfingstmontag', null),
       ('2019-10-03', 'Tag der dt. Einheit', null),
       ('2019-12-25', '1. Weihnachtsfeiertag', null),
       ('2019-12-26', '2. Weihnachtsfeiertag', null);

-- VacationEntries
insert into vacation_entry (start_date, end_date, status, person_id)
VALUES ('2019-01-01', '2019-01-11', 'REQUESTED', (select id from person where name = 'Max Full')),
       ('2019-04-15', '2019-04-26', 'REQUESTED', (select id from person where name = 'Max Full')),
       ('2019-12-16', '2019-12-13', 'REQUESTED', (select id from person where name = 'Max Full'));

-- Relationships
update person
set company_id = (select id from company where company.name = 'Test company')
where person.name = 'Max Full';

insert into project_person (project_id, person_id)
VALUES ((select id from project where title = 'Test project'), (select id from person where person.name = 'Max Full'));