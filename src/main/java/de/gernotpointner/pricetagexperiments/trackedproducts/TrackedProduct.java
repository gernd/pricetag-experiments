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

}
