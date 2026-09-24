package exceptions.airport;

public class DuplicateAirportCodeException extends AirportException {

	private static final long serialVersionUID = 1L;

	public DuplicateAirportCodeException() {
		super("Airport code already exists.");
	}

}
