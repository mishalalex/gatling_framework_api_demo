package gatlingdemostoreapi.test;

import java.time.Duration;

import gatlingdemostoreapi.simulation.Scenarios;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class DemostoreApiSimulation extends Simulation {

    public HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://demostore.gatling.io")
            .header("Cache-Control", "no-cache")
            .contentTypeHeader("application/json")
            .acceptHeader("application/json");

    public static int USER_COUNT = Integer.parseInt(System.getProperty("USERS","5"));
    public static Duration RAMP_DURATION = Duration.ofSeconds(Integer.parseInt(System.getProperty("RAMP_DURATION","10")));
    public static Duration TEST_DURATION = Duration.ofSeconds(Integer.parseInt(System.getProperty("TEST_DURATION","60")));

    @Override
    public void before(){
        System.out.printf("Running test with %d users%n",USER_COUNT);
        System.out.printf("Ramping up users over %d seconds%n",RAMP_DURATION.getSeconds());
        System.out.printf("Total test duration: %d seconds%n",TEST_DURATION.getSeconds());
    }

    @Override
    public void after(){
        System.out.println("Stress test is completed.");
    }

    public static ChainBuilder initSession = exec(session -> session.set("authenticated", false));

    {
        setUp(
                Scenarios.defaultScn.injectOpen(rampUsers(USER_COUNT).during(RAMP_DURATION)),
                Scenarios.noAdminsScn.injectOpen(rampUsers(5).during(Duration.ofSeconds(10)))).protocols(httpProtocol);
    }
}