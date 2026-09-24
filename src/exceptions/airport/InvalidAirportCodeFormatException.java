package exceptions.airport;

public class InvalidAirportCodeFormatException extends AirportException {

	private static final long serialVersionUID = 1L;

	public InvalidAirportCodeFormatException() {
		super("Airport code must be exactly three uppercase letters.");
	}

}
