package de.gernotpointner.pricetaglogic.scraping;

import com.github.lucasfsousa.pricetag.Product;

import java.util.Optional;

/**
 * Result for attempting to scrape an URL
 */
public class ScrapeResult {

    public final Optional<Product> product;

    public final Optional<String> failureReason;

    public final String url;

    public ScrapeResult(String url, Product product) {
        this.url = url;
        this.product = Optional.of(product);
        this.failureReason = Optional.empty();
    }

    public ScrapeResult(String url, String failureReason) {
        this.url = url;
        this.failureReason = Optional.of(failureReason);
        this.product = Optional.empty();
    }
}
