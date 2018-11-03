package de.gernotpointner.pricetagexperiments.trackedproducts;

import com.github.lucasfsousa.pricetag.PriceTag;
import com.github.lucasfsousa.pricetag.Product;
import de.gernotpointner.pricetagexperiments.scraping.ScrapeResult;
import de.gernotpointner.pricetagexperiments.scraping.ScrapedProduct;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TrackedProductScraper {

    private static final PriceTag priceTag = new PriceTag();

    public static Collection<ScrapedProduct> scapedProducts(Collection<TrackedProduct> trackedProducts) {
        return trackedProducts
                .stream()
                .map(trackedProduct ->
                             new ScrapedProduct(trackedProduct.productName, scrapeTrackedProduct(trackedProduct)))
                .collect(Collectors.toList());
    }

    private static List<ScrapeResult> scrapeTrackedProduct(TrackedProduct trackedProduct) {
        return trackedProduct.shopUrls
                .parallelStream()
                .map(gameUrl -> {
                    try {
                        Product p = priceTag.process(gameUrl);
                        return new ScrapeResult(gameUrl, p);
                    } catch (Exception e) {
                        return new ScrapeResult(gameUrl, e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }
}
