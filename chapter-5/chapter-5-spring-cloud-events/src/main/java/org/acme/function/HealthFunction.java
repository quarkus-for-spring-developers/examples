package org.acme.function;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component("health")
public class HealthFunction implements Function<String, String> {
  @Override
  public String apply(String probe) {
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
