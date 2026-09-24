package exceptions.file;

public class DuplicateAirportInFileException extends FileException {

	private static final long serialVersionUID = 1L;

	public DuplicateAirportInFileException(String code) {
		super("File contains duplicate airport code: " + code + ".");
	}
}
