package gatlingdemostoreapi.simulation;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.time.Duration;

import static gatlingdemostoreapi.test.DemostoreApiSimulation.TEST_DURATION;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.exec;

public class Scenarios {
    public static ScenarioBuilder defaultScn = scenario("Default load test")
            .during(TEST_DURATION)
            .on(
                    randomSwitch().on(
                            new Choice.WithWeight(20d, CoreDsl.exec(UserJourneys.admin)),
                            new Choice.WithWeight(40d, exec(UserJourneys.priceScrapper)),
                            new Choice.WithWeight(40d, exec(UserJourneys.priceUpdater))
                    )
            );

    public static ScenarioBuilder noAdminsScn = scenario("Load test without admin users")
            .during(Duration.ofSeconds(60))
            .on(
                    randomSwitch().on(
                            new Choice.WithWeight(60d, exec(UserJourneys.priceScrapper)),
                            new Choice.WithWeight(40d, exec(UserJourneys.priceUpdater))
                    )
            );
}
