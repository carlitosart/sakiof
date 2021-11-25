alter table customer
    add password varchar(60) not null default '2a$10$5X0YtLyGWFJMGn4E.BV3heS8yB3KaZ8dJI2pegv2LU77//tq.Ydiq';

ALTER TABLE film ADD FULLTEXT(title);

ALTER TABLE actor ADD FULLTEXT(first_name,last_name);