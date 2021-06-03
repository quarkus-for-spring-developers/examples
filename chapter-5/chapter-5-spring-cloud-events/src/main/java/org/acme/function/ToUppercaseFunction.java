package org.acme.function;

import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.*;

import java.util.Optional;
import java.util.function.Function;

import org.acme.domain.Input;
import org.acme.domain.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.function.web.util.HeaderUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component("uppercase")
public class ToUppercaseFunction implements Function<Message<Input>, Output> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ToUppercaseFunction.class);
  
  @Override
  public Output apply(Message<Input> inputMessage) {
    HttpHeaders httpHeaders = HeaderUtils.fromMessage(inputMessage.getHeaders());

    LOGGER.info("Input CE Id: {}", httpHeaders.getFirst(ID));
    LOGGER.info("Input CE Spec Version: {}", httpHeaders.getFirst(SPECVERSION));
    LOGGER.info("Input CE Source: {}", httpHeaders.getFirst(SOURCE));
    LOGGER.info("Input CE Subject: {}", httpHeaders.getFirst(SUBJECT));

    Input input = inputMessage.getPayload();
    LOGGER.info("Input: {}", input);
    
    String inputStr = input.getInput();
    String outputStr = Optional.ofNullable(inputStr)
      .map(String::toUpperCase)
      .orElse("NO DATA");
    
    LOGGER.info("Output CE: {}", outputStr);
    return new Output(inputStr, httpHeaders.getFirst(SUBJECT), outputStr, null);
  }
}
