package org.acme;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.*;

import java.util.UUID;
import java.util.stream.Stream;

import org.acme.domain.Input;
import org.acme.domain.Output;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringCloudEventsApplicationTests {
  @Autowired
  private TestRestTemplate rest;

  @ParameterizedTest(name = ParameterizedTest.DISPLAY_NAME_PLACEHOLDER + "[" + ParameterizedTest.INDEX_PLACEHOLDER + "] (" + ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER + ")")
  @MethodSource("toUppercaseFunctionArguments")
  @DisplayName("toUppercase")
  public void testUpperCaseJsonInput(String inputText, String expectedOutputText) {
    HttpHeaders ceHeaders = new HttpHeaders();
    ceHeaders.add(SPECVERSION, "1.0");
    ceHeaders.add(ID, UUID.randomUUID().toString());
    ceHeaders.add(TYPE, "com.redhat.faas.springboot.test");
    ceHeaders.add(SOURCE, "http://localhost:8080/uppercase");
    ceHeaders.add(SUBJECT, "Convert to UpperCase");
    ceHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    HttpEntity<Input> request = new HttpEntity<>(new Input(inputText), ceHeaders);
    ResponseEntity<Output> response = this.rest.postForEntity("/uppercase", request, Output.class);
    
    assertThat(response)
      .isNotNull()
      .extracting(ResponseEntity::getStatusCode)
      .isEqualTo(HttpStatus.OK);
    
    assertThat(response.getBody())
      .isNotNull()
      .extracting(
        Output::getInput,
        Output::getOperation,
        Output::getOutput,
        Output::getError
      )
      .containsExactly(
        inputText,
        "Convert to UpperCase",
        expectedOutputText,
        null
      );
  }

  private static Stream<Arguments> toUppercaseFunctionArguments() {
    return Stream.of(
      Arguments.of("hello", "HELLO"),
      Arguments.of(null, "NO DATA")
    );
  }

  @ParameterizedTest(name = ParameterizedTest.DISPLAY_NAME_PLACEHOLDER + "[" + ParameterizedTest.INDEX_PLACEHOLDER + "] (" + ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER + ")")
  @MethodSource("healthFunctionArguments")
  @DisplayName("health")
  public void health(String probe, String expectedResult) {
    HttpHeaders ceHeaders = new HttpHeaders();
    ceHeaders.add(SPECVERSION, "1.0");
    ceHeaders.add(ID, UUID.randomUUID().toString());
    ceHeaders.add(TYPE, "com.redhat.faas.springboot.test");
    ceHeaders.add(SOURCE, "http://localhost:8080/health");
    ceHeaders.add(SUBJECT, "HealthCheck");
    ceHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

    HttpEntity<String> request = new HttpEntity<>(probe, ceHeaders);
    ResponseEntity<String> response = this.rest.postForEntity("/health", request, String.class);

    assertThat(response)
      .isNotNull()
      .extracting(
        ResponseEntity::getStatusCode,
        ResponseEntity::getBody
      )
      .containsExactly(
        HttpStatus.OK,
        expectedResult
      );
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
