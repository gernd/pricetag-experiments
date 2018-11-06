package de.gernotpointner.pricetagexperiments;

import com.github.lucasfsousa.pricetag.ParseException;
import com.github.lucasfsousa.pricetag.PriceTag;
import com.github.lucasfsousa.pricetag.Product;
import com.github.lucasfsousa.pricetag.ScraperNotFoundException;
import de.gernotpointner.pricetagexperiments.scraping.ScrapedProduct;
import de.gernotpointner.pricetagexperiments.trackedproducts.TrackedProduct;
import de.gernotpointner.pricetagexperiments.trackedproducts.TrackedProductScraper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Experiment {
    public static void main(String[] args) {
        // set up some tracked products
        TrackedProduct battleChasersForPs4 = new TrackedProduct("Battle chasers for PS4");
        battleChasersForPs4.shopUrls.add(
                "https://www.amazon.de/Battle-Chasers-Nightwar-PlayStation-4/dp/B06XMZMTRQ/ref=sr_1_1?s=videogames&ie=UTF8&qid=1540964725&sr=1-1&keywords=battle+chasers+nightwar+ps4");
        battleChasersForPs4.shopUrls.add(
                "https://www.mediamarkt.de/de/product/_battle-chasers-nightwar-rollenspiel-playstation-4-2293619.html");
        battleChasersForPs4.shopUrls.add(
                "notworking");

        TrackedProduct crackingTheCodingInterview = new TrackedProduct("Cracking the coding interview");
        crackingTheCodingInterview.shopUrls.add(
                "https://www.amazon.de/Cracking-Coding-Interview-6th-Programming/dp/0984782850/ref=sr_1_1?ie=UTF8&qid=1541091829&sr=8-1&keywords=cracking+the+coding+interview");

        List<TrackedProduct> trackedProducts = new ArrayList<>();
        trackedProducts.add(battleChasersForPs4);
        trackedProducts.add(crackingTheCodingInterview);

        // scrape associated products
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
                System.out.println("Brand: " + product.getBrand());
                System.out.println("Price: " + product.getPriceAsText());
                System.out.println("Store: " + product.getStore());
                System.out.println("Title: " + product.getTitle());
                System.out.println("Country Code: " + product.getCountryCode());
                System.out.println("URL: " + product.getUrl());
                System.out.println("Images");
                product.getImages().forEach(image -> System.out.println(image));
                System.out.println("metadata");
                product.getMetadata().forEach((key, value) -> System.out.println(key + " : " + value));
            });

            /*
            System.out.println("getting cheapest offer");
            Optional<Product> cheapestOffer = scrapedProduct.getCheapestOffer();

            if (cheapestOffer.isPresent()) {
                Product cheapest = cheapestOffer.get();
                System.out.println("Cheapest offer from " + cheapest.getStore() + " is " + cheapest.getPriceAsText());
            } else {
                System.out.println("no cheapest offer available");
            }
            */

        });

    }

    private static void printPricesOfGames() {

        List<String> gameUrls = Arrays.asList(
                "https://www.amazon.de/Atlus-Persona-5-PS4/dp/B06XKZVH8Z/ref=sr_1_1?ie=UTF8&qid=1540964642&sr=8-1&keywords=persona+5",
                "https://www.amazon.de/Battle-Chasers-Nightwar-PlayStation-4/dp/B06XMZMTRQ/ref=sr_1_1?s=videogames&ie=UTF8&qid=1540964725&sr=1-1&keywords=battle+chasers+nightwar+ps4",
                "https://www.amazon.de/God-War-Standard-Playstation-4/dp/B01H1QQ8L0/ref=sr_1_1?s=videogames&ie=UTF8&qid=1540964876&sr=1-1&keywords=god+of+war+ps4",
                "https://www.amazon.de/Square-Enix-Nier-Automata-Playstation/dp/B06XXPT34R/ref=sr_1_2?s=videogames&ie=UTF8&qid=1540964902&sr=1-2&keywords=nier+automata+ps4",
                "https://www.mediamarkt.de/de/product/_battle-chasers-nightwar-rollenspiel-playstation-4-2293619.html"
        );

        PriceTag priceTag = new PriceTag();

        List<Product> games = gameUrls.parallelStream().map(gameUrl -> {
            try {
                return priceTag.process(gameUrl);
            } catch (ScraperNotFoundException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        games = games.stream().filter(g -> !Objects.isNull(g)).collect(Collectors.toList());

        games.forEach(g -> {
            System.out.println(g.getTitle());
            System.out.println(g.getBrand());
            System.out.println(g.getStore());
            System.out.println(g.getPriceAsText());
            System.out.println("-------------------------");
        });
    }
}
