create table enrolleruser
(
    userid varchar(100) not null,
    documenttype varchar(100) not null,
    documentnumber varchar(100) not null,
    gender varchar(10) not null,
    branchoffice varchar(100) not null,
    creationdate datetime not null,
    modificationdate datetime null
);

create unique index enrolleruser_userid_uindex
    on enrolleruser (userid);

alter table enrolleruser
    add constraint enrolleruser_pk
        primary key (userid);