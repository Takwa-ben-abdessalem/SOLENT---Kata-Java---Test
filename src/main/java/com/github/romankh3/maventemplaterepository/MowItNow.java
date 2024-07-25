package com.github.romankh3.maventemplaterepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MowItNow {

	private static final String DEFAULT_FILE_PATH = "src/main/resources/input.txt";
	private static final Logger LOGGER = Logger.getLogger(MowItNow.class.getName());

	public static void main(String[] args) {
		String filePath = (args.length > 0) ? args[0] : DEFAULT_FILE_PATH;

		List<String> inputLines = readInputFile(filePath);
		if (inputLines == null) {
			LOGGER.severe("Error reading the file.");
			return;
		}

		List<String> output = process(inputLines);
		output.forEach(System.out::println);
	}

	private static List<String> readInputFile(String filePath) {
		List<String> lines = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String trimmedLine = line.trim();
				if (!trimmedLine.isEmpty()) {
					lines.add(trimmedLine);
				}
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error reading the file", e);
			return null;
		}

		return lines;
	}

	public static List<String> process(List<String> inputLines) {
		List<String> output = new ArrayList<>();

		if (inputLines.size() < 2) {
			LOGGER.severe("The input file does not contain sufficient data.");
			return output;
		}

		String[] lawnDimensions = inputLines.get(0).split(" ");
		if (lawnDimensions.length != 2) {
			LOGGER.severe("Lawn dimensions are incorrectly formatted.");
			return output;
		}

		int maxX = parseInteger(lawnDimensions[0]);
		int maxY = parseInteger(lawnDimensions[1]);

		if (maxX == -1 || maxY == -1) {
			LOGGER.severe("Lawn dimensions are invalid.");
			return output;
		}

		Lawn lawn = new Lawn(maxX, maxY);
		InstructionExecutor executor = new SimpleInstructionExecutor();
		Set<String> positions = new HashSet<>();

		for (int i = 1; i < inputLines.size(); i++) {
			String currentLine = inputLines.get(i).trim();

			// Check if the next line contains instructions
			if (i + 1 < inputLines.size() && containsAny(inputLines.get(i + 1).trim(), "GAD")) {
				String instructions = inputLines.get(i + 1).trim();
				processLawnmowerData(currentLine, instructions, lawn, executor, output);
				i++; // Skip the instruction line
			} else {
				// Check if the current line does not contain instructions
				if (!containsAny(currentLine, "GAD")) {
					processLawnmowerDataWithoutInstructions(currentLine, output);
				} else {
					// Skip the current line and return to the beginning of the loop
					continue;
				}
			}

			// Check for collisions
			String finalPosition = output.get(output.size() - 1);
			if (positions.contains(finalPosition)) {
				LOGGER.severe("Collision detected at position: " + finalPosition);
			} else {
				positions.add(finalPosition);
			}
		}

		return output;
	}

	private static void processLawnmowerData(String lawnmowerDataLine, String instructions, Lawn lawn,
			InstructionExecutor executor, List<String> output) {
		String[] lawnmowerData = lawnmowerDataLine.split(" ");
		if (lawnmowerData.length < 3) {
			LOGGER.warning("Lawnmower data is incorrectly formatted.");
			return;
		}

		int x = parseInteger(lawnmowerData[0]);
		int y = parseInteger(lawnmowerData[1]);
		char orientation = lawnmowerData[2].charAt(0);

		// Check that the informations are valid
		if (x < 0 || y < 0) {
			LOGGER.warning("Invalid lawnmower coordinates: (" + x + ", " + y + "). Coordinates must be non-negative.");

		}

		if (!isValidOrientation(orientation)) {
			LOGGER.warning("Invalid lawnmower orientation: " + orientation);

		}

		Position position = new Position(x, y, orientation);
		Lawnmower lawnmower = new Lawnmower(position, lawn);
		lawnmower.executeInstructions(instructions, executor);
		output.add(lawnmower.getPosition().toString());
	}

	private static void processLawnmowerDataWithoutInstructions(String lawnmowerDataLine, List<String> output) {
		String[] lawnmowerData = lawnmowerDataLine.split(" ");
		if (lawnmowerData.length < 3) {
			LOGGER.warning("Lawnmower data is incorrectly formatted.");
			return;
		}

		int x = parseInteger(lawnmowerData[0]);
		int y = parseInteger(lawnmowerData[1]);
		char orientation = lawnmowerData[2].charAt(0);

		// Check that the informations are valid
		if (x < 0 || y < 0) {
			LOGGER.warning("Invalid lawnmower coordinates: (" + x + ", " + y + "). Coordinates must be non-negative.");
			return;
		}

		if (!isValidOrientation(orientation)) {
			LOGGER.warning("Invalid lawnmower orientation: " + orientation);
			return;
		}

		Position position = new Position(x, y, orientation);
		output.add(position.toString());

	}

	private static int parseInteger(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private static boolean isValidOrientation(char orientation) {
		return "NSEW".indexOf(orientation) != -1;
	}

	private static boolean containsAny(String str, String charsToCheck) {
		for (char ch : charsToCheck.toCharArray()) {
			if (str.indexOf(ch) >= 0) {
				return true;
			}
		}
		return false;
	}
}
