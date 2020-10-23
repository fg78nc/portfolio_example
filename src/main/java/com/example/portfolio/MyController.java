package com.example.portfolio;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

 	private static Map<String, com.example.portfolio.Portfolio> data = new HashMap<>();
 	private static final String PORTFOLIO_NAME = "Portfolio_1";
 	private static final List<String> securityIDs = new ArrayList<>();

 	static {
 		for (int i = 0; i < 10 ; i++){
 			securityIDs.add("ID" + i);
		}
 		Random random = new Random();
 		for (int i = 0 ; i < 10; i++){
 			int shortPosition = i % 2 == 0? 10 : 20;
 			int longPosition = i % 2 == 0? 50 : 100;
 			String side = i % 2 == 0 ? "long" : "short";
 			data.put(UUID.randomUUID().toString(),
					new Portfolio(shortPosition, longPosition,
							side, securityIDs.get(random.nextInt(9)), random.nextInt(50), random.nextInt(50)));
		}
	}

	@PostMapping(path = "/select", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Portfolio> selectPortfolios(@RequestBody PortfolioCriteria portfolioCriteria) {

 		log.info("Select request {}", portfolioCriteria);

 		List<Portfolio> result =  data.values().stream().filter( p -> {
 			if (Objects.nonNull(portfolioCriteria.getSecurityId()) && !portfolioCriteria.getSecurityId().equals(p.getSecurityId())){
 				return false;
			}
 			if (Objects.nonNull(portfolioCriteria.getShortPosition()) && portfolioCriteria.getShortPosition() != p.getShortPosition()){
 				return false;
			}
 			if (Objects.nonNull(portfolioCriteria.getSide()) && !portfolioCriteria.getSide().equals(p.getSide())){
				return false;
			}
 			return true;
		}).collect(Collectors.toList());

 		if (result.isEmpty()){
 			throw new com.example.portfolio.DataNotFoundException("No Portfolios matching search criteria were found");
		}

		sortResult(portfolioCriteria, result);

		return result;
	}

	private void sortResult(PortfolioCriteria portfolioCriteria, List<Portfolio> result) {
		String orderBy = portfolioCriteria.getOrderBy();

		if(Objects.nonNull(orderBy)){
			switch (orderBy){
		   case "shortPosition" : result.sort(Comparator.comparingInt(Portfolio::getShortPosition)); break;
		   case "side" : result.sort(Comparator.comparing(Portfolio::getSide)); break;
		   case "securityId" : result.sort(Comparator.comparing(Portfolio::getSecurityId)); break;
		   }
		}
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<Portfolio>> allPortfolioGroupedByLongAndShort(){

		Map<String, List<Portfolio>> result = new HashMap<>();
		List<Portfolio> shortPositions = new ArrayList<>();
		List<Portfolio> longPositions = new ArrayList<>();

		data.forEach((k,v) -> {
			if (v.getSide().equals("short")) {
				shortPositions.add(v);
			} else {
				longPositions.add(v);
			}
		});

		result.put("long", longPositions);
		result.put("short", shortPositions);
		return result;
	}

}
