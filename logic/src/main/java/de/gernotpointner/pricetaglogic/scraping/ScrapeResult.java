package de.gernotpointner.pricetaglogic.scraping;

import com.github.lucasfsousa.pricetag.Product;

import java.util.Optional;

/**
 * Result for attempting to scrape an URL
 */
public class ScrapeResult {

    public Optional<Product> product = Optional.empty();

    public Optional<String> failureReason = Optional.empty();

    public final String url;

    public ScrapeResult(String url, Product product) {
        this.url = url;
        this.product = Optional.of(product);
    }

    public ScrapeResult(String url, String failureReason) {
        this.url = url;
        this.failureReason = Optional.of(failureReason);
    }
}
