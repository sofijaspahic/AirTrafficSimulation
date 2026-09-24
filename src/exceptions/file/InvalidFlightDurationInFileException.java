package exceptions.file;

public class InvalidFlightDurationInFileException extends FileException {

	private static final long serialVersionUID = 1L;

	public InvalidFlightDurationInFileException() {
		super("Flight duration in file must be a valid number.");
	}
	
}