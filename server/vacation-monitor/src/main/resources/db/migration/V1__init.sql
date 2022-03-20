
create table company (
                         id  bigserial not null,
                         federal_state varchar(255),
                         name varchar(255) not null,
                         primary key (id)
);

create table person (
                        id  bigserial not null,
                        email varchar(255) not null,
                        name varchar(255) not null,
                        company_id int8,
                        primary key (id)
);

create table project (
                         id  bigserial not null,
                         title varchar(255) not null,
                         primary key (id)
);

create table project_person (
                                project_id int8 not null,
                                person_id int8 not null,
                                primary key (project_id, person_id)
);

create table public_holiday (
                                id  bigserial not null,
                                date date not null,
                                description varchar(255) not null,
                                federal_state varchar(255),
                                primary key (id)
);

create table vacation_entry (
                                id  bigserial not null,
                                end_date date not null,
                                start_date date not null,
                                status varchar(255),
                                person_id int8 not null,
                                primary key (id)
);

create table vacation_quota (
                                carryover int4 not null,
                                total int4 not null,
                                year int4 not null,
                                person_id int8 not null,
                                primary key (person_id)
);

alter table company
    add constraint UK_niu8sfil2gxywcru9ah3r4ec5 unique (name);

alter table person
    add constraint UK_fwmwi44u55bo4rvwsv0cln012 unique (email);

alter table project
    add constraint UK_etb9i6krbg45bl5o1kt0cc4q8 unique (title);

alter table public_holiday
    add constraint UKqq4tk2lf3jcgcnd3buqh5lbm9 unique (date, description);

alter table vacation_quota
    add constraint UKtbj20hsqy6f0hnq1e4xfhp8q5 unique (person_id, year);

alter table person
    add constraint FKq9cb6xydynxpqhnmif18lmx83
        foreign key (company_id)
            references company;

alter table project_person
    add constraint FKo41dah035166kl020qbowl43t
        foreign key (person_id)
            references person;

alter table project_person
    add constraint FKhevr64jgvdqxi3pxxf0wvu1gb
        foreign key (project_id)
            references project;

alter table vacation_entry
    add constraint FKqpmeqyxc5kindct1i4day5k09
        foreign key (person_id)
            references person;

alter table vacation_quota
    add constraint FK8xgghbdoseuw93ox6s7r927cc
        foreign key (person_id)
            references person;
