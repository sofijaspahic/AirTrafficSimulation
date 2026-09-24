package exceptions.file;

public class InvalidFileSectionException extends FileException {
	
	private static final long serialVersionUID = 1L;

	public InvalidFileSectionException() {
		super("File contains data outside expected AIRPORTS or FLIGHTS section.");
	}
	
}