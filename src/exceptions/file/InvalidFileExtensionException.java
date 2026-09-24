package exceptions.file;

public class InvalidFileExtensionException extends FileException {

	private static final long serialVersionUID = 1L;

	public InvalidFileExtensionException(String expectedExtension) {
		super("Invalid file extension. Expected ." + expectedExtension + " file.");
	}

}
