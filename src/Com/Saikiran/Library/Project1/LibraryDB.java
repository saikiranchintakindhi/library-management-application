package Com.Saikiran.Library.Project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryDB 
 {
	private Connection connection;

	public LibraryDB() 
	{
		
		String Url = "jdbc:mysql://localhost:3306/library";
		String UserName = "root";
		String Password = "tiger";
		try
		{
			connection = DriverManager.getConnection(Url, UserName, Password);
		} catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	
	// Modify the addBook method to throw NotUniqueBookException
	public void addBook(BookPojo book) throws NotUniqueBookException, InvalidISBNException, BookNotFoundException
	{
//		MainClass app = new MainClass();
		try {
			
			if(!isValidBook(book)) {
				System.out.println("please enter correct details.");
				 System.exit(0);
//				 app.startMethod();
//				throw new VoidSpaceException("please enter correct details.");
			}
			
			//to check isbn is 13 digit or not
			if (!isIsbnValid(book.getIsbn()))
			{  
				throw new InvalidISBNException("Invalid ISBN. ISBN must be a 13-digit number.");
			}
			//to check duplicate isbn or not 
			if (!isIsbnUnique(book.getIsbn())) 
			{
				throw new NotUniqueBookException("A book with this ISBN already exists. Please use a different ISBN.");
			}
		
	    
			String sql = "INSERT INTO books (title, author, isbn, publication_year, genre) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
			{
				preparedStatement.setString(1, book.getTitle());
				preparedStatement.setString(2, book.getAuthor());
				preparedStatement.setString(3, book.getIsbn());
				preparedStatement.setInt(4, book.getPublicationYear());
				preparedStatement.setString(5, book.getGenre());
				preparedStatement.executeUpdate();
			}
		} 
	    catch (SQLException e)
		{
			e.printStackTrace();
			// Handle database-related errors
		}
	}

	// Modify the editBook method to throw NotUniqueBookException
	public void editBook(String isbn, BookPojo updatedBook) throws NotUniqueBookException, InvalidISBNException, BookNotFoundException 
	{
		
		try {
		
			  if (!isIsbnValid(updatedBook.getIsbn())) 
			  {
			  System.out.println(updatedBook.getIsbn());
			  throw new InvalidISBNException("Invalid ISBN. Please enter a 13 digit valid ISBN.");
			  }
			  
			  // Check if a book with the provided ISBN exists
		        if (!doesBookExist(isbn)) 
		        {
		            throw new BookNotFoundException("Book with ISBN " + isbn + " not found. Cannot edit.");
		        }
		

			String sql = "UPDATE books SET title = ?, author = ?, publication_year = ?, genre = ? WHERE isbn = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) 
			{
				preparedStatement.setString(1, updatedBook.getTitle());
				preparedStatement.setString(2, updatedBook.getAuthor());
				preparedStatement.setInt(3, updatedBook.getPublicationYear());
				preparedStatement.setString(4, updatedBook.getGenre());
				preparedStatement.setString(5, isbn);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			
		}
	}

	public List<BookPojo> getAllBooks() 
	{
		List<BookPojo> books = new ArrayList<>();
		String sql = "SELECT * FROM books";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) 
	  {
			while (resultSet.next()) 
			   {
				BookPojo book = new BookPojo();
				book.setTitle(resultSet.getString("title"));
				book.setAuthor(resultSet.getString("author"));
				book.setIsbn(resultSet.getString("isbn"));
				book.setPublicationYear(resultSet.getInt("publication_year"));
				book.setGenre(resultSet.getString("genre"));
				books.add(book);
			   }
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return books;
	}

   public void deleteBook(String isbn) throws BookNotFoundException 
   {
		
		// Check if a book with the provided ISBN exists
        if (!doesBookExist(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found. Cannot Delete.");
        }
		String sql = "DELETE FROM books WHERE isbn = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) 
		{
			preparedStatement.setString(1, isbn);
			preparedStatement.executeUpdate();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	// Close the database connection when the program exits
	public void closeConnection()
	{
		try 
		{
			if (connection != null) 
			{
				connection.close();
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	
	// Add a new method in LibraryDatabase to check for duplicate ISBNs
		public boolean isIsbnUnique(String isbn) 
		{
			String sql = "SELECT COUNT(*) FROM books WHERE isbn = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
			{
				preparedStatement.setString(1, isbn);
				try (ResultSet resultSet = preparedStatement.executeQuery()) 
				{
					if (resultSet.next())
					{
						int count = resultSet.getInt(1);
						if( count == 0) { 
							return true;    //  .i.e isbn is not available/no duplicate isbn (true means) in previous record
						}
					}
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			return false; // An error occurred, so treat it as a non-unique ISBN
		}

	// isbn 13 digit or not helper method
	public boolean isIsbnValid(String isbn) throws InvalidISBNException {
		System.out.println();
		if (isbn.length() != 13 )
		{
			return false;
		}
		return true; // ISBN is valid   // if isbn.length is 13 we return true;
	}
	
	 //to check void spaces have or not ...in the user input
    public static boolean isValidString(String str) {
        if (str != null) {
            str = str.trim();
            if (str.length() > 0)
                return true;
        }
        return false;
    }
    
    //to check void spaces have or not ...in the user input
    public static boolean isValidBook(BookPojo book) {
    
    	if(!isValidString(book.getAuthor())) {
    		return false;
    	}
    	if(!isValidString(book.getIsbn())) {
    		return false;
    	}
    	if(!isValidString(book.getTitle())) {
    		return false;
    	}
    	if(!isValidString(book.getGenre())) {
    		return false;
    	}
    	
    	   return true;
    }
	
	//to check book exist in table or not 
	public boolean doesBookExist(String isbn) 
	{
	    String sql = "SELECT COUNT(*) FROM books WHERE isbn = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
	    {
	        preparedStatement.setString(1, isbn);
	        try (ResultSet resultSet = preparedStatement.executeQuery())
	        {
	            if (resultSet.next()) 
	            {
	                int count = resultSet.getInt(1);
	                if( count > 0) 
	                {
	                	return true;      // Return true if a book with the ISBN exists
	                }
	                
	            }
	        }
	    } 
	    catch (SQLException e) 
	    {
	        e.printStackTrace();
	    }
	    return false; // An error occurred, so treat it as if the book doesn't exist
	}

}
