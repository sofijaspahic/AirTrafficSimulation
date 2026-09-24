package io;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Airport;
import model.Flight;
import exceptions.file.*;

// zna kako izgledaju aerodromi i letovi u CSV formatu
// deli sekcije # AIRPORTS / # FLIGHTS	i primena (de)serialize na jednu liniju za svaki objekat
public class CSVFormatter extends Formatter {

	// obj -> str

	@Override
	public String serializeAirport(Airport a) {
		return a.getCode() + "," + a.getName() + "," + a.getX() + "," + a.getY();
	}

	@Override
	public String serializeFlight(Flight f) {
		return f.getDepartureAirport().getCode() + "," + f.getArrivalAirport().getCode() + ","
				+ f.getFullDepartureTime() + "," + f.getDurationMinutes();
	}

	// str -> obj

	@Override
	public Airport deserializeAirport(String line) throws Exception {
		String parts[] = line.split(",");
		if (parts.length != 4)
			throw new InvalidAirportFormatException();
		try {
			String code = parts[0].trim();
			String name = parts[1].trim();
			int x = Integer.parseInt(parts[2].trim());
			int y = Integer.parseInt(parts[3].trim());
			return new Airport(name, code, x, y);
		} catch (NumberFormatException e) {
			throw new InvalidAirportCoordinateInFileException();
		}
	}

	@Override
	public Flight deserializeFlight(String line, List<Airport> airports) throws Exception {
		String parts[] = line.split(",");
		if (parts.length != 4)
			throw new InvalidFlightFormatException();
		String subparts[] = parts[2].split(":");
		if (subparts.length != 2)
			throw new InvalidDepartureTimeInFileException();
		Airport dep = null, arr = null;
		for (Airport a : airports) {
			if (a.getCode().equals(parts[0].trim()))
				dep = a;
			if (a.getCode().equals(parts[1].trim()))
				arr = a;
		}
		if (dep == null)
			throw new UnknownAirportInFileException(parts[0].trim());
		if (arr == null)
			throw new UnknownAirportInFileException(parts[1].trim());

		int hours, minutes, duration;
		try {
			hours = Integer.parseInt(subparts[0].trim());
			minutes = Integer.parseInt(subparts[1].trim());
		} catch (NumberFormatException e) {
			throw new InvalidDepartureTimeInFileException();
		}

		try {
			duration = Integer.parseInt(parts[3].trim());
		} catch (NumberFormatException e) {
			throw new InvalidFlightDurationInFileException();
		}

		return new Flight(dep, arr, hours, minutes, duration);
	}

	@Override
	public void readAll(BufferedReader br, List<Airport> airports, List<Flight> flights) throws Exception {
		Set<String> seenCodes = new HashSet<>();
		boolean airportHeader = false;
		boolean flightHeader = false;
		boolean readA = false, readF = false;

		String line;
		while ((line = br.readLine()) != null) {
			if (line.trim().isEmpty())
				continue;
			if (line.equals("# AIRPORTS")) {
				readA = true;
				readF = false;
				airportHeader = true;
				continue;
			}
			if (line.equals("# FLIGHTS")) {
				readA = false;
				readF = true;
				flightHeader = true;
				continue;
			}
			if (line.startsWith("CODE") || line.startsWith("FROM"))
				continue;
			if (!readA && !readF) {
				throw new InvalidFileSectionException();
			}

			if (readA) {
				addAirport(deserializeAirport(line), airports, seenCodes);
			} else {
				flights.add(deserializeFlight(line, airports));
			}
		}

		if (!airportHeader) {
			throw new MissingAirportSectionException();
		}
		if (!flightHeader) {
			throw new MissingFlightSectionException();
		}
	}

	@Override
	public void writeAll(PrintWriter pw, List<Airport> airports, List<Flight> flights) {
		pw.println("# AIRPORTS");
		pw.println("CODE,NAME,X,Y");
		for (Airport a : airports) {
			pw.println(serializeAirport(a));
		}
		pw.println("# FLIGHTS");
		pw.println("FROM,TO,DEPARTURE,DURATION");
		for (Flight f : flights) {
			pw.println(serializeFlight(f));
		}
	}

	@Override
	public String getExtension() {
		return "csv";
	}

}
