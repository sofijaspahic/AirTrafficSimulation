package exceptions.file;

public class UnknownAirportInFileException extends FileException{

	private static final long serialVersionUID = 1L;
	
	public UnknownAirportInFileException(String code) {
		super("Flight contains unknown airport code: " + code + ".");
	}
	
}


