package com.exchangerate.app.model;

public class Sender {
	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Sender [name=" + name + "]";
	}
	
}
