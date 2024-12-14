package com.vld.dobitnik.analysis;

import com.vld.dobitnik.utils.ExternalResourcesUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DrawnTogetherTest {
    private final String EURO_MILLION_RESULTS = "euromillions-draw-history.csv";
    ExternalResourcesUtils utils;
    SingleNumberFrequency singleNumberFrequency;

    DrawnTogether drawnTogether;

    @BeforeEach
    void setUp() {
        utils = new ExternalResourcesUtils();
        drawnTogether = new DrawnTogether();
        singleNumberFrequency = new SingleNumberFrequency();
    }

    @Test
    @DisplayName("Find out which numbers are most frequently drawn together")
    void frequentlyDrawnTogetherTest(){
// pass a number/ball
// pass the number of draws
// find out only the draws in which the passed numbers appears
// find ou frequency of other numbers drawn with it
// return a map with numbers as key and frequencies as value
        int checkedAgainst = 9;
        int numberOfPastDraws = 13;
        int numberOfBalls = 5;
        int offset = 0;

        int[][] pastDraws = utils.readPastNationalLotteryDrawsCsv(EURO_MILLION_RESULTS, offset, numberOfPastDraws, numberOfBalls);


        int expectedSize = 45;

        Map<Integer, Integer> actualFrequencies = drawnTogether.frequentlyDrawnTogether(checkedAgainst, pastDraws);

        // print actualFrequencies
        actualFrequencies.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(System.out::println);
        int actualSize = actualFrequencies.size();

        assertEquals(expectedSize, actualSize);

    }
}

// TODO (later) find  repeated from previous draw  8, 12 (6, 10)
