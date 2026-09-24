package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import exceptions.file.FileReadException;
import exceptions.file.FileWriteException;
import exceptions.file.InvalidFileExtensionException;

import java.awt.FileDialog;
import java.awt.Frame;

import model.Airport;
import model.Flight;

// bira fajl, ne zna detalje CSV/JSON, to pita formater

public class FileManager {

	private Formatter formatter;
	private FileChooser fileChooser = new FileChooser();

	public FileManager(Formatter formatter) {
		this.formatter = formatter;
	}

	public boolean loadD(Frame owner, List<Airport> airports, List<Flight> flights) throws Exception {
		String filename = fileChooser.choose(owner, "Choose file", FileDialog.LOAD);
		if (filename == null)
			return false;
		validateExtension(filename);
		load(filename, airports, flights);
		return true;
	}

	public boolean saveD(Frame owner, List<Airport> airports, List<Flight> flights) throws Exception {
		String filename = fileChooser.choose(owner, "Save file", FileDialog.SAVE);
		if (filename == null)
			return false;
		validateExtension(filename);
		save(filename, airports, flights);
		return true;
	}

	private void validateExtension(String filename) throws InvalidFileExtensionException {
		if (!filename.toLowerCase().endsWith("." + formatter.getExtension())) {
			throw new InvalidFileExtensionException(formatter.getExtension());
		}
	}

	public void load(String filename, List<Airport> airports, List<Flight> flights) throws Exception {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			formatter.readAll(br, airports, flights);
		} catch (IOException e) {
			throw new FileReadException(filename);
		}
	}

	public void save(String filename, List<Airport> airports, List<Flight> flights) throws Exception {
		try (PrintWriter pw = new PrintWriter(new FileWriter(filename, false))) {
			formatter.writeAll(pw, airports, flights);
		} catch (IOException e) {
			throw new FileWriteException(filename);
		}
	}

}
