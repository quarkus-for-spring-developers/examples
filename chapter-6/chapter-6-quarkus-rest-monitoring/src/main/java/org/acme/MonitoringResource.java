package org.acme;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;

@Path("/monitoring")
@Produces(MediaType.TEXT_PLAIN)
public class MonitoringResource {
    private final MeterRegistry registry;
    private final LinkedList<Long> list = new LinkedList<>();

    public MonitoringResource(MeterRegistry registry) {
        this.registry = registry;
        this.registry.gaugeCollectionSize("monitoring.list.size", Tags.empty(), this.list);
    }

    @GET
    @Path("gauge/{number}")
    public Long checkListSize(@PathParam("number") long number) {
        if (number == 2 || number % 2 == 0) {
            // add even numbers to the list
            this.list.add(number);
        } else {
            // remove items from the list for odd numbers
            try {
                number = this.list.removeFirst();
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
            this.registry.counter("monitoring.prime.number", "type", "not-natural").increment();
            return "Only natural numbers can be prime numbers.";
        }
        if (number == 1) {
            this.registry.counter("monitoring.prime.number", "type", "one").increment();
            return number + " is not prime.";
        }
        if (number == 2 || number % 2 == 0) {
            this.registry.counter("monitoring.prime.number", "type", "even").increment();
            return number + " is not prime.";
        }

        if (testPrimeNumber(number)) {
            this.registry.counter("monitoring.prime.number", "type", "prime").increment();
            return number + " is prime.";
        } else {
            this.registry.counter("monitoring.prime.number", "type", "not-prime").increment();
            return number + " is not prime.";
        }
    }

    protected boolean testPrimeNumber(long number) {
        Timer timer = this.registry.timer("monitoring.prime.number.test");
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
