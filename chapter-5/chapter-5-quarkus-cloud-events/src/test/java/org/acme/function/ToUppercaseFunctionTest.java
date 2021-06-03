package org.acme.function;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.acme.domain.Input;
import org.acme.domain.Output;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.quarkus.funqy.knative.events.CloudEventBuilder;

public class ToUppercaseFunctionTest {
	private ToUppercaseFunction toUppercaseFunction = new ToUppercaseFunction();

	@ParameterizedTest(name = ParameterizedTest.DISPLAY_NAME_PLACEHOLDER + "[" + ParameterizedTest.INDEX_PLACEHOLDER + "] (" + ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER + ")")
	@MethodSource("toUppercaseFunctionArguments")
	@DisplayName("toUppercase")
	public void toUppercase(Input input, Output expectedOutput) {
		assertThat(this.toUppercaseFunction.function(input, CloudEventBuilder.create().subject("Subject").build(input)))
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
			Arguments.of(new Input("hello"), new Output("hello", "Subject", "HELLO", null)),
			Arguments.of(new Input(), new Output(null, "Subject", "NO DATA", null))
		);
	}
}
