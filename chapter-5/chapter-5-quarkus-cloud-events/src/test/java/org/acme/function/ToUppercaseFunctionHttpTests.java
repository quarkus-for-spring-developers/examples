package org.acme.function;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import java.util.stream.Stream;

import org.acme.domain.Input;
import org.acme.domain.Output;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class ToUppercaseFunctionHttpTests {
	@ParameterizedTest(name = ParameterizedTest.DISPLAY_NAME_PLACEHOLDER + "[" + ParameterizedTest.INDEX_PLACEHOLDER + "] (" + ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER + ")")
	@MethodSource("toUppercaseFunctionArguments")
	public void toUppercase(String inputText, String expectedOutputText) {
		Output output = given()
			.when()
				.contentType(ContentType.JSON)
				.body(new Input(inputText))
				.header("ce-specversion", "1.0")
				.header("ce-id", UUID.randomUUID().toString())
				.header("ce-type", "com.redhat.faas.quarkus.uppercase.test")
				.header("ce-source", "http://localhost:8080/uppercase")
				.header("ce-subject", "Convert to UpperCase")
				.post("/")
			.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.extract().body().as(Output.class);

		assertThat(output)
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
