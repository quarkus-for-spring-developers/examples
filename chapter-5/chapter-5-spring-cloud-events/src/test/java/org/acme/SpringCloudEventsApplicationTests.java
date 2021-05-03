package org.acme;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.*;

import java.net.URI;
import java.util.UUID;

import org.acme.domain.Input;
import org.acme.domain.Output;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = SpringCloudEventsApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringCloudEventsApplicationTests {
  
  @Autowired
  private TestRestTemplate rest;

  @Autowired
  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUpperCaseJsonInput() throws Exception {

    Input input = new Input();

    input.setInput("hello");

    HttpHeaders ceHeaders = new HttpHeaders();
    ceHeaders.add(SPECVERSION, "1.0");
    ceHeaders.add(ID, UUID.randomUUID()
        .toString());
    ceHeaders.add(TYPE, "com.redhat.faas.springboot.test");
    ceHeaders.add(SOURCE, "http://localhost:8080/uppercase");
    ceHeaders.add(SUBJECT, "Convert to UpperCase");

    ResponseEntity<String> response = this.rest.exchange(
        RequestEntity.post(new URI("/uppercase"))
            .contentType(MediaType.APPLICATION_JSON)
            .headers(ceHeaders)
            .body(input),
        String.class);

    assertThat(response.getStatusCode()
        .value(), equalTo(200));
    String body = response.getBody();
    assertThat(body, notNullValue());
    Output output = this.objectMapper.readValue(body,
        Output.class);
    assertThat(output, notNullValue());
    assertThat(output.getInput(), equalTo("hello"));
    assertThat(output.getOperation(), equalTo("Convert to UpperCase"));
    assertThat(output.getOutput(), equalTo("HELLO"));
    assertThat(output.getError(), nullValue());
  }
}
