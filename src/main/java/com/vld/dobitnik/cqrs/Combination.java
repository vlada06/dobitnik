package com.vld.dobitnik.cqrs;

import java.io.Serializable;
import java.util.List;

/**
 * Combination of numbers. If present, fun takes place
 * @param mainNumbers present in all the games.
 * @param bonusNumbers - present in some games, can be null
 */
public record Combination(List<Integer> mainNumbers,
                          List<Integer> bonusNumbers) implements Serializable {
}
