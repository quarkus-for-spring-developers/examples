package org.acme;

import java.util.NoSuchElementException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Path("/monitoring")
@Produces("text/plain")
public class MonitoringResource extends RegisterMeterRegistry {

    MonitoringResource(MeterRegistry registry) {
        super(registry);
    }

    @GET
    @Path("gauge/{number}")
    public Long checkListSize(@PathParam("number") long number) {
        if (number == 2 || number % 2 == 0) {
            // add even numbers to the list
            list.add(number);
        } else {
            // remove items from the list for odd numbers
            try {
                number = list.removeFirst();
            } catch (NoSuchElementException nse) {
                number = 0;
            }
        }
        return number;
    }

    @GET
    @Path("prime/{number}")
    public String checkIfPrime(@PathParam("number") long number) {
        if (number < 1) {
            registry.counter("monitoring.prime.number", "type", "not-natural").increment();
            return "Only natural numbers can be prime numbers.";
        }
        if (number == 1) {
            registry.counter("monitoring.prime.number", "type", "one").increment();
            return number + " is not prime.";
        }
        if (number == 2 || number % 2 == 0) {
            registry.counter("monitoring.prime.number", "type", "even").increment();
            return number + " is not prime.";
        }

        if (testPrimeNumber(number)) {
            registry.counter("monitoring.prime.number", "type", "prime").increment();
            return number + " is prime.";
        } else {
            registry.counter("monitoring.prime.number", "type", "not-prime").increment();
            return number + " is not prime.";
        }
    }

    protected boolean testPrimeNumber(long number) {
        Timer timer = registry.timer("monitoring.prime.number.test");
        return timer.record(() -> {
            for (int i = 3; i < Math.floor(Math.sqrt(number)) + 1; i = i + 2) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        });
    }
}