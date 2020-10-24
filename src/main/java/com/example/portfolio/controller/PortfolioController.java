package com.example.portfolio.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

import com.example.portfolio.domain.Portfolio;
import com.example.portfolio.domain.PortfolioCriteria;
import com.example.portfolio.service.PortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private PortfolioService portfolioService;

	PortfolioController(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	@PostMapping(path = "/select", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Portfolio> selectPortfolios(@RequestBody PortfolioCriteria portfolioCriteria) {
		log.info("Select request {}", portfolioCriteria);
		return portfolioService.getSelectedPortfolios(portfolioCriteria);
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Portfolio>> allPortfolioGroupedByLongAndShort() {
		return portfolioService.getPortfoliosGroupedBy();
	}



}
