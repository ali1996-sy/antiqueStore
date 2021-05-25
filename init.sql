Drop Schema project Cascade; 
CREATE SCHEMA if not exists project;
CREATE TABLE project.addresses(
address_id SERIAL PRIMARY KEY, 
street VARCHAR (50) ,
building_number VARCHAR (50) ,
post_code INTEGER ,
city VARCHAR (50) ,
country VARCHAR(50)

);
CREATE TABLE project.users(
user_id SERIAL PRIMARY KEY ,  
last_name VARCHAR (50) ,
first_name VARCHAR (50) , 
address_id INTEGER REFERENCES project.addresses (address_id) ,
email VARCHAR (100) ,
registration_date timestamp ,
validate_registration BOOLEAN ,
is_administrator BOOLEAN ,
is_antique_dealer BOOLEAN ,
username VARCHAR (50)  , 
password VARCHAR (60) ,
photo VARCHAR (50) ,
sell_furniture_number INTEGER  ,
buy_furniture_number INTEGER 
);

CREATE TABLE  project.furniture_types(
furniture_type_id SERIAL PRIMARY KEY,  
name VARCHAR (25) 
);

CREATE TABLE  project.deliveries(
delivery_id SERIAL PRIMARY KEY,
delivery_address VARCHAR (50)  ,					   
buyer INTEGER REFERENCES project.users (user_id) 
);


CREATE TABLE  project.furniture(
furniture_id SERIAL PRIMARY KEY,  
deposit_date date  ,
selling_price DOUBLE PRECISION,
special_sale_price DOUBLE PRECISION,
state  VARCHAR(50),
date_of_sale date ,
purchase_price DOUBLE PRECISION,
buyer INTEGER REFERENCES project.users (user_id) ,
delivery INTEGER REFERENCES project.deliveries (delivery_id) ,
request_for_visit integer ,
withdrawal_date date ,
favorite_photo INTEGER ,
description VARCHAR (100),
furniture_type Integer REFERENCES project.furniture_types (furniture_type_id) ,
furniture_collection_date date
);
CREATE TABLE  project.photos(
photo_id SERIAL PRIMARY KEY,
photo VARCHAR (10000000) ,					   
furniture INTEGER REFERENCES project.furniture(furniture_id) 
);
ALTER TABLE project.furniture add
    CONSTRAINT  photo_id
    FOREIGN KEY (favorite_photo) REFERENCES project.photos;
	
CREATE TABLE project.options(
option_id SERIAL PRIMARY KEY, 
buyer INTEGER REFERENCES project.users (user_id) ,
furniture INTEGER REFERENCES project.furniture(furniture_id) ,
state  VARCHAR(50),
option_term integer, 
date_term timestamp

);




CREATE TABLE  project.visit_requests(
visit_id SERIAL PRIMARY KEY,
request_date varchar(60) ,
time_slot VARCHAR (100),
furniture_address INTEGER REFERENCES project.addresses (address_id) ,
state  VARCHAR(50),
collections_due VARCHAR (50)  ,
chosen_date varchar(60) ,
immediatly_avialable VARCHAR (50)  ,
seller INTEGER REFERENCES project.users (user_id) 
);
ALTER TABLE project.furniture add
    CONSTRAINT  visit_id
    FOREIGN KEY (request_for_visit) REFERENCES project.visit_requests;

insert into project.furniture_types (furniture_type_id, name) VALUES (1, 'Cabinet');
insert into project.furniture_types (furniture_type_id, name) VALUES (2, 'Sideboard');
insert into project.furniture_types (furniture_type_id, name) VALUES (3, 'Bookcase');
insert into project.furniture_types (furniture_type_id, name) VALUES (4, 'Hosier');
insert into project.furniture_types (furniture_type_id, name) VALUES (5, 'Buffet');
insert into project.furniture_types (furniture_type_id, name) VALUES (6, 'Desk');
insert into project.furniture_types (furniture_type_id, name) VALUES (7, 'Chair');
insert into project.furniture_types (furniture_type_id, name) VALUES (8, 'Chiffonier');
insert into project.furniture_types (furniture_type_id, name) VALUES (9, 'Chest');
insert into project.furniture_types (furniture_type_id, name) VALUES (10, 'Vanity');
insert into project.furniture_types (furniture_type_id, name) VALUES (11, 'Dresser');
insert into project.furniture_types (furniture_type_id, name) VALUES (12, 'Confidante');
insert into project.furniture_types (furniture_type_id, name) VALUES (13, 'Console');
insert into project.furniture_types (furniture_type_id, name) VALUES (14, 'Dressoir');
insert into project.furniture_types (furniture_type_id, name) VALUES (15, 'Armchair');
insert into project.furniture_types (furniture_type_id, name) VALUES (16, 'Gueridon');
insert into project.furniture_types (furniture_type_id, name) VALUES (17, 'Linen cupboard');
insert into project.furniture_types (furniture_type_id, name) VALUES (18, 'Bed');
insert into project.furniture_types (furniture_type_id, name) VALUES (19, 'Wardrobe');
insert into project.furniture_types (furniture_type_id, name) VALUES (20, 'Secretary');
insert into project.furniture_types (furniture_type_id, name) VALUES (21, 'Table');
insert into project.furniture_types (furniture_type_id, name) VALUES (22, 'Stool');
insert into project.furniture_types (furniture_type_id, name) VALUES (23, 'China cabinet');
insert into project.furniture_types (furniture_type_id, name) VALUES (24, 'Clothes valet');









