package org.acme.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceConverterTests {
	PriceConverter priceConverter = new PriceConverter();

	@ParameterizedTest(name = ParameterizedTest.DISPLAY_NAME_PLACEHOLDER + "[" + ParameterizedTest.INDEX_PLACEHOLDER + "] (" + ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER + ")")
	@ValueSource(ints = { 1, 2 })
	@DisplayName("process")
	public void process(int price) {
		assertThat(this.priceConverter.process(price))
			.isEqualTo(price * PriceConverter.CONVERSION_RATE);
	}
}
