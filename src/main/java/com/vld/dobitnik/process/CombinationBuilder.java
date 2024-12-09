package com.vld.dobitnik.process;

//import com.eurowinners.syndicate.configuration.ApplicationConfiguration;
//import com.eurowinners.syndicate.cqrs.Combination;
//import com.eurowinners.syndicate.exception.CombinationSizeException;
//import com.eurowinners.syndicate.exception.NotFoundException;
//import com.eurowinners.syndicate.utils.CommonConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vld.dobitnik.cqrs.Combination;
import com.vld.dobitnik.exception.CombinationSizeException;
import com.vld.dobitnik.exception.NotFoundException;
import com.vld.dobitnik.utils.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Vladimir Davidovic
 * date: 07/12/2019
 * time: 18:57
 */

@Component
public class CombinationBuilder {
    public static final String POOL_SIZE_EXCEPTION_MSG = "The number of elements (%s) has to be greater than the size of combination (%s)";
    private static final Logger LOGGER = LoggerFactory.getLogger(CombinationBuilder.class);
//    private final ApplicationConfiguration configuration;


//    public CombinationBuilder(ApplicationConfiguration configuration) {
//        this.configuration = configuration;
//    }


    @Autowired
    private RandomNumbersGenerator randomNumbersGenerator;

    /**
     * Create a shorthand system for a lottery game
     *
     * @param mainNumbersCombination Extended list of numbers of the main game part to be combined into a system.
     * @param bonusNumbers           Extended list of bonus numbers to be combined into a system.
     * @param mainGameSize           The list size (number of the numbers) of the main game played.
     * @param mainCombinationSize    The number of the lines played in the shorthand system.
     * @param bonusPoolSize          The number of the bonus numbers played.
     * @param bonusCombinationSize   The number of the bonus lines produced.
     * @return JsonNode containing the shorthand system to be played
     */
    public JsonNode createWheelingCombinations(List<Integer> mainNumbersCombination,
                                               List<Integer> bonusNumbers,
                                               Integer mainGameSize,
                                               Integer mainCombinationSize,
                                               Integer bonusPoolSize,
                                               Integer bonusCombinationSize) {

        StringBuilder loggerMessageSb = new StringBuilder("CombinationBuilder.createWheelingCombinations() parameters");
        loggerMessageSb.append("\nmainNumbersCombination: ")
                .append(mainNumbersCombination.toString())
                .append("\nbonusNumbers: ");
        if (bonusNumbers == null) {
            loggerMessageSb.append("null");
        } else {
            loggerMessageSb.append(bonusNumbers.toString());
        }
        loggerMessageSb.append("\n mainGameSize: ")
                .append(mainGameSize)
                .append("\n mainCombinationSize: ")
                .append(mainCombinationSize)
                .append("\n bonusPoolSize: ")
                .append(bonusPoolSize)
                .append("\n bonusCombinationSize: ")
                .append(bonusCombinationSize);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(loggerMessageSb.toString());
        }
        // combine indices
        List<ArrayList<Integer>> shorthandIndicesSubset =
                getShorthandIndicesSubset(mainNumbersCombination.size(), mainGameSize, mainCombinationSize);

        List<List<Integer>> shorthandCombinationsSubset
                = getShorthandCombinationsSubset(mainNumbersCombination, shorthandIndicesSubset);

        // Conditional, since not all the games contain bonus numbers
        List<Combination> wheelingSystem;
        List<List<Integer>> shorthandBonusSubset;
        if (bonusNumbers != null) {
            List<ArrayList<Integer>> shorthandBonusIndicesSubset;
            int bonusNumbersRawSize = bonusNumbers.size();

            // Check whether bonus size is smaller than the total shorthand system size
            if (bonusNumbersRawSize >= mainCombinationSize) {
                shorthandBonusIndicesSubset =
                        getShorthandIndicesSubset(bonusNumbersRawSize, bonusPoolSize, bonusCombinationSize);
            } else {
                shorthandBonusIndicesSubset = combineIndices(bonusNumbersRawSize, bonusPoolSize);
            }
            shorthandBonusSubset = getShorthandCombinationsSubset(bonusNumbers, shorthandBonusIndicesSubset);
            wheelingSystem = getWheelingSystem(shorthandCombinationsSubset, shorthandBonusSubset);
        } else {
            wheelingSystem = getWheelingSystem(shorthandCombinationsSubset);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("wheelingSystem result size: %s combinations", wheelingSystem.size()));
        }
        ObjectMapper mapper = new ObjectMapper();

        JsonNode result = mapper.convertValue(wheelingSystem, JsonNode.class);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(result.asText());
        }
        return result;
    }

    /**
     * Create the shorthand (wheeling) system for the main part of the game.
     *
     * @param mainFullSystemCombination - Actual numbers (balls) to be combined
     * @param shorthandIndicesSubset    - combinations  of indices which create a shorthand (wheeling) system
     * @return The shorthand system, combinations (lines) for the main part of the game.
     */
    private List<List<Integer>> getShorthandCombinationsSubset(List<Integer> mainFullSystemCombination,
                                                               List<ArrayList<Integer>> shorthandIndicesSubset) {
        List<List<Integer>> shorthandCombinationsSubset = new ArrayList<>();
        StringBuilder sb = new StringBuilder("CombinationBuilder.getShorthandCombinationsSubset:");
        sb.append("\nshorthandIndicesSubset: ")
            .append(shorthandIndicesSubset);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(sb.toString());
        }

        // assign mainCombination members to appropriate indicesArrayList<ArrayList<Integer>>
        // outer loop - every combination of the wheeling system
        for (List<Integer> singleIndexCombination : shorthandIndicesSubset) {
            int counter = 0;
            List<Integer> singleActualCombination = new ArrayList<>();
            for (Integer index : singleIndexCombination) {
                counter++;
                if (counter <= singleIndexCombination.size()) {
                    singleActualCombination.add(mainFullSystemCombination.get(index - 1));
                }
            }
            shorthandCombinationsSubset.add(singleActualCombination);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("CombinationBuilder.getShorthandCombinationsSubset - returned: %s",
                    shorthandCombinationsSubset));
        }
        return shorthandCombinationsSubset;
    }

    /**
     * Obtain a  smaller, random subset of combinations (array lists), so-called "shorthand"
     * or "wheeling" system this method produces list of lists of keys for ball numbers
     * (maybe for lucky stars, too)
     *
     * @param n                   number of elements which are combined
     * @param k                   the size of an individual combination
     * @param shorthandSystemSize - the smaller number of combinations required out of total
     *                              number of combinations of n over k
     * @return Shorthand Subset (wheeling system), subset of a total number of combinations
     */
    public List<ArrayList<Integer>> getShorthandIndicesSubset(int n, int k, int shorthandSystemSize) {
        LOGGER.info("CombinationBuilder.getShorthandSubset");
        ArrayList<ArrayList<Integer>> shorthandSubset = new ArrayList<>();
        //create full set of possible combinations
        ArrayList<ArrayList<Integer>> rawCombinations = combineIndices(n, k);

        // invoke a random integer producing method send full, return "system" list of combinations
        // passing parameter rawCombinations.size()-1 as the indices start from 0 and go up to maximum -1.
        // So if the maximum number comes as the result of randomization, it could cause IndexOutOfBoundsException
        HashSet<Integer> randomIndices = (HashSet) randomNumbersGenerator
                .getRandomIndices(rawCombinations.size() - 1, shorthandSystemSize);

        randomIndices.forEach(i -> shorthandSubset.add(rawCombinations.get(i)));

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("CombinationBuilder.getShorthandSubset returning: %s",
                    shorthandSubset.toString()));
        }
        return shorthandSubset;
    }


    /**
     * Create all the possible combinations of n number of elements over k combination
     * e.g if we have n comprising 8 elements, and the required size of combination is 5,
     * then the total number of combinations is 56.
     * NOTE: n and k are both sizes of sets (the collection of non-duplicate elements).
     *
     * @param n number of elements which are combined.
     * @param k the size of an individual combination.
     * @return A list of lists, all the actual combinations of the indices for the main game.
     */
    protected ArrayList<ArrayList<Integer>> combineIndices(int n, int k) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if (n <= 0 || k <= 0) {
            String message = String.format(CommonConstants.POSITIVE_NUMBERS_EXCEPTION_MSG, n, k);
            throw new CombinationSizeException(message);
        }
        if (n < k) {
            String message = String.format(POOL_SIZE_EXCEPTION_MSG, n, k);
            throw new CombinationSizeException(message);
        }

        ArrayList<Integer> item = new ArrayList<>();
        doCombine(n, k, 1, item, result); // because it needs to begin from 1

        return result;
    }


    /**
     * Recursive method to create all the actual combinations of the indices for the main game.
     *
     * @param n                The number of elements which are combined.
     * @param k                The size of an individual combination.
     * @param start            The lower end of the combining range
     * @param indexCombination An individual interim combination of the indices built for the main game.
     * @param combinations     A list of lists, all the actual combinations of the indices for the main game.
     */
    private void doCombine(int n, int k, int start, ArrayList<Integer> indexCombination,
                           ArrayList<ArrayList<Integer>> combinations) {
        if (indexCombination.size() == k) {
            combinations.add(new ArrayList<>(indexCombination));
            return;
        }

        for (int i = start; i <= n; i++) {
            indexCombination.add(i);
            doCombine(n, k, i + 1, indexCombination, combinations);
            indexCombination.remove(indexCombination.size() - 1);
        }
    }

    /**
     * Put together the shorthand systems of main and bonus numbers from parameter lists of the two
     *
     * @param mainNumbers  The set of main combination numbers.
     * @param bonusNumbers The set of bonus number combinations.
     * @return A lsit of formed combinations of main + bonus numbers.
     */
    private List<Combination> getWheelingSystem(List<List<Integer>> mainNumbers, List<List<Integer>> bonusNumbers) {
        if (mainNumbers.isEmpty()) {
            throw new NotFoundException("Main numbers list cannot be empty");
        }
        //  bonus numbers set can be null but not bigger than mainNumbers
        List<Combination> wheelingSysetm = new ArrayList<>();
        int bonusNumbersResetCount = 0;

        if (null != bonusNumbers) {
            for (List<Integer> mainNumbersCombination : mainNumbers) {
                Combination combination;

                // need to somehow accommodate small number of bonus numbers combinations
                // with larger number of main combinations; Until a better solution, this one rules!!!
                if (bonusNumbersResetCount > (bonusNumbers.size() - 1)) {
                    bonusNumbersResetCount = 0;
                    combination = new Combination(mainNumbersCombination, bonusNumbers.get(bonusNumbersResetCount));
                } else {
                    combination = new Combination(mainNumbersCombination, bonusNumbers.get(bonusNumbersResetCount));
                    bonusNumbersResetCount++;
                }
                wheelingSysetm.add(combination);
            }
        } else {
            return getWheelingSystem(mainNumbers);
        }
        return wheelingSysetm;
    }


    /**
     * Create a shorthand system of lines (combinations) from the parameter list.
     *
     * @param mainNumbers The set of main combination numbers.
     * @return A list of formed combinations of main numbers (for (where no bonus numbers are played).
     */
    private List<Combination> getWheelingSystem(List<List<Integer>> mainNumbers) {
        List<Combination> wheelingSystem = new ArrayList<>();

        // TODO need to somehow accommodate small number of bonus numbers combinations
        // with larger number of main combinations; Until a better solution, this one rules!!!
        mainNumbers.forEach(c -> {
            final Combination combination = new Combination(c);
            wheelingSystem.add(combination);
        });

        return wheelingSystem;
    }
}
