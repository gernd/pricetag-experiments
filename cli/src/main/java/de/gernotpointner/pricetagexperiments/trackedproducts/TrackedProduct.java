package de.gernotpointner.pricetagexperiments.trackedproducts;

import java.util.ArrayList;
import java.util.List;

/**
 * A product that can be available in multiple shops
 */
public class TrackedProduct {
    public final String productName;

    public final List<String> shopUrls = new ArrayList<>();

    public TrackedProduct(String name) {
        productName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof TrackedProduct)) {
            return false;
        }
        TrackedProduct other = (TrackedProduct) o;
        return other.productName.equals(this.productName);
    }

    @Override
    public int hashCode() {
        return productName.hashCode();
    }
}
