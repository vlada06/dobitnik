package com.vld.dobitnik.analysis;

import com.vld.dobitnik.utils.ExternalResourcesUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalysePastDrawsUKNationalLotteryTest {

    AnalysePastDrawsUKNationalLottery analysePastDrawsUKNationalLottery;
    SingleNumberFrequency singleNumberFrequency;
    private static final String NATIONAL_LOTTERY_RESULTS = "lotto-draw-history.csv";
    private static final int POOL_SIZE = 59;

    ExternalResourcesUtils utils;

    @BeforeEach
    void setUp() {
        utils = new ExternalResourcesUtils();
        analysePastDrawsUKNationalLottery = new AnalysePastDrawsUKNationalLottery();
        singleNumberFrequency = new SingleNumberFrequency();
    }

    @Test
    @DisplayName("Last thirteen draws")
    void getLastTenTests() {
        int numberOfDraws = 13;
        int numberOfBalls = 6;
        int[][] draws = utils.readPastNationalLotteryDrawsCsv(NATIONAL_LOTTERY_RESULTS, numberOfDraws, numberOfBalls);
        Map<Integer, Integer> actualCountByDecades = analysePastDrawsUKNationalLottery.getCountByDecadesNationalLottery(draws);
        int expectedCount = actualCountByDecades.values().stream().reduce(0, Integer::sum);
        StringBuilder sb = new StringBuilder("0x ");
        sb.append(actualCountByDecades.get(0))
                .append("\n1x ").append(actualCountByDecades.get(10))
                .append("\n2x ").append(actualCountByDecades.get(20))
                .append("\n3x ").append(actualCountByDecades.get(30))
                .append("\n4x ").append(actualCountByDecades.get(40))
                .append("\n5x ").append(actualCountByDecades.get(50));

        System.out.println(sb.toString());
        assertEquals(expectedCount, numberOfDraws * numberOfBalls);
    }

    @Test
    @DisplayName("Last nine months draws")
    void test3() {
        int numberOfDraws = 85;
        int numberOfBalls = 7;
        int[][] draws = utils.readPastNationalLotteryDrawsCsv(NATIONAL_LOTTERY_RESULTS, numberOfDraws, numberOfBalls);
        Map<Integer, Integer> actualCountByDecades = analysePastDrawsUKNationalLottery.getCountByDecadesNationalLottery(draws);
        int expectedCount = actualCountByDecades.values().stream().reduce(0, Integer::sum);
        assertEquals(expectedCount, numberOfDraws * numberOfBalls);
//        assertEquals(108, actualCountByDecades.get(30));

        System.out.println("0: " + actualCountByDecades.get(0));
        System.out.println("1x " + actualCountByDecades.get(10));
        System.out.println("2x " + actualCountByDecades.get(20));
        System.out.println("3x " + actualCountByDecades.get(30));
        System.out.println("4x " + actualCountByDecades.get(40));
        System.out.println("5x " + actualCountByDecades.get(50));
    }

    @Test
    @DisplayName("Last 3 month draws")
    void test3a() {
        int numberOfDraws = 3;
        int numberOfBalls = 7;
        int[][] draws = utils.readPastNationalLotteryDrawsCsv(NATIONAL_LOTTERY_RESULTS, numberOfDraws, numberOfBalls);
        Map<Integer, Integer> actualCountByDecades = analysePastDrawsUKNationalLottery.getCountByDecadesNationalLottery(draws);

        System.out.println("0: " + actualCountByDecades.get(0));
        System.out.println("1x " + actualCountByDecades.get(10));
        System.out.println("2x " + actualCountByDecades.get(20));
        System.out.println("3x " + actualCountByDecades.get(30));
        System.out.println("4x " + actualCountByDecades.get(40));
        System.out.println("5x " + actualCountByDecades.get(50));

    }

    @Test
    @DisplayName("Check frequency of single numbers over a period")
    void singleNumbersFrequencyTest() {
        int numberOfDraws = 14;
        int numberOfBalls = 6;
        int[][] draws = utils.readPastNationalLotteryDrawsCsv(NATIONAL_LOTTERY_RESULTS, numberOfDraws, numberOfBalls);
        System.out.println("frequency of single numbers over the latest 13 draws");
        Map<Integer, Integer> actualCountByNumber
                = singleNumberFrequency.getCountByNumber(draws, POOL_SIZE);
        assertEquals(numberOfDraws * numberOfBalls, actualCountByNumber
                .values()
                .stream()
                .reduce(0, Integer::sum));
        assertEquals(0, actualCountByNumber.get(1));
    }

    @Test
    @DisplayName("Check frequency of single numbers over a period")
    void singleNumbersFrequencyTestSixDraws() {
        int numberOfDraws = 15;
        int numberOfBalls = 7;
        int[][] draws = utils.readPastNationalLotteryDrawsCsv(NATIONAL_LOTTERY_RESULTS, numberOfDraws, numberOfBalls);
        System.out.println("Single numbers over the latest " + numberOfDraws + " draws");
        System.out.println("number, frequency");
        Map<Integer, Integer> actualCountByNumber
                = singleNumberFrequency.getCountByNumber(draws, POOL_SIZE);
        assertEquals(numberOfDraws * numberOfBalls, actualCountByNumber
                .values()
                .stream()
                .reduce(0, Integer::sum));
        System.out.println("\nzero shows:\n");
        for (Integer number : actualCountByNumber.keySet()) {
            int temp = actualCountByNumber.get(number);
            if (temp == 0) {
                System.out.println(number);
            }
        }
        System.out.println();
//        assertEquals(3, actualCountByNumber.get(1));
    }


    @Test
    @DisplayName("Check frequency of single numbers over a recent period")
    void singleNumbersFrequencyOffsetRangeAvailableAgainTest() {
        int numberOfDraws = 27;
        int numberOfBalls = 7;
        int offset = 0;
        int[][] draws = utils.readPastNationalLotteryDrawsCsv(NATIONAL_LOTTERY_RESULTS, offset, numberOfDraws, numberOfBalls);

        Map<Integer, Integer> actualCountByNumber
                = singleNumberFrequency.getCountByNumber(draws, POOL_SIZE);

        StringBuffer sb = new StringBuffer();
        sb.append("\n################################################")
            .append("\nNumbers' frequency over the last ")
            .append(numberOfDraws)
            .append(" draws")
            .append("\n################################################\n");
        System.out.println(sb);
        System.out.println("number, frequency");
        actualCountByNumber.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);

        assertEquals((numberOfDraws - offset) * numberOfBalls, actualCountByNumber
                .values()
                .stream()
                .reduce(0, Integer::sum));
        int topEndLimit = 4;
        Map<Integer, Integer> topEnd =
                actualCountByNumber.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(topEndLimit)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println("\nhighEnd:\n");
        topEnd.entrySet().stream().forEach(System.out::println);

        int lowEndLimit = 5;
        Map<Integer, Integer> lowEnd =
                actualCountByNumber.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                        .limit(lowEndLimit)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        lowEnd.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        System.out.println("\nlowEnd:\n");
        lowEnd.entrySet().stream().forEach(System.out::println);
        System.out.println("\ntheEnd!\n");
    }

    // TODO introduce analysis of odds/evens, divisible by primes (3,5,7...

    @Ignore
    @Test
    @DisplayName("Analyse the frequency of odds/evens, divisible by primes (3,5,7...)")
    void testDivisibleGroupings() {
        int numberOfDraws = 6;
        int numberOfBalls = 7;
        int divisor = 7;
        int expected = 11;
        int[][] draws = utils.readPastNationalLotteryDrawsCsv(NATIONAL_LOTTERY_RESULTS, numberOfDraws, numberOfBalls);

        int numberDivisibleByParam = analysePastDrawsUKNationalLottery.getDivisibleGrouping(draws, divisor);
        System.out.println("number of divisibles by " + divisor + " is " + numberDivisibleByParam);
        System.out.println("deviation from expected " + expected + " is " + (numberDivisibleByParam - expected));

        assertEquals(expected, numberDivisibleByParam);

    }
    // TODO find number of draws with empty decades
    // TODO Fix the offset counts in data load!!!
    // TODO include bonus numbers
    // TODO consider/analyse machines
    // TODO create parametrized tests

}

