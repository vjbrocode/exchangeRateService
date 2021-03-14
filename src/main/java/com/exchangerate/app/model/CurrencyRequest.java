package com.exchangerate.app.model;

public class CurrencyRequest {
	private String currency;
	private Long requestedCount;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Long getRequestedCount() {
		return requestedCount;
	}
	public void setRequestedCount(Long requestedCount) {
		this.requestedCount = requestedCount;
	}
	
	@Override
	public String toString() {
		return "CurrencyRequest [currency=" + currency + ", requestedCount=" + requestedCount + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((requestedCount == null) ? 0 : requestedCount.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyRequest other = (CurrencyRequest) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (requestedCount == null) {
			if (other.requestedCount != null)
				return false;
		} else if (!requestedCount.equals(other.requestedCount))
			return false;
		return true;
	}
}
