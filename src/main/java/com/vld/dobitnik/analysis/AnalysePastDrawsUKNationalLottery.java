package com.vld.dobitnik.analysis;

import java.util.*;

public class AnalysePastDrawsUKNationalLottery {
    private FlattenList flattenList = new FlattenList();

    public Map<Integer, Integer> getCountByDecadesNationalLottery(int[][] draws) {
        Map<Integer, Integer> countByDecades = new HashMap<>();
        countByDecades.put(0, 0);
        countByDecades.put(10, 0);
        countByDecades.put(20, 0);
        countByDecades.put(30, 0);
        countByDecades.put(40, 0);

        countByDecades.put(50, 0);
        for (int[] draw : draws) {
            for (Integer ball : draw) {
                if (ball < 10) {
                    int count = countByDecades.get(0);
                    count++;
                    countByDecades.put(0, count);
                } else if (ball >= 10 && ball < 20) {
                    int count = countByDecades.get(10);
                    count++;
                    countByDecades.put(10, count);
                } else if (ball >= 20 && ball < 30) {
                    int count = countByDecades.get(20);
                    count++;
                    countByDecades.put(20, count);
                } else if (ball >= 30 && ball < 40) {
                    int count = countByDecades.get(30);
                    count++;
                    countByDecades.put(30, count);
                } else if (ball >= 40 && ball < 50) {
                    int count = countByDecades.get(40);
                    count++;

                    countByDecades.put(40, count);
                } else if (ball >= 50 && ball < 60) {
                    int count = countByDecades.get(50);
                    count++;
                    countByDecades.put(50, count);
                }
            }
        }
        return countByDecades;
    }

    /**
     *
     * @param draws previous drawn numbers
     * @param divisor
     * @return count of balls divisible by the divisor
     *
     *    Check a small number of most recent draws, e.g. 4 for odds/evens, 6 for divisible by 3,
     *    5 for divisible by 5, 6 for divisible by 7; The groups where the frequency deviates from
     *    average should be favoured
     */
    public int getDivisibleGrouping(int[][] draws, Integer divisor) {
        // takes a draw. Figures how many balls are divisible by the divisor argument.
        int count = 0;
        for (int[] draw : draws) {
            for (int ball : draw) {
                if (ball % divisor == 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
