package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import exceptions.file.InvalidAirportCoordinateInFileException;
import exceptions.file.InvalidAirportFormatException;
import exceptions.file.InvalidDepartureTimeInFileException;
import exceptions.file.InvalidFlightDurationInFileException;
import exceptions.file.InvalidFlightFormatException;
import exceptions.file.MissingAirportSectionException;
import exceptions.file.MissingFlightSectionException;
import exceptions.file.UnknownAirportInFileException;
import model.Airport;
import model.Flight;

// zna kako izgledaju aerodromi i letovi u JSON formatu
// deli blokove airports: [..] / flights: [..]	i primena (de)serialize na jednu liniju za svaki objekat
public class JSONFormatter extends Formatter {

	@Override
	public String serializeAirport(Airport a) {
		return "{\"code\":\"" + a.getCode() + "\",\"name\":\"" + a.getName() + "\",\"x\":" + a.getX() + ",\"y\":"
				+ a.getY() + "}";
	}

	@Override
	public String serializeFlight(Flight f) {
		return "{\"from\":\"" + f.getDepartureAirport().getCode() + "\",\"to\":\"" + f.getArrivalAirport().getCode()
				+ "\",\"departure\":\"" + f.getFullDepartureTime() + "\",\"duration\":" + f.getDurationMinutes() + "}";

	}

	@Override
	public Airport deserializeAirport(String line) throws Exception {
		try {
			line = line.replace("{", "");
			line = line.replace("}", "");
			line = line.replace("\"", "");
			String[] parts = line.split(",");
			if (parts.length != 4)
				throw new InvalidAirportFormatException();
			String code = parts[0].split(":")[1].trim();
			String name = parts[1].split(":")[1].trim();
			int x = Integer.parseInt(parts[2].split(":")[1].trim());
			int y = Integer.parseInt(parts[3].split(":")[1].trim());
			return new Airport(name, code, x, y);
		} catch (NumberFormatException e) {
			throw new InvalidAirportCoordinateInFileException();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidAirportFormatException();
		}
	}

	@Override
	public Flight deserializeFlight(String line, List<Airport> airports) throws Exception {
		line = line.replace("{", "");
		line = line.replace("}", "");
		line = line.replace("\"", "");
		String[] parts = line.split(",");
		if (parts.length != 4)
			throw new InvalidFlightFormatException();
		String from = parts[0].split(":")[1].trim();
		String to = parts[1].split(":")[1].trim();
		String[] subparts = parts[2].split(":");
		if (subparts.length != 3)
			throw new InvalidDepartureTimeInFileException();
		Airport dep = null, arr = null;
		for (Airport a : airports) {
			if (a.getCode().equals(from))
				dep = a;
			if (a.getCode().equals(to))
				arr = a;
		}

		if (dep == null)
			throw new UnknownAirportInFileException(from);
		if (arr == null)
			throw new UnknownAirportInFileException(to);

		try {
			int departureHour = Integer.parseInt(subparts[1].trim());
			int departureMin = Integer.parseInt(subparts[2].trim());
			int duration = Integer.parseInt(parts[3].split(":")[1].trim());
			return new Flight(dep, arr, departureHour, departureMin, duration);

		} catch (NumberFormatException e) {
			throw new InvalidFlightDurationInFileException();
		}

	}

	@Override
	public void readAll(BufferedReader br, List<Airport> airports, List<Flight> flights) throws Exception {
		String content = readAllText(br);

		String airportsBlock = getContentFromBrackets(content, "airports");
		if (airportsBlock == null) {
			throw new MissingAirportSectionException();
		}
		String flightsBlock = getContentFromBrackets(content, "flights");
		if (flightsBlock == null) {
			throw new MissingFlightSectionException();
		}

		Set<String> seenCodes = new HashSet<>();
		for (String obj : getJsonObjects(airportsBlock)) {
			addAirport(deserializeAirport(obj), airports, seenCodes);
		}
		for (String obj : getJsonObjects(flightsBlock)) {
			flights.add(deserializeFlight(obj, airports));
		}

	}

	@Override
	public void writeAll(PrintWriter pw, List<Airport> airports, List<Flight> flights) {
		pw.println("{");
		pw.println("\"airports\": [");
		for (int i = 0; i < airports.size(); i++) {
			pw.print(serializeAirport(airports.get(i)));
			pw.println(i < airports.size() - 1 ? "," : "");
		}
		pw.println("],");

		pw.println("\"flights\": [");
		for (int i = 0; i < flights.size(); i++) {
			pw.print(serializeFlight(flights.get(i)));
			pw.println(i < flights.size() - 1 ? "," : "");
		}
		pw.println("]");
		pw.println("}");

	}

	private String readAllText(BufferedReader br) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line).append('\n');
		}
		return sb.toString();
	}

	// dobijamo sve sto je unutar [ ] tj. {..},{..},...
	private String getContentFromBrackets(String content, String key) {
		int start = content.indexOf("\"" + key + "\"");
		if (start < 0)
			return null;

		start = content.indexOf('[', start);
		if (start < 0)
			return null;

		int end = content.indexOf(']', start);
		if (end < 0)
			return null;

		return content.substring(start + 1, end);
	}

	// dobijamo listu stringova tipa {..}
	private List<String> getJsonObjects(String arrayContent) {
		List<String> result = new ArrayList<>();
		if (arrayContent == null)
			return result;
		int start = -1;
		for (int i = 0; i < arrayContent.length(); i++) {
			char c = arrayContent.charAt(i);
			if (c == '{') {
				start = i;
			} else if (c == '}' && start != -1) {
				result.add(arrayContent.substring(start, i + 1));
				start = -1;
			}
		}
		return result;
	}

	@Override
	public String getExtension() {
		return "json";
	}

}
