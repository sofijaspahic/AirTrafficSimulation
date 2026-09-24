package model;

import exceptions.airport.EmptyAirportCodeException;
import exceptions.airport.EmptyAirportNameException;
import exceptions.airport.InvalidAirportCodeFormatException;
import exceptions.airport.InvalidAirportXCoordException;
import exceptions.airport.InvalidAirportYCoordException;

// zapis o jednom aerodromu, osnovni podaci
public class Airport {

	private String name;
	private String code;
	private int x;
	private int y;

	public Airport(String name, String code, int x, int y) throws EmptyAirportCodeException, EmptyAirportNameException,
			InvalidAirportCodeFormatException, InvalidAirportXCoordException, InvalidAirportYCoordException {

		validate(name, code, x, y);
		this.name = name.trim();
		this.code = code.trim();
		this.x = x;
		this.y = y;
	}

	private static void validate(String name, String code, int x, int y)
			throws EmptyAirportCodeException, EmptyAirportNameException, InvalidAirportCodeFormatException,
			InvalidAirportXCoordException, InvalidAirportYCoordException {

		if (name == null || name.trim().isEmpty())
			throw new EmptyAirportNameException();
		if (code == null || code.trim().isEmpty())
			throw new EmptyAirportCodeException();
		code = code.trim();
		if (!code.matches("[A-Z]{3}"))
			throw new InvalidAirportCodeFormatException();
		if (x < -180 || x > 180)
			throw new InvalidAirportXCoordException();
		if (y < -90 || y > 90)
			throw new InvalidAirportYCoordException();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Airport))
			return false;
		Airport a = (Airport) obj;
		return this.code.equals(a.code);
	}
	//ako su dva objekta jednako po equals() onda imaju isti hashCode()
	@Override
	public int hashCode() {
		return java.util.Objects.hashCode(this.code);  // dva aerodroma sa istim kodom imaju isti hashCode; null->0
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	

}
