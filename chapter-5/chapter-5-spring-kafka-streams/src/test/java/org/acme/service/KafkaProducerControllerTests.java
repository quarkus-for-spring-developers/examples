package org.acme.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class KafkaProducerControllerTests {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	KafkaProducer kafkaProducer;

	@MockBean
	KafkaConsumer kafkaConsumer;

	@Test
	public void send() throws Exception {
		String message = "Test_Message";

		Mockito.doNothing()
			.when(this.kafkaProducer).sendData(Mockito.eq(message));

		this.mockMvc.perform(get("/prices/{message}", message))
			.andExpect(status().isNoContent());

		Mockito.verify(this.kafkaProducer).sendData(Mockito.eq(message));
		Mockito.verifyNoMoreInteractions(this.kafkaProducer);
		Mockito.verifyNoInteractions(this.kafkaConsumer);
	}
}
