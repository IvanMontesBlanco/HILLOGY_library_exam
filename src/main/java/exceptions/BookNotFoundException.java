package exceptions;

public class BookNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public BookNotFoundException(String ISBN) {
		super("The library does not have a book with ISBN (" + ISBN + ").");
	}
}
