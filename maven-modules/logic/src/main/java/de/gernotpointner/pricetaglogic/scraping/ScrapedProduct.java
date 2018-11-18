package de.gernotpointner.pricetaglogic.scraping;

import com.github.lucasfsousa.pricetag.Product;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScrapedProduct {

    public final String name;

    public final Collection<ScrapeResult> scrapeResults;

    public ScrapedProduct(String name, Collection<ScrapeResult> scrapeResults) {
        this.name = name;
        this.scrapeResults = scrapeResults;
    }

    public Collection<ScrapeResult> getSuccessfulScrapes() {
        return scrapeResults
                .stream()
                .filter(scrapeResult -> scrapeResult.product.isPresent())
                .collect(Collectors.toList());
    }

    public Collection<ScrapeResult> getFailedScrapes() {
        return scrapeResults
                .stream()
                .filter(scrapeResult -> !scrapeResult.product.isPresent())
                .collect(Collectors.toList());
    }

    public Optional<Product> getCheapestOffer() {
        return scrapeResults
                .stream()
                .filter(scrapeResult -> scrapeResult.product.isPresent())
                .map(scrapeResult -> scrapeResult.product.get())
                .min(
                        (firstProduct, secondProduct) -> firstProduct.getPrice()
                                                                     .compareTo(secondProduct.getPrice()));
    }

    public ScrapedProduct productWithSuccessfulScrapes() {
        return new ScrapedProduct(name, getSuccessfulScrapes());
    }

    @Override
    public String toString() {
        return "ScrapedProduct " + name;
    }
}
