package com.exchangerate.app.model;

import java.util.List;

public class CurrencyRequestList {
	private List<CurrencyRequest> currencyRequests;

	public List<CurrencyRequest> getCurrencyRequests() {
		return currencyRequests;
	}

	public void setCurrencyRequests(List<CurrencyRequest> currencyRequests) {
		this.currencyRequests = currencyRequests;
	}

	@Override
	public String toString() {
		return "CurrencyRequestList [currencyRequests=" + currencyRequests + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyRequests == null) ? 0 : currencyRequests.hashCode());
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
		CurrencyRequestList other = (CurrencyRequestList) obj;
		if (currencyRequests == null) {
			if (other.currencyRequests != null)
				return false;
		} else if (!currencyRequests.equals(other.currencyRequests))
			return false;
		return true;
	}
}
