package com.app.gorent.data.storage.sql;

class StatementsSQL {

    static final String tableCategories = "create table categories(\n" +
            "\tid text primary key not null,\n" +
            "\tname text not null,\n" +
            "\tdescription text\n" +
            ");";

    static final String tableUsers = "create table users(\n" +
            "\temail text primary key not null,\n" +
            "\tpassword text not null,\n" +
            "\tfull_name text not null,\n" +
            "\trole text not null\n" +
            ");";

    static final String tableItems = "create table items(\n" +
            "\tid text primary key not null,\n" +
            "\tname text not null,\n" +
            "\tdescription text not null,\n" +
            "\tprice integer not null,\n" +
            "\tfeeType text not null,\n" +
            "\tisRent integer default 0,\n" +
            "\tisDeleted integer default 0,\n" +
            "\timage_path text not null,\n" +
            "\titemOwnerEmail text not null,\n" +
            "\tcategoryId text not null,\n" +
            "\tsynchronized integer default 0,\n" +
            "\tforeign key(itemOwnerEmail) references users(email),\n" +
            "\tforeign key(categoryId) references categories(id)\n" +
            ");";

    static final String tableItemLending ="create table itemLending(\n" +
            "\titemLendingId text primary key not null,\n" +
            "\tlendingDate text not null,\n" +
            "\tdueDate text not null,\n" +
            "\treturnDate text,\n" +
            "\ttotalPrice integer not null,\n" +
            "\tdelivery_address text not null,\n" +
            "\trenterEmail text not null,\n" +
            "\titemId text not null,\n" +
            "\tforeign key(renterEmail) references users(email),\n" +
            "\tforeign key(itemId) references items(id)\n" +
            ");";

    static final String dropItemLending = "drop table if exists itemLending;";
    static final String dropItems = "drop table if exists items;";
    static final String dropCategories = "drop table if exists categories;";
    static final String dropUsers = "drop table if exists users;";

    static final String insertUser="insert into users(email, password, full_name, role) values('%s','%s','%s','%s');";

    static final String insertCategory = "insert into category(id, name, description) values('%s','%s','%s');";

    static final String login="select * from users where email='%s' and password='%s';";

    static final String findUserByEmail = "select email, full_name, role from users where email='%s'";
}