package com.exchangerate.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.exchangerate.app.data.ExchangeRateDataStore;

/**
 * UT for controller end points.
 * @author vijay
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeRateControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	ExchangeRateDataStore exchangeRateDataStore;

	private MockMvc mockMvc;

	@BeforeAll
	public void setup() throws JAXBException, URISyntaxException {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		mockExchangeRateData();
	}
	
	@Test
	public void testPing() throws Exception {
		mockMvc.perform(get("/ers/ping")).andExpect(status().isOk())
				.andExpect(content().string("Exchange Rate Service is Up"));
	}
	
	@Test
	public void testGetReferenceRateToEuro() throws Exception {
		mockMvc.perform(get("/ers/referencerate/euro?currency=USD")).andExpect(status().isOk())
				.andExpect(content().string("1 EUR equals 1.1933 USD"));
	}
	
	@Test
	public void testGetReferenceRate() throws Exception {
		mockMvc.perform(get("/ers/exchangerate/usd/to/inr")).andExpect(status().isOk())
				.andExpect(content().string("1 USD equals 72.6774 INR"));
	}
	
	@Test
	public void testGetAllSupportedCurrenciesWithRequestCount() throws Exception {
		testGetReferenceRate(); //make USD and INR request counts = 1
		mockMvc.perform(get("/ers/supportedcurrencies/requestcount")).andExpect(status().isOk())
				.andExpect(content().string("{\"currencyRequests\":[{\"currency\":\"EUR\",\"requestedCount\":0},{\"currency\":\"USD\",\"requestedCount\":1},{\"currency\":\"INR\",\"requestedCount\":1}]}"));
	}
	
	@Test
	public void testGetExchangeAmount() throws Exception {
		mockMvc.perform(get("/ers/convert/100/currency/USD/to/EUR")).andExpect(status().isOk())
				.andExpect(content().string("100.0 USD equals 83.8 EUR"));
	}
	
	@Test
	public void testGetChartLink() throws Exception {
		mockMvc.perform(get("/ers/currencychart/EUR/to/USD")).andExpect(status().isOk())
				.andExpect(content().string("https://www.xe.com/currencycharts/?from=EUR&to=USD"));
	}
	
	@Test
	public void testGetAllSupportedCurrencies() throws Exception {
		mockMvc.perform(get("/ers/supportedcurrencies")).andExpect(status().isOk())
				.andExpect(content().string("{\"currency\":[\"EUR\",\"USD\",\"INR\"]}"));
	}
	
	private void mockExchangeRateData() {
		Map<String, Double> testRateValues = new HashMap<>();
		testRateValues.put("USD", 1.1933d);
		testRateValues.put("INR", 86.7260d);
		testRateValues.put("EUR", 1.0d);
		exchangeRateDataStore.setEuroRate(testRateValues);
	}
}
