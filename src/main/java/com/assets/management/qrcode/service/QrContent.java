package com.assets.management.qrcode.service;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class QrContent {
 
//	@NotNull
//	@JsonProperty("device_number")
	public Long id;
	
//	@NotNull
//	@JsonProperty("serial_number")
	public String serialNumber;
	
//	@NotNull
	public String modelName;
	
//	@NotNull
//	@JsonProperty("generated_at")
	public Instant stockedAt;

	@Override
	public String toString() {
		return "QrContent [id=" + id + ", serialNumber=" + serialNumber
				+ ", modelName=" + modelName + ", stockedAt=" + stockedAt + "]";
	} 
	
	
 
}
