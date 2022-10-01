package com.assets.management.qrcode.model;

public enum Encoding {
	
	MECARD("MECARD"), vCARD("vCARD");
	
	private String value;

	private Encoding(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
