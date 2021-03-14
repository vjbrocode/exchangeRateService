package com.exchangerate.app.task;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.exchangerate.app.config.ExchangeRateConfig;
import com.exchangerate.app.data.ExchangeRateDataStore;
import com.exchangerate.app.model.Cube;
import com.exchangerate.app.model.Envelope;

/**
 * 
 * This task will pull latest exchange rates by polling European Central Bank API for published rates
 * 
 * NOTE: 
 * Polling interval should be larger based on European Central Bank rate publish time on daily basis. 
 * Kept 5 seconds now for demo and testing purpose
 * @author vijay
 *
 */
@Component
public class RateUpdateScheduledTask {
	
	private static final Logger logger = LoggerFactory.getLogger(RateUpdateScheduledTask.class);
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
	ExchangeRateConfig exchangeRateConfig;
	
	@Autowired
	ExchangeRateDataStore exchangeRateDataStore;
	
	@Scheduled(fixedRate = 5000)
	public void refreshCurrencyExchangeRate() {
		logger.info("Updating rates...");
		
		Envelope rateData = makeApiCallAndGetCurrentRateData();
		
		if(rateData != null) {
			loadCurrentEuroRate(rateData);
		}
		
		logger.info("Current exchange rates -> " + exchangeRateDataStore.getEuroRate());
		
		logger.info("Updated new rates...");
	}

	public Envelope makeApiCallAndGetCurrentRateData() {
		logger.info("making GET call -> " + exchangeRateConfig.getExchangeRateSourceUrl());

		try {
			ResponseEntity<Envelope> response
			  = restTemplate.getForEntity(exchangeRateConfig.getExchangeRateSourceUrl(), Envelope.class);
			return response.getBody();
		} catch(Exception e) {
			logger.warn("unable to fetch latest exchange rates.");
		}
		
		return null;
	}
	
	private void loadCurrentEuroRate(Envelope data) {
		Map<String, Double> currentRate = new HashMap<>();
		for(Cube cube : data.getCube().getCube()) {
			currentRate.put(cube.getCurrency().toUpperCase(), cube.rate);
		}
		currentRate.put("EUR", 1.0d); //base case
		exchangeRateDataStore.setEuroRate(currentRate);
	}
}
