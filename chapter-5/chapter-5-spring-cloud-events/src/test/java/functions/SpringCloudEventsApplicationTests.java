package functions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.HTTP_ATTR_PREFIX;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.ID;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.SOURCE;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.SPECVERSION;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.SUBJECT;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.TYPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

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

    input.input = "hello";

    HttpHeaders ceHeaders = new HttpHeaders();
    ceHeaders.add(HTTP_ATTR_PREFIX + SPECVERSION, "1.0");
    ceHeaders.add(HTTP_ATTR_PREFIX + ID, UUID.randomUUID()
        .toString());
    ceHeaders.add(HTTP_ATTR_PREFIX + TYPE, "com.redhat.faas.springboot.test");
    ceHeaders.add(HTTP_ATTR_PREFIX + SOURCE, "http://localhost:8080/uppercase");
    ceHeaders.add(HTTP_ATTR_PREFIX + SUBJECT, "Convert to UpperCase");

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
    Output output = objectMapper.readValue(body,
        Output.class);
    assertThat(output, notNullValue());
    assertThat(output.input, equalTo("hello"));
    assertThat(output.operation, equalTo("Convert to UpperCase"));
    assertThat(output.output, equalTo("HELLO"));
    assertThat(output.error, nullValue());
  }
}
