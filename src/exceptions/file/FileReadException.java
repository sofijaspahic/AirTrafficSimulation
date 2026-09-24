package exceptions.file;

public class FileReadException extends FileException {

	private static final long serialVersionUID = 1L;

	public FileReadException() {
		super("File cannot be read.");
	}

	public FileReadException(String filename) {
		super("File " + filename + " cannot be read.");
	}

}