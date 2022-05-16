insert into role(name) value('admin');
insert into role(name) value('regular');

insert into authority(name) value('manage');
insert into authority(name) value('normal');

insert into role_authority(rid, aid)
value(
    (select id from role where name = 'admin'),
    (select id from authority where name = 'manage')
);
insert into role_authority(rid, aid)
value(
    (select id from role where name = 'regular'),
    (select id from authority where name = 'normal')
);

-- the password of user 'boss' is '007'
insert into user(username, password) value('boss', '$2a$10$i7GgiUHYcjOkcC6Ma/2bReQIlt1b6NzcCzFT4STkxOtzlExebFu8u');
insert into user_role(uid, rid) value(
    (select id from user where username = 'boss'),
    (select id from role where name = 'admin')
);