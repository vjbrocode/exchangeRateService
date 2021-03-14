package com.exchangerate.app.model;

import java.util.List;

public class Cube { 
	public String time;
	
	public String currency;
	public Double rate;
	public List<Cube> Cube;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public List<Cube> getCube() {
		return Cube;
	}
	public void setCube(List<Cube> cube) {
		Cube = cube;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "Cube [time=" + time + ", currency=" + currency + ", rate=" + rate + ", Cube=" + Cube + "]";
	}
	
	
}