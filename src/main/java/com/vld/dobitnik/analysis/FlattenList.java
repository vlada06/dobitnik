package com.vld.dobitnik.analysis;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlattenList {
    List<Integer> getFlatListOfNumbers(int[][] draws) {
        List<Integer> flatList = new ArrayList<>();

        for (int i = 0; i < draws.length; i++) {
            for (int j = 0; j < draws[i].length; j++) {
                flatList.add(draws[i][j]);
            }
        }
        return flatList;
    }
}
