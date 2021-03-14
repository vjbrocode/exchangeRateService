package com.exchangerate.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.exchangerate.app.data.ExchangeRateDataStore;
import com.exchangerate.app.model.CurrencyRequest;
import com.exchangerate.app.model.CurrencyRequestList;

/**
 * UT for service class methods
 * @author vijay
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeRateServiceTest {
	
	@Autowired
	ExchangeRateDataStore exchangeRateDataStore;
	
	@Autowired
	ExchangeRateService exchangeRateService;

	@BeforeEach
	public void setup() {
		resetTestData();
		mockExchangeRateData();
	}
	
	@Test
	public void testGetExchangeRate() {
		String actualVal = exchangeRateService.getExchangeRate("USD", "INR");
		Assertions.assertEquals("72.6774", actualVal, "Wrong exchange rate value.");
	}
	
	@Test
	public void testGetEuroRateForCurrency() {
		String actualVal = exchangeRateService.getEuroRateForCurrency("USD");
		Assertions.assertEquals("1.1933", actualVal, "Wrong exchange rate value to Euro.");
	}
	
	@Test
	public void testGetRequestCount() {
		exchangeRateService.getExchangeRate("USD", "INR");
		exchangeRateService.getEuroRateForCurrency("EUR");
		
		CurrencyRequestList actualVal = exchangeRateService.getRequestCount();
		
		CurrencyRequestList expectedVal = new CurrencyRequestList();
		List<CurrencyRequest> currencyRequestList = new ArrayList<>();
		CurrencyRequest r1 = new CurrencyRequest();
		r1.setCurrency("EUR");
		r1.setRequestedCount(1l);
		CurrencyRequest r2 = new CurrencyRequest();
		r2.setCurrency("USD");
		r2.setRequestedCount(1l);
		CurrencyRequest r3 = new CurrencyRequest();
		r3.setCurrency("INR");
		r3.setRequestedCount(1l);
		currencyRequestList.add(r1);
		currencyRequestList.add(r2);
		currencyRequestList.add(r3);
		expectedVal.setCurrencyRequests(currencyRequestList);
		
		Assertions.assertEquals(expectedVal, actualVal, "Wrong exchange rate value to Euro.");
	}
	
	@Test
	public void testIsSupportedCurrency() {
		Assertions.assertTrue(exchangeRateService.isSupportedCurrency("USD"));
		Assertions.assertFalse(exchangeRateService.isSupportedCurrency("JPY"));
	}
	
	@Test
	public void testGetAllSupportedCurrencies() {
		List<String> actual = exchangeRateService.getAllSupportedCurrencies();
		List<String> expected = new ArrayList<>();
		expected.add("EUR");
		expected.add("USD");
		expected.add("INR");
		Assertions.assertEquals(expected, actual, "Wrong supported currency list.");
	}
	
	@Test
	public void testGetConversionAmount() {
		String actual = exchangeRateService.getConversionAmount(100d, "USD", "EUR");
		Assertions.assertEquals("83.8", actual, "Wrong converted value for given currency.");
	}
	
	@Test
	public void testGetCurrenyChartUrl() {
		String actual = exchangeRateService.getCurrenyChartUrl("USD", "EUR");
		Assertions.assertEquals("https://www.xe.com/currencycharts/?from=USD&to=EUR", actual, "Wrong currency chart URL.");
	}
	
	private void resetTestData() {
		exchangeRateDataStore.setRequestCount(new ConcurrentHashMap<>());
		exchangeRateDataStore.setEuroRate(new ConcurrentHashMap<>());
	}
	
	private void mockExchangeRateData() {
		Map<String, Double> testRateValues = new HashMap<>();
		testRateValues.put("USD", 1.1933d);
		testRateValues.put("INR", 86.7260d);
		testRateValues.put("EUR", 1.0d);
		exchangeRateDataStore.setEuroRate(testRateValues);
	}
}
