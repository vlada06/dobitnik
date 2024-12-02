package com.vld.dobitnik.validate;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit/component test for the Similarity class
 *
 * @author Vladimir Davidovic
 * date: 15/10/2020
 * time: 12:07
 */

public class SimilarityTest {

    private Similarity similarity;

    @BeforeEach
    void setUp() {
        similarity = new Similarity();
    }

    @Test
    @DisplayName("Test basic happy path")
    void similarityTest() {
        List<Integer> draw = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> combination = Arrays.asList(2, 3, 4, 5, 6, 7);

        Integer similarityRatio = similarity.getListSimilarityRatio(draw, combination);
        assertEquals(80, similarityRatio);
    }

    @Test
    @DisplayName("Test basic happy path")
    void similarityTest_sameLength() {
        List<Integer> draw = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> combination = Arrays.asList(2, 3, 4, 5, 6);

        Integer similarityRatio = similarity.getListSimilarityRatio(draw, combination);
        assertEquals(80, similarityRatio);
    }


    @Test
    @DisplayName("Test when draw is longer than combination")
    void similarityTest_DrawLongerThanCombination() {
        List<Integer> draw = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> combination = Arrays.asList(3, 4, 5, 6);

        Integer similarityRatio = similarity.getListSimilarityRatio(draw, combination);
        assertEquals(60, similarityRatio);
    }


    @Test
    @DisplayName("Test when draw has no overlap with combination")
    void similarityTest_NoOverlap() {
        List<Integer> draw = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> combination = Arrays.asList(6, 7, 8, 9, 10, 11, 12);

        Integer similarityRatio = similarity.getListSimilarityRatio(draw, combination);
        assertEquals(0, similarityRatio);
    }

    @Test
    @DisplayName("Test when combination has no elements")
    void similarityTest_Empty() {
        List<Integer> draw = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> combination = new ArrayList<>();

        Integer similarityRatio = similarity.getListSimilarityRatio(draw, combination);
        assertEquals(0, similarityRatio);
    }
}
