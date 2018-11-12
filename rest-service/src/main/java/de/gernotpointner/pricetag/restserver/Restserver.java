package de.gernotpointner.pricetag.restserver;

import static spark.Spark.get;

public class Restserver {
    public static void main(String[] args) {
        get("/", (req, res) -> "Simple Pricing Restserver");
    }
}
