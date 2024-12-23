package com.vld.dobitnik.analysis;

import com.vld.dobitnik.utils.ExternalResourcesUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalysePastDrawsEuroMillionsTest {
    private final String EURO_MILLION_RESULTS = "euromillions-draw-history.csv";
    AnalysePastDrawsEuroMillions analysePastDrawsEuroMillions;
    ExternalResourcesUtils utils;
    SingleNumberFrequency singleNumberFrequency;

    @BeforeEach
    void setUp() {
        utils = new ExternalResourcesUtils();
        analysePastDrawsEuroMillions = new AnalysePastDrawsEuroMillions();
        singleNumberFrequency = new SingleNumberFrequency();
    }

    @Test
    @DisplayName("Last thirty EuroMillion draws - lucky stars")
    void euroMillionLuckyStarsIndividualFrequencyTest() {
        int numberOfDraws = 13;
        int numberOfBalls = 2;
        int offset = 0;
        int[][] draws = utils.readPastDrawLuckyStarsEuroMillionsCsv(EURO_MILLION_RESULTS, offset, numberOfDraws, numberOfBalls);
        Map<Integer, Integer> actualCountByDecades = analysePastDrawsEuroMillions.getCountByEuroMillionLuckyStars(draws);
        StringBuffer sb = new StringBuffer();
        sb.append("################################################")
            .append("\nLucky stars over the last ")
            .append(numberOfDraws)
            .append(" draws")
            .append("\n################################################")
            .append("\n\n");
        System.out.println(sb);
        System.out.println("sorted by number of draws where appear:");
        actualCountByDecades.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(System.out::println);

        System.out.println("actualCountByDecades:");
        actualCountByDecades.entrySet().forEach(System.out::println);

        assertEquals(numberOfDraws * numberOfBalls, actualCountByDecades
            .values()
            .stream()
            .reduce(0, Integer::sum));
    }


    @Test
    @DisplayName("Check frequency of single numbers over an up to 6 month  period")
    void singleNumbersFrequencyHiLoRangeTest() {
        int numberOfDraws = 28;

        int numberOfBalls = 5;
        int offset = 0;
        int[][] draws = utils.readPastNationalLotteryDrawsCsv(EURO_MILLION_RESULTS, offset, numberOfDraws, numberOfBalls);
        Map<Integer, Integer> actualCountByNumber
            = singleNumberFrequency.getCountByNumber(draws, 50);


        StringBuffer sb = new StringBuffer();
        sb.append("\n################################################")
            .append("\nMain numbers' frequency over the last ")
            .append(numberOfDraws)
            .append(" draws")
            .append("\n################################################")
            .append("\n\n");
        System.out.println(sb);
        actualCountByNumber.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .forEach(System.out::println);

        int highEndLimit = 4;
        Map<Integer, Integer> highEnd =
            actualCountByNumber.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(highEndLimit)
                .collect(Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue,
                    (e1, e2) -> e1, LinkedHashMap::new));

//    System.out.println("sorted by number of draws where appear:");

        System.out.println("\nhighEnd:\n");
        highEnd.entrySet().stream().forEach(System.out::println);

        int lowEndLimit = 5;
        Map<Integer, Integer> lowEnd =
            actualCountByNumber.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .limit(lowEndLimit)
                .collect(Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue,
                    (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println("Stani!");
        lowEnd.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        System.out.println("\nlowEnd:\n");
        lowEnd.entrySet().stream().forEach(System.out::println);

    }

    @Test
    @DisplayName("Check frequency of single EUM lucky numbers over a number of draws")
    void singleNumbersFrequencyLuckyNumbersHiLoRangeTest() {
        int numberOfDraws = 26;
        int numberOfBalls = 2;
        int offset = 0;
        int[][] draws = utils.readPastDrawLuckyStarsEuroMillionsCsv(EURO_MILLION_RESULTS, offset, numberOfDraws, numberOfBalls);

        Map<Integer, Integer> actualCountByNumber
            = singleNumberFrequency.getCountByNumber(draws, 50);
        for (int i = 1; i <= actualCountByNumber.size(); i++) {
            System.out.println(i + ", " + actualCountByNumber.get(i));
        }

        assertEquals(numberOfDraws * numberOfBalls,
            actualCountByNumber
                .values()
                .stream()
                .reduce(0, Integer::sum));
    }

    // TODO find number of draws with empty decades
    // TODO create parametrized tests
    // TODO introduce analysis of odds/evens, divisible by primes (3,5,7...)
    // TODO test a range of draws (as opposed to testing the latest XYZ draws)
    // TODO   - check what happens in 1, 2, 3 draws after a series of draws

}


/*
 */
