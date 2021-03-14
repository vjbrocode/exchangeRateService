package com.exchangerate.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ecb")
public class ExchangeRateConfig {
	
	//Reading rates URL from configuration file ["https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"]
	private String exchangeRateSourceUrl;
	
	//Reading chart URL from configuration file ["https://www.xe.com/currencycharts/?from=EUR&to=USD"]
	private String currencyChartUrlTemplate;
	
	public String getExchangeRateSourceUrl() {
		return exchangeRateSourceUrl;
	}

	public void setExchangeRateSourceUrl(String exchangeRateSourceUrl) {
		this.exchangeRateSourceUrl = exchangeRateSourceUrl;
	}

	public String getCurrencyChartUrlTemplate() {
		return currencyChartUrlTemplate;
	}

	public void setCurrencyChartUrlTemplate(String currencyChartUrlTemplate) {
		this.currencyChartUrlTemplate = currencyChartUrlTemplate;
	}
	

}
