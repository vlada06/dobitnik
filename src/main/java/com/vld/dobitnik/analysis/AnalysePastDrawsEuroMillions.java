package com.vld.dobitnik.analysis;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysePastDrawsEuroMillions {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
//    FlattenList flattenList; // TODO find out why autowiring does not work!!!
  FlattenList flattenList = new FlattenList(); // TODO find out why autowiring does not work!!!

    public Map<Integer, Integer> getCountByDecadesEuroMillionMainDraw(int[][] draws) {
        Map<Integer, Integer> countByDecades = new HashMap<>();
        countByDecades.put(0, 0);
        countByDecades.put(10, 0);
        countByDecades.put(20, 0);
        countByDecades.put(30, 0);
        countByDecades.put(40, 0);
        for (int[] draw : draws) {
            for (Integer ball : draw) {
                if (ball <= 10) {
                    int count = countByDecades.get(0);
                    count++;
                    countByDecades.put(0, count);
                } else if (ball <= 20) {
                    int count = countByDecades.get(10);
                    count++;
                    countByDecades.put(10, count);
                } else if ( ball <= 30) {
                    int count = countByDecades.get(20);
                    count++;
                    countByDecades.put(20, count);
                } else if (ball <= 40) {
                    int count = countByDecades.get(30);
                    count++;
                    countByDecades.put(30, count);
                } else if (ball <= 50) {
                    int count = countByDecades.get(40);
                    count++;
                    countByDecades.put(40, count);
                }
            }
        }

        return countByDecades;
    }

    public Map<Integer, Integer> getCountByPairsEuroMillionLuckyStars(int[][] draws) {
        List<Integer> luckyStars = flattenList.getFlatListOfNumbers(draws);
        Map<Integer, Integer> countByPairs = new HashMap<>();
        countByPairs.put(12, 0);
        countByPairs.put(34, 0);
        countByPairs.put(56, 0);
        countByPairs.put(78, 0);
        countByPairs.put(910, 0);
        countByPairs.put(1112, 0);
        for (Integer ball : luckyStars) {
            if (ball >= 1 && ball <= 2) {
                int count = countByPairs.get(12);
                count++;
                countByPairs.put(12, count);
            } else if (ball >= 3 && ball <= 4) {
                int count = countByPairs.get(34);
                count++;
                countByPairs.put(34, count);
            } else if (ball >= 5 && ball <= 6) {
                int count = countByPairs.get(56);
                count++;
                countByPairs.put(56, count);
            } else if (ball >= 7 && ball <= 8) {
                int count = countByPairs.get(78);
                count++;
                countByPairs.put(78, count);
            } else if (ball >= 9 && ball <= 10) {
                int count = countByPairs.get(910);
                count++;
                countByPairs.put(910, count);
            } else if (ball >= 11 && ball <= 12) {
                int count = countByPairs.get(1112);
                count++;
                countByPairs.put(1112, count);
            }
        }
        return countByPairs;
    }

    /**
     * @param draws
     * @return
     */
    public Map<Integer, Integer> getCountByEuroMillionLuckyStars(int[][] draws) {
        List<Integer> luckyStars = flattenList.getFlatListOfNumbers(draws);

        Map<Integer, Integer> countByPairs = new HashMap<>();
        countByPairs.put(1, 0);
        countByPairs.put(2, 0);
        countByPairs.put(3, 0);
        countByPairs.put(4, 0);
        countByPairs.put(5, 0);
        countByPairs.put(6, 0);
        countByPairs.put(7, 0);
        countByPairs.put(8, 0);
        countByPairs.put(9, 0);
        countByPairs.put(10, 0);
        countByPairs.put(11, 0);
        countByPairs.put(12, 0);

        for (Integer ball : luckyStars) {
            switch (ball) {
                case 1:
                    int count1 = countByPairs.get(1);
                    count1++;
                    countByPairs.put(1, count1);
                    break;
                case 2:
                    int count2 = countByPairs.get(2);
                    count2++;
                    countByPairs.put(2, count2);
                    break;
                case 3:
                    int count3 = countByPairs.get(3);
                    count3++;
                    countByPairs.put(3, count3);
                    break;
                case 4:
                    int count4 = countByPairs.get(4);
                    count4++;
                    countByPairs.put(4, count4);
                    break;
                case 5:
                    int count5 = countByPairs.get(5);
                    count5++;
                    countByPairs.put(5, count5);
                    break;
                case 6:
                    int count6 = countByPairs.get(6);
                    count6++;
                    countByPairs.put(6, count6);
                    break;
                case 7:
                    int count7 = countByPairs.get(7);
                    count7++;
                    countByPairs.put(7, count7);
                    break;
                case 8:
                    int count8 = countByPairs.get(8);
                    count8++;
                    countByPairs.put(8, count8);
                    break;
                case 9:
                    int count9 = countByPairs.get(9);
                    count9++;
                    countByPairs.put(9, count9);
                    break;
                case 10:
                    int count10 = countByPairs.get(10);
                    count10++;
                    countByPairs.put(10, count10);
                    break;
                case 11:
                    int count11 = countByPairs.get(11);
                    count11++;
                    countByPairs.put(11, count11);
                    break;
                case 12:
                    int count12 = countByPairs.get(12);
                    count12++;
                    countByPairs.put(12, count12);
                    break;
            }

        }
        return countByPairs;
    }

    public Map<Integer, Integer> getCountByFrequencyEuroMillionLuckyStars(int[][] draws) {

        List<Integer> luckyStars = flattenList.getFlatListOfNumbers(draws);

        System.out.println(luckyStars);
        return null;
    }


}
