package com.exchangerate.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exchangerate.app.config.ExchangeRateConfig;
import com.exchangerate.app.data.ExchangeRateDataStore;
import com.exchangerate.app.model.CurrencyRequest;
import com.exchangerate.app.model.CurrencyRequestList;
import com.exchangerate.app.util.Utils;

@Service
public class ExchangeRateService {
	
	@Autowired
	ExchangeRateConfig exchangeRateConfig;

	@Autowired
	ExchangeRateDataStore exchangeRateDataStore;

	public String getExchangeRate(String fromcurrency, String tocurrency) {
		exchangeRateDataStore.updateRequestCount(fromcurrency);
		exchangeRateDataStore.updateRequestCount(tocurrency);
		
		Double resultVal = exchangeRate(fromcurrency, tocurrency);
		
		return resultVal + "";
	}

	private Double exchangeRate(String fromcurrency, String tocurrency) {
		Double fromVal = exchangeRateDataStore.getEuroRate().get(fromcurrency.toUpperCase());
		Double toVal = exchangeRateDataStore.getEuroRate().get(tocurrency.toUpperCase());
		
		Double val = toVal/fromVal;
		Double resultVal = Utils.round(val);
		return resultVal;
	}

	public String getEuroRateForCurrency(String currency) {
		exchangeRateDataStore.updateRequestCount(currency);
		
		return exchangeRateDataStore.getEuroRate().get(currency.toUpperCase()) + "";
	}
	
	public CurrencyRequestList getRequestCount() {
		CurrencyRequestList currencyRequestList = new CurrencyRequestList();
		List<CurrencyRequest> list = new ArrayList<>();
		
		exchangeRateDataStore.getRequestCount().entrySet().forEach(item -> {
			String currency = item.getKey();
			AtomicLong count = item.getValue();
			CurrencyRequest currencyRequest = new CurrencyRequest();
			currencyRequest.setCurrency(currency);
			currencyRequest.setRequestedCount(count.get());
			list.add(currencyRequest);
		});
		 
		currencyRequestList.setCurrencyRequests(list);
		return currencyRequestList;
	}
	
	public boolean isSupportedCurrency(String currency) {
		if(exchangeRateDataStore.getEuroRate().containsKey(currency.toUpperCase())) {
			return true;
		}
		return false;
	}
	
	public List<String> getAllSupportedCurrencies() {
		List<String> allSupportedCurrencies = new ArrayList<>();
		exchangeRateDataStore.getEuroRate().entrySet().forEach( c -> allSupportedCurrencies.add(c.getKey().toUpperCase()));
		return allSupportedCurrencies;
	}

	public String getConversionAmount(Double amount, String fromcurrency, String tocurrency) {
		exchangeRateDataStore.updateRequestCount(fromcurrency);
		exchangeRateDataStore.updateRequestCount(tocurrency);
		
		return  Utils.round(amount * exchangeRate(fromcurrency, tocurrency)) + "";
	}

	public String getCurrenyChartUrl(String fromcurrency, String tocurrency) {
		return exchangeRateConfig.getCurrencyChartUrlTemplate().replace("{{FROM}}", fromcurrency.toUpperCase()).replace("{{TO}}", tocurrency.toUpperCase());
	}
	
	
}
