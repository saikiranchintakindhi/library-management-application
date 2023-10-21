package Com.Saikiran.Library.Project1;

import java.util.List;
import java.util.Scanner;

public class MainClass 
{
    private LibraryDB libraryDB;

    public MainClass()
    {
        libraryDB = new LibraryDB();
    }

    public void startMethod() throws NotUniqueBookException, InvalidISBNException, BookNotFoundException {
       
    	Scanner sc = new Scanner(System.in);

        while (true)
        {
            System.out.println("Library Management System Menu Items:");
            System.out.println("1. Add a book in Data Base");
            System.out.println("2. Display all books available in Data Base");
            System.out.println("3. Edit book details");
            System.out.println("4. Delete a book record");
            System.out.println("5. Exit");
            System.out.println("Enter your choice");

            int choice = sc.nextInt();
           sc.nextLine();

            switch (choice)
            {
                case 1:
                    // Add a book
                    System.out.println("Enter the book details:");
                    BookPojo newBook = new BookPojo();
                    System.out.print("Enter Book Title: ");
                    newBook.setTitle(sc.nextLine());
                    System.out.print("Enter Book Author Name: ");
                    newBook.setAuthor(sc.nextLine());
                    System.out.print("Enter 13 digit ISBN: ");
                    newBook.setIsbn(sc.nextLine());
                    System.out.print("Enter Book Publication Year: ");
                    newBook.setPublicationYear(sc.nextInt());
                    sc.nextLine(); // Consume the newline character
                    if(newBook.getPublicationYear()>=1000 && newBook.getPublicationYear()<=2024)
                    {
                    System.out.print("Genre: ");
                    newBook.setGenre(sc.nextLine());
                    libraryDB.addBook(newBook);
                    System.out.println("Book added successfully!");
                    break;
                    }
                    else
                    {
                    	System.out.println("please Enter valid published year in yyyy format)");
                    break;
                    }

                case 2:
                    // Display all books
                	 System.out.println("------------------------------------------------------------------------------------");
                    List<BookPojo> books = libraryDB.getAllBooks();
                    if (books.isEmpty()) 
                    {
                        System.out.println("The library is empty/No Records found.");
                    } else
                    {
                        System.out.println("List of all books in the library:");
                        
                        for (BookPojo book : books) 
                        {
                            System.out.println(book.getTitle() + " Book written by " + book.getAuthor()+" , published in " +book.getPublicationYear()+" , isbn is "+book.getIsbn());
                            System.out.println("------------------------------------------------------------------------------------");
                        }
                    }
                    break;

                case 3:
                	
                    // Edit book details
                    System.out.println("Enter the 13 digit ISBN  of the book you want to edit:");
                    String isbnToEdit = sc.nextLine();
                    BookPojo updatedBook = new BookPojo();
                    updatedBook.setIsbn(isbnToEdit);
                    System.out.print("Enter New Title: ");
                    updatedBook.setTitle(sc.nextLine());
                    System.out.print("Enter New Author: ");
                    updatedBook.setAuthor(sc.nextLine());
                    System.out.print("Enter New Publication Year: ");
                    updatedBook.setPublicationYear(sc.nextInt());
                    sc.nextLine(); // Consume the newline character
                    System.out.print("Enter New Genre: ");
                    updatedBook.setGenre(sc.nextLine());
                    if(updatedBook.getPublicationYear()>=1000 && updatedBook.getPublicationYear()<=2024)
                    {
                    libraryDB.editBook(isbnToEdit, updatedBook);
                    System.out.println("Book details updated successfully!");
                    break;
                    }
                    else
                    {
                    	System.out.println("please Enter valid published year in yyyy format)");
                        break;
                    }

                case 4:
                    // Delete a book
                    System.out.println("Enter the ISBN of the book you want to delete:");
                    String isbnToDelete = sc.nextLine();
                    libraryDB.deleteBook(isbnToDelete);
                    System.out.println("Book deleted successfully!");
                    break;

                case 5:
                    libraryDB.closeConnection();
                    System.out.println("Exiting the program.");
                   
                    return;
                    
                   

                default:
                    System.out.println("Invalid choice entered. please try again ");
            }
            System.out.println("____________________________________________________________________________________");
        }
    }

    public static void main(String[] args)  
    {
       
    	MainClass m = new MainClass();
        try 
        {
            m.startMethod();
        } catch (NotUniqueBookException e)
        {
            System.out.println("MainClass Error1: " + e.getMessage());
        } catch (InvalidISBNException e) 
        {
            System.out.println("MainClass Error2: " + e.getMessage());
        } catch (BookNotFoundException e)
        {
            System.out.println("MainClass Error3: " + e.getMessage());
        }
    }
}
