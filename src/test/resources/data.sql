insert into cty_city (id, create_date, created_by, update_date, updated_by, country_code, latitude, longitude, name)
values (1, null, 1, null, 1, 'TR', 0 ,0 , 'Ankara');
insert into pol_pollution (id, create_date, created_by, update_date, updated_by, co, "date", o3, so2, id_cty_city)
values (1, null, 1, null, 1, 0, 22-02-2021, 0, 0, 1);
insert into usr_user (id, create_date, created_by, update_date, updated_by, first_name, last_name, password, username)
values (1, null, null, null, null, 'John', 'Doe', 'oldpassword', 'John Doe');