package de.gernotpointner.pricetagexperiments.trackedproducts;

import de.gernotpointner.pricetagexperiments.scraping.ScrapeResult;
import de.gernotpointner.pricetagexperiments.scraping.ScrapedProduct;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Tracks prices for a collection of tracked products
 */
public class PriceTracker {
    final Collection<TrackedProduct> trackedProducts;

    public PriceTracker(Collection<TrackedProduct> trackedProducts) {
        this.trackedProducts = trackedProducts;
    }

    public void startTracking() {
        System.out.println("Pricetracker started for the following products:");
        trackedProducts.forEach(trackedProduct -> System.out.println(trackedProduct.productName));
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            System.out.println("Scraping...");
            long start = System.currentTimeMillis();
            Collection<ScrapedProduct> scrapedProducts = TrackedProductScraper.scapedProducts(trackedProducts);
            long end = System.currentTimeMillis();
            long timeInMs = end - start;
            System.out.println("Scraping finished in " + timeInMs + " ms");

            // handle each scrape result
            scrapedProducts.forEach(scrapedProduct -> {

                // check for failed scrapes
                Collection<ScrapeResult> failedScrapes = scrapedProduct.getFailedScrapes();
                if (!failedScrapes.isEmpty()) {
                    System.out.println("Scraping failed for " + scrapedProduct.name + " for the following URls:");
                    failedScrapes.forEach(failedScrape -> {
                        System.out.println(failedScrape.url + ": " + failedScrape.failureReason);
                    });
                }

                // get and print cheapest offer

            });
        }, 0, 10, TimeUnit.SECONDS);
    }

}
