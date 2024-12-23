package com.vld.dobitnik.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * Validate the range of provided numbers
 *
 * @author: Vladimir Davidovic
 * date: 31/12/2020
 * time: 10:25
 * Migrated to gradle build 02/12/2024
 */
@Service
public class CombinationValidation {
    private static final Logger LOGGER = LoggerFactory.getLogger(CombinationValidation.class);

    /**
     * Validate that all the numbers in the full combination provided through the request are within a valid range for
     * the game. E.g. 59 for UK National Lottery, 50/12 for EuroMillions.
     *
     * @param systemCombination A sorted full combination from the request.
     * @param topOfRange        A maximum number that can be played for the combination
     * @return Empty String if all the numbers in the full combination are within the permitted range for the game,
     * an error message otherwise;
     */
    public String invalidRange(List<Integer> systemCombination, final int topOfRange) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(
                    MessageFormat.format("Validating systemCombination: {0} is within 1 to {1} range",
                            systemCombination, topOfRange));
        }

        return systemCombination.stream()
                .allMatch(num -> num >= 1 && num <= topOfRange)
                ? ""
                : String.format("Combination %s contains numbers outside of the range 1 to %s",
                systemCombination, topOfRange);
    }

    /**
     * Validate that the size the full combination is within a valid range for
     * the game. E.g. 6 for UK National Lottery, 5/2 for EuroMillions.
     * @param systemCombination - the combination being validated
     * @param combinationSize - the maximum valid size. Size can be less than maximum,
     *                          in case of systems with fixed numbers.
     * @return Error message if invalid, empty string if size is correct
     */
    public String invalidSize(List<Integer> systemCombination, final int combinationSize) {
        return systemCombination.size() <= combinationSize
            ? ""
            : String.format("Combination %s is longer than %s",
            systemCombination, combinationSize);
    }
}
