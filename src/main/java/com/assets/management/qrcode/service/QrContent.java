package com.assets.management.qrcode.service;

import java.time.LocalDateTime;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class QrContent {
	public String itemSerialNumber;
	public String workId;
	public LocalDateTime dateAssigned;

	@Override
	public String toString() {
		return "QrContent {"
				+ "itemSerialNumber=" + itemSerialNumber + 
				", workId=" + workId + 
				", dateAssigned=" + dateAssigned + 
				"}";
	}
	
	

}
