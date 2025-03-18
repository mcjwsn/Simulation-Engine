package agh.ics.oop.presenter;


import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.enums.MutationType;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles importing and exporting simulation configurations to/from CSV files.
 */
public class CsvManager {

    /**
     * Exports the given configuration to a CSV file.
     *
     * @param file The file to write to
     * @param config The configuration to export
     * @throws IOException If an I/O error occurs
     */
    public void exportConfig(File file, SimulationConfig config) throws IOException {
        String filePath = file.getAbsolutePath();
        if (!filePath.endsWith(".csv")) {
            filePath += ".csv";
            file = new File(filePath);
        }

        File parentDirectory = file.getParentFile();
        if (!parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            // Write header if file doesn't exist or is empty
            if (!file.exists() || file.length() == 0) {
                writer.println("Parameter,Value");
            }

            // Write configuration values
            writer.println("MapWidth," + config.mapWidth());
            writer.println("MapHeight," + config.mapHeight());
            writer.println("EquatorHeight," + config.equatorHeight());
            writer.println("GrassNumber," + config.grassNumber());
            writer.println("EnergyAddition," + config.energyAddition());
            writer.println("PlantRegeneration," + config.plantRegeneration());
            writer.println("NumberOfAnimals," + config.numberOfAnimals());
            writer.println("StartingAnimalEnergy," + config.startingAnimalEnergy());
            writer.println("EnergyNeededForReproduction," + config.energyNeededForReproduction());
            writer.println("EnergyLosingWithReproduction," + config.energyLosingWithReproduction());
            writer.println("MinGenMutations," + config.minGenMutations());
            writer.println("MaxGenMutations," + config.maxGenMutations());
            writer.println("GenomeLength," + config.genomeLength());
            writer.println("MutationType," + config.mutationType());
            writer.println("MaxEnergy," + config.maxEnergy());
            writer.println("MapType," + config.mapType());
            writer.println("MoveEnergy," + config.moveEnergy());
            writer.println("CSVSaveStats," + config.csvSaveStats());
        }
    }

    /**
     * Imports configuration from a CSV file.
     *
     * @param file The file to read from
     * @return The imported configuration
     * @throws IOException If an I/O error occurs
     * @throws IllegalArgumentException If the CSV contains invalid values
     */
    public SimulationConfig importConfig(File file) throws IOException {
        Map<String, String> configMap = readConfigFile(file);

        try {
            return new SimulationConfig(
                    Integer.parseInt(getOrDefault(configMap, "MapWidth", "20")),
                    Integer.parseInt(getOrDefault(configMap, "MapHeight", "20")),
                    Integer.parseInt(getOrDefault(configMap, "EquatorHeight", "5")),
                    Integer.parseInt(getOrDefault(configMap, "GrassNumber", "10")),
                    Integer.parseInt(getOrDefault(configMap, "EnergyAddition", "5")),
                    Integer.parseInt(getOrDefault(configMap, "PlantRegeneration", "5")),
                    Integer.parseInt(getOrDefault(configMap, "NumberOfAnimals", "10")),
                    Integer.parseInt(getOrDefault(configMap, "StartingAnimalEnergy", "10")),
                    Integer.parseInt(getOrDefault(configMap, "EnergyNeededForReproduction", "10")),
                    Integer.parseInt(getOrDefault(configMap, "EnergyLosingWithReproduction", "5")),
                    Integer.parseInt(getOrDefault(configMap, "MinGenMutations", "1")),
                    Integer.parseInt(getOrDefault(configMap, "MaxGenMutations", "5")),
                    Integer.parseInt(getOrDefault(configMap, "GenomeLength", "8")),
                    parseEnum(MutationType.class, getOrDefault(configMap, "MutationType", "LITLLECHANGE")),
                    Integer.parseInt(getOrDefault(configMap, "MaxEnergy", "100")),
                    parseEnum(MapType.class, getOrDefault(configMap, "MapType", "OWLBEAR")),
                    Integer.parseInt(getOrDefault(configMap, "MoveEnergy", "1")),
                    getOrDefault(configMap, "CSVSaveStats", "No")
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value in CSV: " + e.getMessage());
        }
    }

    /**
     * Reads a configuration file into a map.
     *
     * @param file The file to read
     * @return A map of parameter names to values
     * @throws IOException If an I/O error occurs
     */
    private Map<String, String> readConfigFile(File file) throws IOException {
        Map<String, String> configMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String parameter = parts[0].trim();
                    String value = parts[1].trim();
                    configMap.put(parameter, value);
                }
            }
        }

        return configMap;
    }

    /**
     * Gets a value from a map or returns a default value if not present.
     */
    private String getOrDefault(Map<String, String> map, String key, String defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    /**
     * Parses an enum value from a string.
     *
     * @param enumClass The enum class
     * @param value The string value
     * @return The parsed enum value
     * @throws IllegalArgumentException If the value is not a valid enum constant
     */
    private <T extends Enum<T>> T parseEnum(Class<T> enumClass, String value) {
        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for " + enumClass.getSimpleName() + ": " + value);
        }
    }
}
