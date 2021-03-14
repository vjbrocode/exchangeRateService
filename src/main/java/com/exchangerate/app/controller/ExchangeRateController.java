package com.exchangerate.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exchangerate.app.model.CurrencyList;
import com.exchangerate.app.model.CurrencyRequestList;
import com.exchangerate.app.service.ExchangeRateService;

/**
 * Swagger Documentation for these end points can be found here -> http://localhost:8080/swagger-ui/index.html
 * @author vijays3
 *
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/ers")
public class ExchangeRateController {
	
	@Autowired
	ExchangeRateService exchangeRateService;
	
	static Logger logger = LoggerFactory.getLogger(ExchangeRateController.class);
	
	/**
	 * Sample GET URL -> http://localhost:8080/ers/referencerate/euro?currency=USD
	 * Sample Response -> 1 EUR equals 1.1969 USD
	 * @param currency
	 * @return
	 */
	@GetMapping("/referencerate/euro")
	@ResponseBody
	public ResponseEntity<String> getReferenceRateToEuro(@RequestParam(name = "currency", required = true) String currency) {
		if(isNotValid(currency)) {
			return failureResponse(currency, "");
		}
		
		String responseBody = String.format("1 EUR equals %s %s", exchangeRateService.getEuroRateForCurrency(currency), currency.toUpperCase());
		return successResponse(responseBody);
	}
	
	/**
	 * Sample GET URL -> http://localhost:8080/ers/exchangerate/USD/to/INR
	 * Response Body -> 1 USD equals 72.5942 INR
	 * @param fromcurrency
	 * @param tocurrency
	 * @return
	 */
	@GetMapping("/exchangerate/{fromcurrency}/to/{tocurrency}")
	@ResponseBody
	public ResponseEntity<String> getReferenceRate(@PathVariable("fromcurrency") String fromcurrency, @PathVariable("tocurrency") String tocurrency) {
		if(isNotValid(fromcurrency) || isNotValid(tocurrency)) {
			return failureResponse(fromcurrency, tocurrency);
		}
		
		String responseBody = String.format("1 %s equals %s %s", fromcurrency.toUpperCase(), exchangeRateService.getExchangeRate(fromcurrency, tocurrency), tocurrency.toUpperCase());
		return successResponse(responseBody);
	}
	
	/**
	 * Request URL -> http://localhost:8080/ers/supportedcurrencies/requestcount
	 * @return
	 */
	@GetMapping("/supportedcurrencies/requestcount")
	@ResponseBody
	public ResponseEntity<CurrencyRequestList> getAllSupportedCurrenciesWithRequestCount() {
		return successResponse(exchangeRateService.getRequestCount());
	}
	
	/**
	 * Sample request URL -> http://localhost:8080/ers/convert/100/currency/USD/to/EUR
	 * Response Body -> 100.0 USD equals 83.55 EUR
	 * @param amount
	 * @param fromcurrency
	 * @param tocurrency
	 * @return
	 */
	@GetMapping("/convert/{amount}/currency/{fromcurrency}/to/{tocurrency}")
	@ResponseBody
	public ResponseEntity<String> getExchangeAmount(@PathVariable("amount") Double amount, @PathVariable("fromcurrency") String fromcurrency, @PathVariable("tocurrency") String tocurrency) {
		if(isNotValid(fromcurrency) || isNotValid(tocurrency)) {
			return failureResponse(fromcurrency, tocurrency);
		}
		
		String responseBody = String.format("%s %s equals %s %s", amount, fromcurrency, exchangeRateService.getConversionAmount(amount, fromcurrency, tocurrency), tocurrency.toUpperCase());
		return successResponse(responseBody);
	}
	
	/**
	 * Sample request URL -> http://localhost:8080/ers/currencychart/EUR/to/USD
	 * Response Body -> https://www.xe.com/currencycharts/?from=EUR&to=USD
	 * @param fromcurrency
	 * @param tocurrency
	 * @return
	 */
	@GetMapping("/currencychart/{fromcurrency}/to/{tocurrency}")
	@ResponseBody
	public ResponseEntity<String> getChartLink(@PathVariable("fromcurrency") String fromcurrency, @PathVariable("tocurrency") String tocurrency) {
		if(isNotValid(fromcurrency) || isNotValid(tocurrency)) {
			return failureResponse(fromcurrency, tocurrency);
		}
		
		String responseBody = exchangeRateService.getCurrenyChartUrl(fromcurrency, tocurrency);
		return successResponse(responseBody);
	}
	
	/**
	 * Request URL -> http://localhost:8080/ers/supportedcurrencies
	 * @return
	 */
	@GetMapping("/supportedcurrencies")
	@ResponseBody
	public ResponseEntity<CurrencyList> getAllSupportedCurrencies() {
		List<String> supportedCurrencies = exchangeRateService.getAllSupportedCurrencies();
		CurrencyList currencyList = new CurrencyList();
		currencyList.setCurrency(supportedCurrencies);
		return successResponse(currencyList);
	}
	
	/**
	 * Ping test URL -> http://localhost:8080/ers/ping
	 * Response -> Exchange Rate Service is Up
	 * @return
	 */
	@GetMapping("/ping")
	public String ping() {
		return "Exchange Rate Service is Up";
	}

	private ResponseEntity<String> failureResponse(String responseBody) {
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}
	
	private ResponseEntity<String> failureResponse(String fromcurrency, String tocurrency) {
		List<String> supportedCurrencies = exchangeRateService.getAllSupportedCurrencies();
		String responseBody = "Unsupported Currency Requested: " + fromcurrency + "/" + tocurrency + ". Supported = " + supportedCurrencies;
		return failureResponse(responseBody);
	}
	
	private ResponseEntity<String> successResponse(String responseBody) {
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	
	private ResponseEntity<CurrencyList> successResponse(CurrencyList responseBody) {
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	
	private ResponseEntity<CurrencyRequestList> successResponse(CurrencyRequestList responseBody) {
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}

	private boolean isNotValid(String currency) {
		if(!exchangeRateService.isSupportedCurrency(currency.toUpperCase())) {
			return true;
		}
		return false;
	}
}
