package com.example.portfolio;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = PortfolioApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PortfolioApplicationTests {

	@LocalServerPort
	private int port;

	private String url = "http://localhost";

	private static RestTemplate restTemplate = null;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		url = String.format("%s:%d", url, port);
	}

	@Test
	@SuppressWarnings("unchecked")
	void shouldReturnMapOfPortfoliosGroupedBySide() {
		Map<?, List<?>> results = restTemplate.getForObject(url.concat("/all"), Map.class);
		assertNotNull(results);
		assertEquals(2, results.size()); ;
		assertEquals(5, results.get("long").size());
		assertEquals(5, results.get("short").size());
	}

	@Test()
	@SuppressWarnings("unchecked")
	void shouldThrowExceptionWhenNoCriteriaMatch() {
		PortfolioCriteria portfolioCriteria = new PortfolioCriteria();
		portfolioCriteria.setShortPosition(10);
		portfolioCriteria.setSide("short");
		ResponseEntity<List> response = null;
		assertThrows(HttpClientErrorException.class, () -> restTemplate
				.postForEntity(url.concat("/select"), portfolioCriteria, List.class));
	}


	@Test
	void shouldReturn10ElementsSortedBySecurityId() {
		PortfolioCriteria portfolioCriteria = new PortfolioCriteria();
		portfolioCriteria.setOrderBy("securityId");

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<PortfolioCriteria> postRequest = new HttpEntity<>(portfolioCriteria, headers);
		final List<Portfolio> parameterizedList = getParameterizedList(url
				.concat("/select"), postRequest, new ParameterizedTypeReference<List<Portfolio>>() { });

		boolean sorted = IntStream.range(0, parameterizedList.size() - 1).allMatch(x ->
				parameterizedList.get(x).getSecurityId().compareTo(parameterizedList.get(x + 1).getSecurityId()) <= 0);

		assertAll(
				() -> assertNotNull(parameterizedList),
				() -> assertEquals(10, parameterizedList.size()),
				() -> assertTrue(sorted));
	}

	public <T> List<T> getParameterizedList(String uri, HttpEntity<PortfolioCriteria> postRequest, ParameterizedTypeReference<List<T>> responseType) {
		return restTemplate.exchange(uri, HttpMethod.POST, postRequest, responseType).getBody();
	}
}
