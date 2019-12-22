create table contact (
id identity not null,
name varchar not null
);

create table application (
id identity not null,
contact_id bigint not null,
product_name varchar not null,
created_date_time bigint not null,
foreign key (contact_id) references contact(id)
);

create index application_contact_id_idx on application(contact_id);
