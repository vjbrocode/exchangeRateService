package com.exchangerate.app.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * In-memory data store - Can be modified to use external storages
 * @author vijay
 *
 */
@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ExchangeRateDataStore {
	
	private Map<String, Double> euroRate = new ConcurrentHashMap<>();
	
	private Map<String, AtomicLong> requestCount = new ConcurrentHashMap<>();
	
	public Map<String, Double> getEuroRate() {
		return euroRate;
	}

	public void setEuroRate(Map<String, Double> euroRate) {
		this.euroRate = euroRate;
	}

	public Map<String, AtomicLong> getRequestCount() {
		//populate uncalled currencies with 0 value
		euroRate.entrySet().forEach(item -> {
			String key = item.getKey();
			if(!requestCount.containsKey(key)) {
				requestCount.put(key, new AtomicLong());
			}
		});
		
		return requestCount;
	}
	
	public void setRequestCount(Map<String, AtomicLong> requestCount) {
		this.requestCount = requestCount;
	}
	
	public void updateRequestCount(String currency) {
		if(requestCount.containsKey(currency.toUpperCase())) {
			AtomicLong val = requestCount.get(currency.toUpperCase());
			val.getAndIncrement();
			requestCount.put(currency.toUpperCase(), val);
		} else {
			requestCount.put(currency.toUpperCase(), new AtomicLong(1));
		}
	}
}
