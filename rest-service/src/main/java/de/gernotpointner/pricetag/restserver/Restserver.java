package de.gernotpointner.pricetag.restserver;

import com.google.gson.Gson;
import de.gernotpointner.pricetaglogic.scraping.ScrapedProduct;
import de.gernotpointner.pricetaglogic.trackedproducts.TrackedProduct;
import de.gernotpointner.pricetaglogic.trackedproducts.TrackedProductScraper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static spark.Spark.get;

public class Restserver {
    public static void main(String[] args) {

        Gson gson = new Gson();

        get("/", (req, res) -> {
                List<TrackedProduct> trackedProducts = createTrackedProducts();
                Collection<ScrapedProduct> scrapedProducts = TrackedProductScraper.scapedProducts(trackedProducts);
                return scrapedProducts;
            }, gson::toJson
        );
    }

    private static List<TrackedProduct> createTrackedProducts() {

        TrackedProduct battleChasersForPs4 = new TrackedProduct("Battle chasers for PS4");
        battleChasersForPs4.shopUrls.add(
                "https://www.amazon.de/Battle-Chasers-Nightwar-PlayStation-4/dp/B06XMZMTRQ/ref=sr_1_1?s=videogames&ie=UTF8&qid=1540964725&sr=1-1&keywords=battle+chasers+nightwar+ps4");
        battleChasersForPs4.shopUrls.add(
                "https://www.mediamarkt.de/de/product/_battle-chasers-nightwar-rollenspiel-playstation-4-2293619.html");
        battleChasersForPs4.shopUrls.add(
                "notworking");
        battleChasersForPs4.shopUrls.add("https://www.gamestop.de/PS4/Games/45092/battle-chasers-nightwar");

        TrackedProduct crackingTheCodingInterview = new TrackedProduct("Cracking the coding interview");
        crackingTheCodingInterview.shopUrls.add(
                "https://www.amazon.de/Cracking-Coding-Interview-6th-Programming/dp/0984782850/ref=sr_1_1?ie=UTF8&qid=1541091829&sr=8-1&keywords=cracking+the+coding+interview");

        return Arrays.asList(crackingTheCodingInterview, battleChasersForPs4);
    }
}
