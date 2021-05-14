package functions;

import io.quarkus.funqy.Funq;

public class HealthFunction {

    @Funq("health")
    public String function(String probe) {
        if ("readiness".equals(probe)) {
            return "ready";
        }
        else if ("liveness".equals(probe)) {
            return "live";
        }
        else {
            return "OK";
        }
    }
    
}