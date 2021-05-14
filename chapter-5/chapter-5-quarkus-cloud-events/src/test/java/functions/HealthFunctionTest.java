package functions;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class HealthFunctionTest {

    @Test
    void testFunction() {
        Assertions.assertEquals("ready", new HealthFunction().function("readiness"));
        Assertions.assertEquals("live", new HealthFunction().function("liveness"));
        Assertions.assertEquals("OK", new HealthFunction().function(null));
    
    }
    
}
