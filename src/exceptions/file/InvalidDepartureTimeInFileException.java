package exceptions.file;

public class InvalidDepartureTimeInFileException extends FileException {

	private static final long serialVersionUID = 1L;
	
	public InvalidDepartureTimeInFileException() {
		super("Departure time in file has invalid format.");
	}
	
}