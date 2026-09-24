package exceptions.file;

public class InvalidFlightFormatException extends FileException {

	private static final long serialVersionUID = 1L;
	
	public InvalidFlightFormatException() {
		super("Invalid flight format in file.");
	}
	
}