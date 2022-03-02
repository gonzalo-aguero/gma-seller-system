# GMA SELLER SYSTEM
It is a small management software where you can keep up to date the stock of all your products and the records of the sales made.

## Installation
The software needs the .jar file (the software itself), an image folder named "img", the .env file for configuration and a file named "database.db" where the database will be hosted in case of using SQLite (this file will be created automatically).

For small businesses it will suffice to use SQLite. However, for medium or large companies it will be better to use MySQL. For this just go to the ".env" file and change the value of the "DB_TYPE" variable from "sqlite" to "mysql". Next, in the same file, put the server, database name, username, and password in the following variables.

The software will take care of creating the necessary tables when it starts up.
