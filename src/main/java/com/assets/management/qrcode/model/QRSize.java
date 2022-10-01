package com.assets.management.qrcode.model;

public enum QRSize {
	Small(125), Medium(190), Large(250);
	
	private int size;

	private QRSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

}
