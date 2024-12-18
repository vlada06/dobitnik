package com.vld.dobitnik.cqrs;

import java.io.Serializable;
import java.util.List;

public record Combination(List<Integer> mainNumbers,
                          List<Integer> bonusNumbers) implements Serializable {
}
