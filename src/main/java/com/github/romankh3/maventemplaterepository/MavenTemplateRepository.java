package com.github.romankh3.maventemplaterepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MavenTemplateRepository {

    public static final String JAVA_REPOSITORY_TEMPLATE = "maven-template-repository";
    private static final String DEFAULT_FILE_PATH = "src/main/resources/input.txt";

    public static void main(String[] args) {
        // Déterminez le chemin du fichier en fonction de l'argument fourni
        String filePath = (args.length > 0) ? args[0] : DEFAULT_FILE_PATH;
        
        List<String> inputLines = readInputFile(filePath);
        if (inputLines == null) {
            System.out.println("Erreur lors de la lecture du fichier.");
            return;
        }

        List<String> output = process(inputLines);
        output.forEach(System.out::println);
    }

    private static List<String> readInputFile(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);

        // Vérifiez si le fichier existe avant de tenter de le lire
        if (!file.exists()) {
            System.err.println("Le fichier " + filePath + " n'existe pas.");
            return null;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) { // Ignore les lignes vides
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return lines;
    }

    public static List<String> process(List<String> inputLines) {
        // Assurez-vous qu'il y a suffisamment de lignes pour traiter les dimensions du gazon
        if (inputLines.isEmpty() || inputLines.size() < 2) {
            System.err.println("Le fichier d'entrée ne contient pas suffisamment de données.");
            return new ArrayList<>();
        }

        // Vérifiez que la première ligne contient les dimensions
        String[] pelouseDimensions = inputLines.get(0).split(" ");
        if (pelouseDimensions.length < 2) {
            System.err.println("Les dimensions du gazon sont mal formatées.");
            return new ArrayList<>();
        }

        int maxX = parseInteger(pelouseDimensions[0]);
        int maxY = parseInteger(pelouseDimensions[1]);

        if (maxX == -1 || maxY == -1) {
            System.err.println("Les dimensions du gazon sont invalides.");
            return new ArrayList<>();
        }

        Lawn lawn = new Lawn(maxX, maxY);
        InstructionExecutor executor = new SimpleInstructionExecutor();
        List<String> output = new ArrayList<>();

        // Traitez chaque paire de lignes après les dimensions
        for (int i = 1; i < inputLines.size(); i += 2) {
            if (i + 1 >= inputLines.size()) {
                System.err.println("Ligne de données pour la tondeuse sans instructions.");
                break;
            }

            String[] tondeuseData = inputLines.get(i).split(" ");
            if (tondeuseData.length < 3) {
                System.err.println("Les données de la tondeuse sont mal formatées.");
                continue;
            }

            int x = parseInteger(tondeuseData[0]);
            int y = parseInteger(tondeuseData[1]);
            char orientation = tondeuseData[2].charAt(0);

            if (x == -1 || y == -1) {
                System.err.println("Les coordonnées de la tondeuse sont invalides.");
                continue;
            }

            Position position = new Position(x, y, orientation);
            Lawnmower lawnmower = new Lawnmower(position, lawn);
            String instructions = inputLines.get(i + 1);
            lawnmower.executeInstructions(instructions, executor);

            output.add(lawnmower.getPosition().toString());
        }

        return output;
    }

    private static int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1; // Utiliser une valeur indicative d'erreur
        }
    }
}
