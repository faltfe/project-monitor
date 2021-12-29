set @fullperson = 'fullperson';
set @company = 'Test Company';
set @project = 'Test Project';

-- Persons
insert into PERSON (NAME, EMAIL)
values ('plainperson', 'plain@person.de');

insert into PERSON (NAME, EMAIL)
values (@fullperson, 'full@person.de');

-- Vacation Quota
insert into VACATION_QUOTA (year, total, carryover, person_id)
values (YEAR(CURRENT_DATE()), 30, 0, select id from PERSON where name = @fullperson);

-- Projects
insert into PROJECT (TITLE)
values (@project);

-- Companies
insert into COMPANY (FEDERAL_STATE, NAME)
values ('Niedersachsen', @company);

-- OfficialHolidays 11 (+ 1 NI)
insert into PUBLIC_HOLIDAY (DATE, DESCRIPTION, FEDERAL_STATE)
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
insert into VACATION_ENTRY (START_DATE, END_DATE, STATUS, PERSON_ID)
VALUES ('2019-01-01', '2019-01-11', 'REQUESTED', select id from PERSON where name = @fullperson),
       ('2019-04-15', '2019-04-26', 'REQUESTED', select id from PERSON where name = @fullperson),
       ('2019-12-16', '2019-12-13', 'REQUESTED', select id from PERSON where name = @fullperson);

-- Relationships
update PERSON
set COMPANY_ID = (select id from COMPANY where COMPANY.NAME = @company)
where PERSON.NAME = @fullperson;

insert into PROJECT_PERSON (PROJECT_ID, PERSON_ID)
VALUES (select ID from PROJECT where TITLE = @project, select ID from PERSON where PERSON.NAME = @fullperson);