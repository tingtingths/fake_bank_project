-- create account
insert into account values (12345678, 'Eric Chan');
insert into account values (88888888, 'Jason Ma');

-- add initial balance
insert into transaction (ID, DEST_ACCOUNT_ID, AMOUNT) values (1, 12345678, 1000000);
insert into transaction (ID, DEST_ACCOUNT_ID, AMOUNT) values (2, 88888888, 1000000);

commit;
