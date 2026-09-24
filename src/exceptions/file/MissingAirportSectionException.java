package exceptions.file;

public class MissingAirportSectionException extends FileException{
	
	private static final long serialVersionUID = 1L;
	
	public MissingAirportSectionException() {
		super("File does not contain AIRPORTS section.");
	}
	
}
