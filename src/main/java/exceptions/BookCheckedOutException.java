package exceptions;

public class BookCheckedOutException extends Exception {
	private static final long serialVersionUID = 1L;

	public BookCheckedOutException(String ISBN) {
		super("Book with ISBN (" + ISBN + ") is currently checked out.");
	}
}