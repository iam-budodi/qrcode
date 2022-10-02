package com.assets.management.qrcode.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "smart_phone")
public class SmartPhone extends Item {

	@NotNull
	@Column(name = "topped_up")
	public Boolean toppedUp;
	
	@Column(name = "topup_amount")
	public BigDecimal topupAmout;
	 
	@NotNull
	public Boolean paid;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n{");
		builder.append("\nSN: ").append(serialNumber).append("\t");
		builder.append("\nBRAND: ").append(brand).append("\t"); 
		builder.append("\nCOMMISSIONED DATE: ").append(
				commissionedDate.equals(null) ? "Still in stock"
						: commissionedDate
		).append("\t");
		builder.append("\nTOPPED UP: ").append(toppedUp ? "Yes" : "No");
		builder.append("\nPAID: ").append(paid ? "Yes" : "No");
		builder.append("\n}");
		return builder.toString();
	}
 }
 