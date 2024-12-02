package com.vld.dobitnik.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RandomNumbersGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomNumbersGenerator.class);

    private static final String LOGGER_MESSAGE_PARAMS = "RandomNumbersGenerator.getRandomIndices max: %s combinationSize: %s";
    private static final String LOGGER_MESSAGE_OUTPUT = "RandomNumbersGenerator.getRandomIndices returning %s";

    /**
     * Obtain indices for combinations, implementing Fisher–Yates shuffle algorithm.
     *
     * @param max             The upper bound (exclusive) of randomly obtained set.  Must be positive.
     * @param combinationSize The number of combinations to be created.
     * @return The set of the size combinationSize
     */
    public Set<Integer> getRandomIndices(int max, int combinationSize) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format(LOGGER_MESSAGE_PARAMS, max, combinationSize));
        }

        Set<Integer> randomIndices = new HashSet<>();

        List<Integer> rawList = getRandomlyOrderedIndices(max);

        // Shuffled list (pseudo randomly ordered)
        for (int i = 0; i < combinationSize; i++) {
            randomIndices.add(rawList.get(i));
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format(LOGGER_MESSAGE_OUTPUT, randomIndices.size()));
        }
        return randomIndices;
    }


    /**
     * Generate a system combination of random numbers
     *
     * @param poolSize                The size of the game played (e.g. for EuroMillions the pool size is 50,
     *                                i.e. numbers played from 1-50)
     * @param numberOfSelectedNumbers The number of selected numbers, i.e. size of full system played.
     * @return A set of the size numberOfSelectedNumbers created from a pool of integers between 1 and  poolSize.
     */
    public List<Integer> getRandomCombination(Integer poolSize, Integer numberOfSelectedNumbers) {
        Integer min = 1;

        Set<Integer> combination = new HashSet<>();

        int setSize = numberOfSelectedNumbers;
        int tempSetSize = 0;
        while (setSize > 0) {
            tempSetSize++;
            combination.add((new Random().nextInt(poolSize - min + 1) + min));
            if (tempSetSize == combination.size()){
                setSize--;
            }else {
                tempSetSize--;
            }
        }


        List<Integer> randomCombination = new ArrayList<>(combination);

        Collections.sort(randomCombination);
        LOGGER.info(randomCombination.toString());
        return randomCombination;
    }

    /**
     * Fetch the next random number (Fisher–Yates shuffle algorithm).
     *
     * @param orderedRawList An ordered list of numbers.
     * @return The next random number
     */
    @SuppressWarnings("squid:S2140")
    private Integer getRandomlyOrderedNumber(ArrayList<Integer> orderedRawList) {

        int rawListSize = orderedRawList.size();

        // Create a pseudo random number, making sure the it is within the index range
        int index = (int) (Math.random() * rawListSize);

        // Apply the random number to the raw list
        int randomlyOrderedNumber = orderedRawList.get(index);

        // Remove the number from the raw list
        orderedRawList.set(index, orderedRawList.get(rawListSize - 1));
        orderedRawList.remove(rawListSize - 1);

        // Return the removed number
        return randomlyOrderedNumber;
    }

    /**
     * Generate a list of non-repeating random numbers
     *
     * @param listSize The full combination size.
     * @return The list of randomly ordered numbers of full combination size.
     */
    private List<Integer> getRandomlyOrderedIndices(int listSize) {
        ArrayList<Integer> rawList = new ArrayList<>(listSize);

        // Populate the rawList:
        for (int i = 0; i < listSize; i++) {
            rawList.add(i + 1);
        }

        // While the list has elements, get a random number from it and add it to the resulting list
        List<Integer> randomlyOrderedIndices = new ArrayList<>();
        while (!rawList.isEmpty()) {
            randomlyOrderedIndices.add(getRandomlyOrderedNumber(rawList));
        }

        return randomlyOrderedIndices;
    }
}
