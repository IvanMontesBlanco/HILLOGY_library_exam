package exceptions;

public class ISBNInvalidException extends Exception {
	private static final long serialVersionUID = 1L;

	public ISBNInvalidException(String ISBN) {
		super(ISBN + " is not a valid ISBN number.");
	}
}
