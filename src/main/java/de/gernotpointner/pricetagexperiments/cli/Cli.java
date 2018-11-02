package de.gernotpointner.pricetagexperiments.cli;

import de.gernotpointner.pricetagexperiments.de.gernotpointner.pricetagexperiments.trackedproducts.TrackedProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Command Line App periodically checking for cheap prices of tracked products
 */
public class Cli {

    public static void main(String[] args) {
        List<TrackedProduct> trackedProducts = createTrackedProducts();

    }

    private static List<TrackedProduct> createTrackedProducts() {

        TrackedProduct battleChasersForPs4 = new TrackedProduct("Battle chasers for PS4");
        battleChasersForPs4.shopUrls.add(
                "https://www.amazon.de/Battle-Chasers-Nightwar-PlayStation-4/dp/B06XMZMTRQ/ref=sr_1_1?s=videogames&ie=UTF8&qid=1540964725&sr=1-1&keywords=battle+chasers+nightwar+ps4");
        battleChasersForPs4.shopUrls.add(
                "https://www.mediamarkt.de/de/product/_battle-chasers-nightwar-rollenspiel-playstation-4-2293619.html");
        battleChasersForPs4.shopUrls.add(
                "notworking");

        TrackedProduct crackingTheCodingInterview = new TrackedProduct("Cracking the coding interview");
        crackingTheCodingInterview.shopUrls.add(
                "https://www.amazon.de/Cracking-Coding-Interview-6th-Programming/dp/0984782850/ref=sr_1_1?ie=UTF8&qid=1541091829&sr=8-1&keywords=cracking+the+coding+interview");

        List<TrackedProduct> trackedProducts = new ArrayList<>();
        trackedProducts.add(battleChasersForPs4);
        trackedProducts.add(crackingTheCodingInterview);

        return trackedProducts;
    }
}
