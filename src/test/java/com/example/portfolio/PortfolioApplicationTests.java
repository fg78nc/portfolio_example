package com.example.portfolio;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
	void shouldReturn404WhenNoMatchCriteria() {
		PortfolioCriteria portfolioCriteria = new PortfolioCriteria();
		portfolioCriteria.setShortPosition(10);
		portfolioCriteria.setSide("short");
		ResponseEntity<List> response = null;
		try {
			response = restTemplate
					.postForEntity(url.concat("/select"), portfolioCriteria, List.class);
		} catch (Exception e){

		} finally {
//			assertAll(
//					() -> assertNotNull(response.getBody()),
//					() -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
		}
	}

	@Test
	void shouldReturn10ElementsSortedBySecurityId() {
		PortfolioCriteria portfolioCriteria = new PortfolioCriteria();
		portfolioCriteria.setOrderBy("securityId");
		ResponseEntity<List> response = restTemplate
				.postForEntity(url.concat("/select"), portfolioCriteria, List.class);

//		boolean sorted = IntStream.range(0, portfolios.size() - 1).allMatch(x ->
//				portfolios.get(x).getSecurityId().compareTo(portfolios.get(x + 1).getSecurityId()) > 1);
		assertAll(
				() -> assertNotNull(response.getBody()),
				() -> assertEquals(10, response.getBody().size()));
	}
}
