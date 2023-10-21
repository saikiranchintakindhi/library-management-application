

# Library Management System Project


This Java application is a simple CRUD (Create, Read, Update, Delete) system for managing books in a library. 
It uses Java JDBC to interact with an SQL database(MYSQL).
 Below are the instructions to set up and run the application.

 # Prerequisites
Java Development Kit (JDK) installed on your system.
An SQL database server (e.g MySQL) up and running.
JDBC driver for your database installed and included in the project.

# note
Configure your database connection in the LibraryDB.java file. 
GO to LibraryDB class and 
Modify the connection URL, username, and password according to your database setup.

String url = "jdbc:mysql://localhost:3306/librarydb";
String username = "your-username";
String password = "your-password";
change username and password based on your MySQL username and password



# Script

Now,open mysql workbench and write below query

CREATE DATABASE library;
USE library;

CREATE TABLE books (
  title VARCHAR(100) NOT NULL,
  author VARCHAR(100) NOT NULL,
  isbn VARCHAR(13) PRIMARY KEY,
  publication_year INT NOT NULL,
  genre VARCHAR(100) 
);

## Setup

1. Set up a SQL database (MySQL) and create a database called `library`.
2. Run the SQL script provided in `Step 2` of the project setup to create the `books` table.

## Running the Application

1. Clone this repository to your local drive.
2. Open the project in  Java IDE (e.g., Eclipse, IntelliJ IDEA).
3. Add the JDBC Driver to the project.
4. Configure the database connection in the `LibraryDB` class.
5. Run the `MainClass.java` class to start/run the application..this is the first step to start .

## Usage

- Follow the console - menu to perform CRUD operations on books in the library.


## Contributors

- SaiKiran Chintakindhi


Feel free to contribute to this project or report issues

email:saikiranchintakindhi@gmail.com
 
 ---------------------------------------------------------
 ##sample test case 1 :
 
 Library Management System Menu Items:
1. Add a book in Data Base
2. Display all books available in Data Base
3. Edit book details
4. Delete a book record
5. Exit
Enter your choice:2

o/p: The library is empty/No Records found.

----------------------------------------------------------
 ##sample test case 2 :
 
 Library Management System Menu Items:
1. Add a book in Data Base
2. Display all books available in Data Base
3. Edit book details
4. Delete a book record
5. Exit
Enter your choice:1

Enter the book details:
Enter Book Title: Tiger The king
Enter Book Author Name: john
Enter 13 digit ISBN: 1245784585125
Enter Book Publication Year: 1996
Genre: fiction

o/p:Book added successfully!
-----------------------------------------------------------
## sample test case 3

 Library Management System Menu Items:
1. Add a book in Data Base
2. Display all books available in Data Base
3. Edit book details
4. Delete a book record
5. Exit
Enter your choice:1

Enter the book details:
Enter Book Title: Tiger The king
Enter Book Author Name: john
Enter 13 digit ISBN: 1245784585125
Enter Book Publication Year: 1996
Genre: fiction

o/p: MainClass Error1: A book with this ISBN already exists. Please use a different ISBN.




 



