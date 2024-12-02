package com.vld.dobitnik.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * Validate the range of provided numbers
 */
@Service
public class RangeValidation {
    private static final Logger LOGGER = LoggerFactory.getLogger(RangeValidation.class);

    /**
     * Validate that all the numbers in the full combination provided through the request are within the
     *
     * @param systemCombination A sorted full combination from the request.
     * @param topOfRange        A maximum number that can be played for the combination
     * @return Empty String if all the numbers in the full combination are within the permitted range for the game,
     * an error message otherwise;
     */
    public String invalidRange(List<Integer> systemCombination, final int topOfRange) {
        System.out.println("VLD");
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

}
