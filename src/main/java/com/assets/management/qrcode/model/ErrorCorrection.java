package com.assets.management.qrcode.model;

public enum ErrorCorrection {
	
	Low("L"), Medium("M"), Quartile("Q"), High("H");
	
	private String value;

	private ErrorCorrection(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
