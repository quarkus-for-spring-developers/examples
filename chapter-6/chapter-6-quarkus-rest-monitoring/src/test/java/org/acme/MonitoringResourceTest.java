package org.acme;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class MonitoringResourceTest {

    @Test
    void testGaugeExample() {
        when().get("/monitoring/gauge/1").then().statusCode(200);
        when().get("/monitoring/gauge/2").then().statusCode(200);
        when().get("/monitoring/gauge/4").then().statusCode(200);
        when().get("/q/metrics").then().statusCode(200)
                .body(containsString(
                        "monitoring_list_size 2.0"));
        when().get("/monitoring/gauge/6").then().statusCode(200);
        when().get("/monitoring/gauge/5").then().statusCode(200);
        when().get("/monitoring/gauge/7").then().statusCode(200);
        when().get("/q/metrics").then().statusCode(200)
                .body(containsString(
                        "monitoring_list_size 1.0"));
    }

    @Test
    void testCounterExample() {
        when().get("/monitoring/prime/-1").then().statusCode(200);
        when().get("/monitoring/prime/0").then().statusCode(200);
        when().get("/monitoring/prime/1").then().statusCode(200);
        when().get("/monitoring/prime/2").then().statusCode(200);
        when().get("/monitoring/prime/3").then().statusCode(200);
        when().get("/monitoring/prime/15").then().statusCode(200);

        when().get("/q/metrics").then().statusCode(200)
                .body(containsString(
                        "monitoring_prime_number_total{type=\"prime\",}"))
                .body(containsString(
                        "monitoring_prime_number_total{type=\"not-prime\",}"))
                .body(containsString(
                        "monitoring_prime_number_total{type=\"one\",}"))
                .body(containsString(
                        "monitoring_prime_number_total{type=\"even\",}"))
                .body(containsString(
                        "monitoring_prime_number_total{type=\"not-natural\",}"));
    }

    @Test
    void testTimerExample() {
        when().get("/monitoring/prime/257").then().statusCode(200);
        when().get("/q/metrics").then().statusCode(200)
                .body(containsString(
                        "monitoring_prime_number_test_seconds_sum"))
                .body(containsString(
                        "monitoring_prime_number_test_seconds_max"))
                .body(containsString(
                        "monitoring_prime_number_test_seconds_count 1.0"));
        when().get("/monitoring/prime/7919").then().statusCode(200);
        when().get("/q/metrics").then().statusCode(200)
                .body(containsString(
                        "monitoring_prime_number_test_seconds_count 2.0"));
    }
}