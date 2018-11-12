package de.gernotpointner.pricetagexperiments.pricealarm;

import java.math.BigDecimal;

public class Limit {

    public final BigDecimal priceLimit;

    public Limit(final BigDecimal priceLimit) {
        this.priceLimit = priceLimit;
    }
}
