package com.example.demo.database

import java.sql.Connection
import java.sql.DriverManager

/*
*
DROP DATABASE IF EXISTS gestacad;
CREATE DATABASE gestacad;
DROP USER IF EXISTS administrador;
CREATE USER administrador IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON gestacad.* TO administrador WITH GRANT OPTION;
USE gestacad;

CREATE TABLE person (
id int primary key auto_increment,
name VARCHAR(32) not null,
title VARCHAR(64) NOT NULL,
date_deleted int unsigned not null default 0)ENGINE=InnoDB;

INSERT INTO person VALUE (0,'Josep Vila','Enginyer informatica');
INSERT INTO person VALUE (0,'Ninel Mariana','Doctora fisica');
INSERT INTO person VALUE (0,'Joan marquez','Artista');
SELECT * FROM person;

ALTER TABLE person CHANGE nom name varchar(32);
alter table person add column date_delete int unsigned not null default 0 after title;
alter table person change column  date_deleted date_deleted bigint unsigned default 0;
*
* */

const val MYSQL_URL = "jdbc:mysql://localhost:3306/gestacad"
const val MYSQL_USER  = "root"
const val MYSQL_PASSWORD  = "admin"

object Database {
    var connection: Connection

    init {
//        Class.forName("com.mysql.jdbc.Driver")
        connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
    }

    fun closeConnection() {
        if (!connection.isClosed)
            connection.close()
    }
}