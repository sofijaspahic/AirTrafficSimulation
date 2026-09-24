package io;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import exceptions.file.DuplicateAirportInFileException;
import model.Airport;
import model.Flight;

// konvertuje jedan Airport/Flight <-> String
// struktuira ceo fajl
public abstract class Formatter {

	public abstract String serializeAirport(Airport a);
	public abstract String serializeFlight(Flight l);
	public abstract Airport deserializeAirport(String line) throws Exception;
	public abstract Flight deserializeFlight(String line, List<Airport> airports) throws Exception;
	public abstract void readAll(BufferedReader br, List<Airport> airports, List<Flight> flights) throws Exception;
	public abstract void writeAll(PrintWriter pw, List<Airport> airports, List<Flight> flights) throws Exception;
	public abstract String getExtension();

	// zajednicka metoda
	protected void addAirport(Airport airport, List<Airport> airports, Set<String> seenCodes) throws DuplicateAirportInFileException {
		if (!seenCodes.add(airport.getCode())) {
			throw new DuplicateAirportInFileException(airport.getCode());
		}
		airports.add(airport);
	}

}
