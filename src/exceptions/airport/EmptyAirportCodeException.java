package exceptions.airport;

public class EmptyAirportCodeException extends AirportException {
	
	private static final long serialVersionUID = 1L;

	public EmptyAirportCodeException() {
		super("Airport code cannot be empty.");
	}

}
