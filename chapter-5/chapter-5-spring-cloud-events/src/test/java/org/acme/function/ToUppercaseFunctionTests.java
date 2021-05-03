package org.acme.function;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.stream.Stream;

import org.acme.domain.Input;
import org.acme.domain.Output;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.cloud.function.cloudevent.CloudEventMessageUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

class ToUppercaseFunctionTests {
  private ToUppercaseFunction toUppercaseFunction = new ToUppercaseFunction();

  @ParameterizedTest(name = ParameterizedTest.DISPLAY_NAME_PLACEHOLDER + "[" + ParameterizedTest.INDEX_PLACEHOLDER + "] (" + ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER + ")")
  @MethodSource("toUppercaseFunctionArguments")
  @DisplayName("toUppercase")
  public void toUppercase(Message<Input> inputMessage, Output expectedOutput) {
    assertThat(this.toUppercaseFunction.apply(inputMessage))
      .isNotNull()
      .extracting(
        Output::getError,
        Output::getInput,
        Output::getOperation,
        Output::getOutput
      )
      .containsExactly(
        expectedOutput.getError(),
        expectedOutput.getInput(),
        expectedOutput.getOperation(),
        expectedOutput.getOutput()
      );
  }
  
  private static Stream<Arguments> toUppercaseFunctionArguments() {
    return Stream.of(
      Arguments.of(
        new GenericMessage<>(new Input("hello"), Map.of(CloudEventMessageUtils.SUBJECT, "Subject")),
        new Output("hello", "Subject", "HELLO", null)
      ),
      Arguments.of(
        new GenericMessage<>(new Input(), Map.of(CloudEventMessageUtils.SUBJECT, "Subject")),
        new Output(null, "Subject", "NO DATA", null)
      )
    );
  }
}