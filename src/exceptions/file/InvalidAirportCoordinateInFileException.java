package exceptions.file;

public class InvalidAirportCoordinateInFileException extends FileException {

	private static final long serialVersionUID = 1L;
	
	public InvalidAirportCoordinateInFileException() {
		super("Airport coordinates in file must be valid numbers.");
	}
	
}
