package com.vld.dobitnik.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * Find similarities with previous draws. If there are too many, eliminate the combination
 * @author Vladimir Davidovic
 * date: 14/10/2020
 * time: 16:19
 */
@Service
public class Similarity {
    private static final Logger LOGGER = LoggerFactory.getLogger(Similarity.class);
    private static final String LOGGER_MSG =     "pastDraw %s combination %s ";

    /**
     * Find similarities with previous draws. If there are too many, eliminate the combination
     *
     * @param pastDraw          A past draw against which the combination is validated
     * @param systemCombination A combination, typically larger than the size of a draw for the game.
     * @return A percentage of similarity. Since most of the games have a single digit game (combination) size, the
     * difference doesn't require decimal precision, hence integer rounded precision is sufficient.
     */
    Integer getListSimilarityRatio(List<Integer> pastDraw, List<Integer> systemCombination) {
        Integer list1IndexSame = 0;
        LOGGER.info("Similarity.getListSimilarityRatio");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format(LOGGER_MSG, pastDraw.toString(), systemCombination.toString()));
        }

        // compare each element of the larger list(proposed combination) with an element from the shorter one (draw)
        for (int i = 0; i < systemCombination.size(); i++) {
            for (int j = 0; j < pastDraw.size(); j++) {
                if (pastDraw.get(j).equals(systemCombination.get(i))) {
                    list1IndexSame++;
                }
            }
        }
        return (100 * list1IndexSame) / pastDraw.size();
    }
}
