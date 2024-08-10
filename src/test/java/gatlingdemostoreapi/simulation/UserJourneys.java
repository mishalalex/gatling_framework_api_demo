package gatlingdemostoreapi.simulation;

import gatlingdemostoreapi.pages.Categories;
import gatlingdemostoreapi.pages.Products;
import io.gatling.javaapi.core.ChainBuilder;

import java.time.Duration;
import java.util.List;

import static gatlingdemostoreapi.test.DemostoreApiSimulation.initSession;
import static io.gatling.javaapi.core.CoreDsl.exec;

public class UserJourneys {

    public static Duration minPause = Duration.ofMillis(200);
    public static Duration maxPause = Duration.ofSeconds(3);

    public static ChainBuilder admin =
            exec(initSession)
                    .exec(Categories.list)
                    .pause(minPause, maxPause)
                    .exec(Products.list)
                    .pause(minPause, maxPause)
                    .exec(Products.get)
                    .pause(minPause, maxPause)
                    .exec(Products.update)
                    .pause(minPause, maxPause)
                    .repeat(3).on(exec(Products.create))
                    .pause(minPause, maxPause)
                    .exec(Categories.update);

    public static ChainBuilder priceScrapper =
            exec(Categories.list)
                    .pause(minPause, maxPause)
                    .exec(Products.listAll);

    public static ChainBuilder priceUpdater =
            exec(initSession)
                    .exec(Products.listAll)
                    .pause(minPause, maxPause)
                    .repeat("#{allProducts.size()}", "productIndex").on(
                            exec(session -> {
                                int index = session.getInt("productIndex");
                                List<Object> allProducts = session.getList("allProducts");
                                return session.set("product", allProducts.get(index));
                            })
                                    .exec(Products.update)
                                    .pause(minPause, maxPause));
}
