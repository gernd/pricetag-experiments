package de.gernotpointner.pricetag.restservice;

import com.google.gson.Gson;
import de.gernotpointner.pricetaglogic.scraping.ScrapedProduct;
import de.gernotpointner.pricetaglogic.trackedproducts.TrackedProduct;
import de.gernotpointner.pricetaglogic.trackedproducts.TrackedProductScraper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.options;

public class Restserver {
    public static void main(String[] args) {

        enableCORS("*", "*", "*");

        Gson gson = new Gson();


        get("/", (req, res) -> {
            List<TrackedProduct> trackedProducts = createTrackedProducts();
            System.out.println("Starting to scrape results");
            Collection<ScrapedProduct> scrapedProducts = TrackedProductScraper.scapedProducts(trackedProducts);
            System.out.println("Scraping of results finished");
            return
                    scrapedProducts
                            .stream()
                            .map(scrapedProduct -> scrapedProduct.getSuccessfulScrapes())
                            .flatMap(scrapeResults -> scrapeResults.stream())
                            .collect(Collectors.toList());
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

    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
}
