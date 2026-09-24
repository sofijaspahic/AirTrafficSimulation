package exceptions.file;

public class MissingFlightSectionException extends FileException{
	
	private static final long serialVersionUID = 1L;
	
	public MissingFlightSectionException() {
		super("File does not contain FLIGHTS section.");
	}
	
}

