package exceptions;

public class BookDuplicatedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public BookDuplicatedException(String ISBN) {
		super("A book with ISBN (" + ISBN + ") already exists in the library.");
	}
}
