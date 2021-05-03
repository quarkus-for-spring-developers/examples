package org.acme.function;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HealthFunctionTests {
  private HealthFunction healthFunction = new HealthFunction();
  
  @ParameterizedTest(name = ParameterizedTest.DISPLAY_NAME_PLACEHOLDER + "[" + ParameterizedTest.INDEX_PLACEHOLDER + "] (" + ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER + ")")
  @MethodSource("healthFunctionArguments")
  @DisplayName("health")
  public void health(String probe, String expectedResult) {
    assertThat(this.healthFunction.apply(probe))
      .isEqualTo(expectedResult);
  }
  
  private static Stream<Arguments> healthFunctionArguments() {
    return Stream.of(
      Arguments.of("readiness", "ready"),
      Arguments.of("liveness", "live"),
      Arguments.of("somethingelse", "OK"),
      Arguments.of(null, "OK")
    );
  }
}