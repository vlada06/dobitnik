package com.vld.dobitnik.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ExternalResourcesUtils {
    private static final String COMMA_DELIMITER = ",";

    /**
     * @param path The path of JSON file used in a test.
     * @return The search filter Map.
     * @throws IOException When the JSON file cannot be found in the provided location.
     */
    public String getTestRequestJsonString(String path) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        return new Scanner(file).useDelimiter("\\Z").next();
    }


    public int[][] readPastNationalLotteryDrawsCsv(String path, int numberOfDraws, int numberOfBalls) {
        return readPastNationalLotteryDrawsCsv(path, 0, numberOfDraws, numberOfBalls);
    }

    public int[][] readPastNationalLotteryDrawsCsv(String path, int offset, int numberOfDraws, int numberOfBalls) {
        int initialReadSize = numberOfDraws + 1 + offset; // so that later we can remove offset ones without getting into negative index for array
        String[][] records = new String[initialReadSize][numberOfBalls];
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());

        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                records[i] = line.split(COMMA_DELIMITER);
                i++;
            }
        } catch (Exception e) {
// VLD TODO write a proper exception handling
        }

        int[][] draws = new int[numberOfDraws][numberOfBalls];
        for (int j = (1 + offset); j < records.length; j++) {
            for (int k = 1; k <= numberOfBalls; k++) {
                draws[j - 1][k - 1] = Integer.parseInt(records[j][k]);
            }
        }

        int[][] resultDraws = new int[numberOfDraws - offset][numberOfBalls];

        int populatedCounter = 0;
        for (int[] draw : draws) {
            if (draw[0] != 0 && draw[1] != 0) {
                resultDraws[populatedCounter] = draw;
                populatedCounter++;
            }
        }
        return resultDraws;
    }

    /**
     * @param path
     * @param numberOfDraws
     * @param numberOfBalls
     * @return
     */
    public int[][] readPastEuroMillionMainDrawsCsv(String path, int numberOfDraws, int numberOfBalls) {
        String[][] records = new String[numberOfDraws + 1][numberOfBalls];
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null && i <= (numberOfDraws + 1)) {
                records[i] = line.split(COMMA_DELIMITER);
                i++;
            }
        } catch (Exception e) {
            // VLD TODO write a proper exception handling
        }


        int[][] draws = new int[numberOfDraws][numberOfBalls];
        for (int j = 1; j < records.length; j++) {
            for (int k = 1; k <= numberOfBalls; k++) {
                draws[j - 1][k - 1] = Integer.parseInt(records[j][k]);
            }
        }
        return draws;
    }

    /**
     * @param path
     * @param numberOfDraws
     * @param numberOfBalls
     * @return
     */
    public int[][] readPastDrawLuckyStarsEuroMillionsCsv(String path, int offset, int numberOfDraws, int numberOfBalls) {
        String[][] records = new String[numberOfDraws + 1][numberOfBalls];
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null && i <= (numberOfDraws + 1)) {
                records[i] = line.split(COMMA_DELIMITER);
                i++;
            }
        } catch (Exception e) {
            // VLD TODO write a proper exception handling
        }

        int[][] draws = new int[numberOfDraws][numberOfBalls];
        for (int j = 1; j < records.length; j++) {
            int luckyStarsOffset = 5;
            for (int k = 1; k <= numberOfBalls; k++) {
                draws[j - 1][k - 1] = Integer.parseInt(records[j][k + luckyStarsOffset]);
            }
        }
        return draws;
    }
}
