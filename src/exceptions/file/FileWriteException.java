package exceptions.file;

public class FileWriteException extends FileException {

	private static final long serialVersionUID = 1L;

	public FileWriteException() {
		super("File cannot be saved.");
	}

	public FileWriteException(String filename) {
		super("File " + filename + " cannot be saved.");
	}

}