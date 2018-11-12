package de.gernotpointner.pricetagexperiments.pricealarm;

import com.github.lucasfsousa.pricetag.Product;
import de.gernotpointner.pricetagexperiments.scraping.ScrapedProduct;

import java.util.Collection;
import java.util.stream.Collectors;

public class PriceAlarmChecker {

    public Collection<Product> checkPriceAlarms(ScrapedProduct scrapedProduct, Limit priceLimit) {
        return scrapedProduct
                .getSuccessfulScrapes()
                .stream()
                .map(scrapeResult -> scrapeResult.product.get())
                .filter(product -> product.getPrice().compareTo(priceLimit.priceLimit) < 0)
                .collect(Collectors.toList());
    }
}
