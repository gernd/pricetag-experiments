package de.gernotpointner.pricetagexperiment;

import com.github.lucasfsousa.pricetag.Product;
import de.gernotpointner.pricetagexperiments.scraping.ScrapeResult;
import de.gernotpointner.pricetagexperiments.scraping.ScrapedProduct;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ScapedProductTest {

    @Test
    public void testInstantiationWorks() {
        ScrapedProduct product = new ScrapedProduct("dummy product", Collections.emptyList());
    }

    @Test
    public void testGetCheapestOffer_noProducts_emptyOptionalReturned() {
        ScrapedProduct product = new ScrapedProduct("dummy product", Collections.emptyList());
        Optional<Product> cheapestProduct = product.getCheapestOffer();
        Assert.assertFalse("Cheapest product should not exist", cheapestProduct.isPresent());
    }

    @Test
    public void testGetCheapestOffer_oneProduct_ProductReturned() {
        Product cheapProduct = new Product();
        cheapProduct.setPrice(new BigDecimal(12.99));
        cheapProduct.setTitle("A cheap product");
        ScrapeResult scrapeResult = new ScrapeResult("www.google.de", cheapProduct);
        ScrapedProduct product =
                new ScrapedProduct("dummy product", Collections.singletonList(scrapeResult));
        Optional<Product> cheapestProduct = product.getCheapestOffer();
        Assert.assertTrue("Cheapest product should exist", cheapestProduct.isPresent());
        assertEquals(cheapestProduct.get().getPrice(), cheapProduct.getPrice());
    }

    @Test
    public void testGetCheapestOffer_twoProducts_cheaperOneReturned() {
        Product cheapProduct = new Product();
        cheapProduct.setPrice(new BigDecimal(12.99));
        cheapProduct.setTitle("A cheap product");

        Product expensiveProduct = new Product();
        expensiveProduct.setPrice(new BigDecimal(199.99));
        expensiveProduct.setTitle("An expensive product");

        ScrapeResult firstScrapeResult = new ScrapeResult("www.google.de", cheapProduct);
        ScrapeResult secondScrapeResult = new ScrapeResult("www.google.de", expensiveProduct);

        ScrapedProduct product =
                new ScrapedProduct("dummy product", Arrays.asList(firstScrapeResult, secondScrapeResult));
        Optional<Product> cheapestProduct = product.getCheapestOffer();
        Assert.assertTrue("Cheapest product should exist", cheapestProduct.isPresent());
        assertEquals(cheapestProduct.get().getPrice(), cheapProduct.getPrice());
    }
}
