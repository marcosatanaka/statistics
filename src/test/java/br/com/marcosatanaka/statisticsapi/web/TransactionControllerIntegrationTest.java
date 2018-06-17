package br.com.marcosatanaka.statisticsapi.web;

import br.com.marcosatanaka.statisticsapi.domain.transaction.LogTransactionAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;

import static br.com.marcosatanaka.statisticsapi.domain.transaction.LogTransactionAction.MSG_AMOUNT_CANNOT_BE_NULL;
import static br.com.marcosatanaka.statisticsapi.domain.transaction.LogTransactionAction.MSG_TIMESTAMP_CANNOT_BE_NULL;
import static br.com.marcosatanaka.statisticsapi.infrastructure.ExceptionErrorsType.ACTION_NOT_NULL_PROPERTIES;
import static br.com.marcosatanaka.statisticsapi.infrastructure.ExceptionErrorsType.BAD_REQUEST;
import static java.time.ZonedDateTime.now;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionControllerIntegrationTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void should_not_accept_empty_post() throws Exception {
		mockMvc.perform(post("/transactions"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("code", is(BAD_REQUEST.getCode())));
	}

	@Test
	public void should_not_accept_post_without_amount() throws Exception {
		String json = "{ \"amount\": null, \"timestamp\": 1529204615 }";

		mockMvc.perform(post("/transactions")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("code", is(ACTION_NOT_NULL_PROPERTIES.getCode())))
				.andExpect(jsonPath("message", is(MSG_AMOUNT_CANNOT_BE_NULL)))
				.andExpect(jsonPath("code", is(ACTION_NOT_NULL_PROPERTIES.getCode())));
	}

	@Test
	public void should_not_accept_post_without_timestamp() throws Exception {
		String json = "{ \"amount\": 123.45, \"timestamp\": null }";

		mockMvc.perform(post("/transactions")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("code", is(ACTION_NOT_NULL_PROPERTIES.getCode())))
				.andExpect(jsonPath("message", is(MSG_TIMESTAMP_CANNOT_BE_NULL)))
				.andExpect(jsonPath("code", is(ACTION_NOT_NULL_PROPERTIES.getCode())));
	}

	@Test
	public void should_not_accept_post_with_timestamp_older_than_sixty_seconds() throws Exception {
		long oldTimestamp = now().minus(65, SECONDS).toEpochSecond();
		String json = String.format("{ \"amount\": 123.45, \"timestamp\": %s }", oldTimestamp);

		mockMvc.perform(post("/transactions")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json))
				.andExpect(status().isNoContent());
	}

}
