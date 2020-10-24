package com.example.portfolio.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.portfolio.domain.Portfolio;
import com.example.portfolio.domain.PortfolioCriteria;
import com.example.portfolio.exception.DataNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

	private static final Map<String, Portfolio> data = new HashMap<>();
	private static final List<String> securityIDs = new ArrayList<>();

	static {
		for (int i = 0; i < 10; i++) {
			securityIDs.add("ID" + i);
		}
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int shortPosition = i % 2 == 0 ? 10 : 20;
			int longPosition = i % 2 == 0 ? 50 : 100;
			String side = i % 2 == 0 ? "long" : "short";
			data.put(UUID.randomUUID().toString(),
					new Portfolio(shortPosition, longPosition,
							side, securityIDs.get(random.nextInt(9)), random.nextInt(50), random.nextInt(50)));
		}
	}


	public List<Portfolio> getSelectedPortfolios(PortfolioCriteria portfolioCriteria) {
		List<Portfolio> result = data.values().stream().filter(p -> {
			if (Objects.nonNull(portfolioCriteria.getSecurityId()) && !portfolioCriteria.getSecurityId()
					.equals(p.getSecurityId())) {
				return false;
			}
			if (Objects.nonNull(portfolioCriteria.getShortPosition()) && portfolioCriteria.getShortPosition() != p
					.getShortPosition()) {
				return false;
			}
			if (Objects.nonNull(portfolioCriteria.getSide()) && !portfolioCriteria.getSide().equals(p.getSide())) {
				return false;
			}
			return true;
		}).collect(Collectors.toList());

		if (result.isEmpty()) {
			throw new DataNotFoundException("No Portfolios matching search criteria were found");
		}

		sortResult(portfolioCriteria, result);
		return result;
	}

	public Map<String, List<Portfolio>> getPortfoliosGroupedBy() {
		return data.values().stream().collect(Collectors.groupingBy(Portfolio::getSide));
	}

	private void sortResult(PortfolioCriteria portfolioCriteria, List<Portfolio> result) {
		String orderBy = portfolioCriteria.getOrderBy();

		if (Objects.nonNull(orderBy)) {
			switch (orderBy) {
			case "shortPosition":
				result.sort(Comparator.comparingInt(Portfolio::getShortPosition)); break;
			case "side":
				result.sort(Comparator.comparing(Portfolio::getSide)); break;
			case "securityId":
				result.sort(Comparator.comparing(Portfolio::getSecurityId)); break;
			}
		}
	}

}
