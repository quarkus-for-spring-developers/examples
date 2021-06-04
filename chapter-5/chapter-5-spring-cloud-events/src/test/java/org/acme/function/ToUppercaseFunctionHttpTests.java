package org.acme.function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.*;

import java.util.UUID;
import java.util.stream.Stream;

import org.acme.domain.Input;
import org.acme.domain.Output;
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
public class ToUppercaseFunctionHttpTests {
  @Autowired
  TestRestTemplate rest;

  @ParameterizedTest(name = ParameterizedTest.DISPLAY_NAME_PLACEHOLDER + "[" + ParameterizedTest.INDEX_PLACEHOLDER + "] (" + ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER + ")")
  @MethodSource("toUppercaseFunctionArguments")
  public void toUppercase(String inputText, String expectedOutputText) {
    HttpHeaders ceHeaders = new HttpHeaders();
    ceHeaders.add(SPECVERSION, "1.0");
    ceHeaders.add(ID, UUID.randomUUID().toString());
    ceHeaders.add(TYPE, "com.redhat.faas.springboot.uppercase.test");
    ceHeaders.add(SOURCE, "http://localhost:8080/uppercase");
    ceHeaders.add(SUBJECT, "Convert to UpperCase");
    ceHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    HttpEntity<Input> request = new HttpEntity<>(new Input(inputText), ceHeaders);
    ResponseEntity<Output> response = this.rest.postForEntity("/", request, Output.class);
    
    assertThat(response)
      .isNotNull()
      .extracting(ResponseEntity::getStatusCode, re -> re.getHeaders().getContentType())
      .containsExactly(HttpStatus.OK, MediaType.APPLICATION_JSON);
    
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

  static Stream<Arguments> toUppercaseFunctionArguments() {
    return Stream.of(
      Arguments.of("hello", "HELLO"),
      Arguments.of(null, "NO DATA")
    );
  }
}
