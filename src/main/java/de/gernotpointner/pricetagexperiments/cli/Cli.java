package de.gernotpointner.pricetagexperiments.cli;

import com.github.lucasfsousa.pricetag.Product;
import de.gernotpointner.pricetagexperiments.pricealarm.Limit;
import de.gernotpointner.pricetagexperiments.pricealarm.PriceAlarmChecker;
import de.gernotpointner.pricetagexperiments.scraping.ScrapedProduct;
import de.gernotpointner.pricetagexperiments.trackedproducts.TrackedProduct;
import de.gernotpointner.pricetagexperiments.trackedproducts.TrackedProductScraper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Command Line App periodically checking for cheap prices of tracked products
 */
public class Cli {

    public static void main(String[] args) {
        List<TrackedProduct> trackedProducts = createTrackedProducts();

        Collection<ScrapedProduct> scrapedProducts = TrackedProductScraper.scapedProducts(trackedProducts);

        scrapedProducts.forEach((scrapedProduct) -> {

            System.out.println("---------------------------------");
            System.out.println("Scraping results for " + scrapedProduct.name);
            System.out.println("---------------------------------");

            // display errors
            scrapedProduct.getFailedScrapes()
                          .forEach(scrapeResult ->
                                           System.out.println("Scraping failed for URL " + scrapeResult.url + " reason: " + scrapeResult.failureReason));

            // get all products where scraping worked
            Collection<Product> successfullyScrapedProducts = scrapedProduct.getSuccessfulScrapes()
                                                                            .stream()
                                                                            .map(scrapeResult -> scrapeResult.product.get())
                                                                            .collect(Collectors.toList());

            System.out.println("Product name " + scrapedProduct.name);
            successfullyScrapedProducts.forEach(product -> {
                System.out.println("----------------------------------------");
                System.out.println("Store: " + product.getStore());
                System.out.println("----------------------------------------");
                System.out.println("Brand: " + product.getBrand());
                System.out.println("Price: " + product.getPriceAsText());
                System.out.println("Title: " + product.getTitle());
                System.out.println("Country Code: " + product.getCountryCode());
                System.out.println("URL: " + product.getUrl());
                System.out.println("Images");
                product.getImages().forEach(image -> System.out.println(image));
                System.out.println("metadata");
                product.getMetadata().forEach((key, value) -> System.out.println(key + " : " + value));
            });

            Optional<Product> cheapestProduct = scrapedProduct.getCheapestOffer();
            if (cheapestProduct.isPresent()) {
                System.out.println("Cheapest price is " + cheapestProduct.get()
                                                                         .getPriceAsText() + " at " + cheapestProduct.get()
                                                                                                                     .getStore());
            } else {
                System.out.println("No cheapest product could be found");
            }
            System.out.println("Checking price alarms for " + scrapedProduct.name);
            // price alarms
            Limit limit = new Limit(new BigDecimal("15.00"));
            PriceAlarmChecker priceAlarmChecker = new PriceAlarmChecker();
            Collection<Product> lessThanLimit = priceAlarmChecker.checkPriceAlarms(scrapedProduct, limit);
            if (!lessThanLimit.isEmpty()) {
                System.out.println("Price alarm for: ");
                lessThanLimit.forEach(productAlarm -> System.out.println(productAlarm.getStore() + " : " + productAlarm
                        .getPriceAsText()));
            }
        });

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

        List<TrackedProduct> trackedProducts = new ArrayList<>();
        trackedProducts.add(battleChasersForPs4);
        trackedProducts.add(crackingTheCodingInterview);

        return trackedProducts;
    }
}
