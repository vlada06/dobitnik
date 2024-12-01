package com.vld.dobitnik.analysis;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleNumberFrequency {
    private final FlattenList flattenList = new FlattenList();

    public Map<Integer, Integer> getCountByNumber(int[][] draws, int poolSize) {
        List<Integer> allBallsDrawn = flattenList.getFlatListOfNumbers(draws);

        Map<Integer, Integer> countByNumber = new HashMap<>();
        for (int i = 1; i <= poolSize; i++) {
            int frequency = Collections.frequency(allBallsDrawn, i);
            countByNumber.put(i, Collections.frequency(allBallsDrawn, i));
        }
        return countByNumber;
    }
}
