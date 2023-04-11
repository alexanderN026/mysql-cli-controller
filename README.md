# MySQL CLI Controller

## Description

***

This project allows you to perform the following console actions:

* Show, add, modify and remove a product
* Show, add, modify and remove an order
* Show all products that match orders

## Setup of the MySQL database management system

***

* Download MySQL, MySQL workbench, install it and give the root user your chosen password
* Create the database or schema cli_controller

Create the tables products and orders with these templates:

`create table cli_controller.products`\
`(`\
`id int auto_increment`\
`primary key,`\
`name  varchar(60) not null,`\
`price decimal(10, 2) not null,`\
`constraint id_UNIQUE`\
`unique (id),`\
`constraint products_pk`\
`unique (name)`\
`);`

`create table cli_controller.orders`\
`(`\
`id int auto_increment`\
`primary key,`\
`product_id int not null,`\
`quantity int not null,`\
`total_price decimal(10, 2) not null,`\
`constraint orders_pk`\
`unique (id),`\
`constraint orders_products_id_fk`\
`foreign key (product_id) references cli_controller.products (id)`\
`);`

## Setup of the .env file

***

The .env file must be created in the same location as the pom.xml file and contain the following:

`URL=jdbc:mysql://localhost:3306/cli_controller`\
`USER_NAME=root`\
`PASSWORD=****`

## Menu interaction

***

Press Enter to confirm all actions

* Enter the number you want in the main menu to access the submenu
* To return to the main menu, press `esc` in the submenus