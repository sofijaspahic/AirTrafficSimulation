package exceptions.file;

public class InvalidAirportFormatException extends FileException {

	private static final long serialVersionUID = 1L;
	
	public InvalidAirportFormatException() {
		super("Invalid airport format in file.");
	}
	
}
