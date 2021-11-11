create table list_item (id int8 generated by default as identity, completed TIMESTAMP WITHOUT TIME ZONE, created TIMESTAMP WITHOUT TIME ZONE, title varchar(255), updated TIMESTAMP WITHOUT TIME ZONE, list_id int8 not null, primary key (id))
create table roles (id int8 generated by default as identity, name varchar(255), primary key (id))
create table user_roles (user_id int8 not null, role_id int8 not null, primary key (user_id, role_id))
create table user_list (id int8 generated by default as identity, created TIMESTAMP WITHOUT TIME ZONE, title varchar(255), updated TIMESTAMP WITHOUT TIME ZONE, user_id int8 not null, primary key (id))
create table users (id int8 generated by default as identity, created TIMESTAMP WITHOUT TIME ZONE, updated TIMESTAMP WITHOUT TIME ZONE, email varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255), primary key (id))
alter table if exists list_item add constraint FKhj7k9b19lnk4d9biyjlvup813 foreign key (list_id) references user_list
alter table if exists user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles
alter table if exists user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users
alter table if exists user_list add constraint FKlw2i9i54j0u3y25hk5sx2vq9w foreign key (user_id) references users
create table list_item (id int8 generated by default as identity, completed TIMESTAMP WITHOUT TIME ZONE, created TIMESTAMP WITHOUT TIME ZONE, title varchar(255), updated TIMESTAMP WITHOUT TIME ZONE, list_id int8 not null, primary key (id))
create table roles (id int8 generated by default as identity, name varchar(255), primary key (id))
create table user_roles (user_id int8 not null, role_id int8 not null, primary key (user_id, role_id))
create table user_list (id int8 generated by default as identity, created TIMESTAMP WITHOUT TIME ZONE, title varchar(255), updated TIMESTAMP WITHOUT TIME ZONE, user_id int8 not null, primary key (id))
create table users (id int8 generated by default as identity, created TIMESTAMP WITHOUT TIME ZONE, updated TIMESTAMP WITHOUT TIME ZONE, email varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255), primary key (id))
alter table if exists list_item add constraint FKhj7k9b19lnk4d9biyjlvup813 foreign key (list_id) references user_list
alter table if exists user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles
alter table if exists user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users
alter table if exists user_list add constraint FKlw2i9i54j0u3y25hk5sx2vq9w foreign key (user_id) references users
create table list_item (id int8 generated by default as identity, completed TIMESTAMP WITHOUT TIME ZONE, created TIMESTAMP WITHOUT TIME ZONE, title varchar(255), updated TIMESTAMP WITHOUT TIME ZONE, list_id int8 not null, primary key (id))
create table roles (id int8 generated by default as identity, name varchar(255), primary key (id))
create table user_roles (user_id int8 not null, role_id int8 not null, primary key (user_id, role_id))
create table user_list (id int8 generated by default as identity, created TIMESTAMP WITHOUT TIME ZONE, title varchar(255), updated TIMESTAMP WITHOUT TIME ZONE, user_id int8 not null, primary key (id))
create table users (id int8 generated by default as identity, created TIMESTAMP WITHOUT TIME ZONE, updated TIMESTAMP WITHOUT TIME ZONE, email varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255), primary key (id))
alter table if exists list_item add constraint FKhj7k9b19lnk4d9biyjlvup813 foreign key (list_id) references user_list
alter table if exists user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles
alter table if exists user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users
alter table if exists user_list add constraint FKlw2i9i54j0u3y25hk5sx2vq9w foreign key (user_id) references users
