package org.acme.function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class HealthFunctionTest {

    @Test
    void testFunction() {
        Assertions.assertEquals("ready", new HealthFunction().function("readiness"));
        Assertions.assertEquals("live", new HealthFunction().function("liveness"));
        Assertions.assertEquals("OK", new HealthFunction().function(null));
    
    }
    
}
