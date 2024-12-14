package com.vld.dobitnik.analysis;

import java.util.*;

public class DrawnTogether {
    /**
     * @param checkedAgainst the number which is checked
     * @param pastDraws,     two-dimensional array, where each draw is a single dimensional array
     * @return togetherFrequency - how frequently have certain numbers been drawn along the number tested against
     */
    public Map<Integer, Integer> frequentlyDrawnTogether(int checkedAgainst, int[][] pastDraws, int offset) {
        int pastDrawsLength = pastDraws.length;
        int drawSize = pastDraws[0].length;
        // reduce the array to only contain draws having the `checkedAgainst` number
        int reducedLength = getReducedArraySize(checkedAgainst, pastDraws);
        int[][] reduced = new int[reducedLength][drawSize];
        for (int i = 0; i < pastDrawsLength; i++) {
            int[] draw = pastDraws[i];
            for (int j = 0; j < draw.length; j++) {
                if (pastDraws[i][j] == checkedAgainst) {
                    // add draw to the reduced array
                    reduced[i] = draw;
                }
            }
        }

        Map<Integer, Integer> togetherFrequency = new HashMap<>();

        return togetherFrequency;
    }

    public Map<Integer, Integer> frequentlyDrawnTogether(int checkedAgainst, int[][] pastDraws) {

        // Validate inputs
        if (pastDraws == null || pastDraws.length == 0 || checkedAgainst < 1) {
            return Collections.emptyMap(); // Return empty map for invalid inputs
        }

        List<int[]> filteredDraws = new ArrayList<>(); // Use an ArrayList for dynamic sizing

        // Filter draws containing the checkedAgainst number
        for (
            int[] draw : pastDraws) {
            if (Arrays.stream(draw).anyMatch(num -> num == checkedAgainst)) {
                filteredDraws.add(draw); // Add entire draw only once found match
            }
        }

        Map<Integer, Integer> frequencyMap = new HashMap<>();

        // Count frequencies of numbers drawn together with 'checkedAgainst'
        for (
            int[] draw : filteredDraws) {
            for (int num : draw) {
                if (num != checkedAgainst) { // Exclude the checked number itself
                    frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
                }
            }
        }

        return frequencyMap;
    }

    private int getReducedArraySize(int checkedAgainst, int[][] pastDraws) {
        int reducedArraySize = 0;
        for (int i = 0; i < pastDraws.length; i++) {
            int[] draw = pastDraws[i];
            for (int j = 0; j < draw.length; j++) {
                if (pastDraws[i][j] == checkedAgainst) {
                    // add draw to the reduced array
                    reducedArraySize++;
                }
            }
        }
        return reducedArraySize;
    }
}
