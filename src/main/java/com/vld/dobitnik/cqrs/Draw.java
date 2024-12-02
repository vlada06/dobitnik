package com.vld.dobitnik.cqrs;

import java.util.Date; // TODO - better class for dates

public class Draw {
    public Draw(String drawNumber, Combination drawnCombination, Date drawDate) {
        this.drawNumber = drawNumber;
        this.drawnCombination = drawnCombination;
        this.drawDate = drawDate;
    }

    private String drawNumber;
    private Combination drawnCombination;
    private Date drawDate;
}
