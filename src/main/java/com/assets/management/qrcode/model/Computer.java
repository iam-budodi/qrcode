package com.assets.management.qrcode.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
//@Table(name = "computers")
//@DiscriminatorValue("C")
public class Computer extends Device {
//public class Computer {

	@NotNull
	public Boolean mouse;
//	public String    brand;
//	public String    name;
//	public String    comment;
//	public Instant   manufacturedDate;
//	public String    manufacturer;
//	public LocalDate commissionedDate;
//	public String    serialNumber;
	
//	@OneToOne(
//			mappedBy = "computer", cascade = CascadeT ype.ALL,
//			fetch = FetchType.LAZY, optional = false
//	)
//	@PrimaryKeyJoinColumn
//	public QRCode qrCode;
//
//	public void addQR(QRCode qrCode) {
//		if (qrCode == null) {
//			if (this.qrCode != null) {
//				this.qrCode.setComputer(null);
//			}
//		} else {
//
//			qrCode.setComputer(this);
//		}
//	}  
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n{");
		builder.append("\nSN: ").append(serialNumber).append("\t");
		builder.append("\nBRAND: ").append(brand).append("\t");
		builder.append("\nMANUFACTURER: ").append(manufacturer).append("\t");
//		builder.append("\nCOMMISSIONED DATE: ").append(
//				commissionedDate.equals(null) ? "Still in stock"
//						: commissionedDate
//		).append("\t");
		builder.append("\nMOUSE: ").append(mouse ? "Yes" : "No");
		builder.append("\n}");
		return builder.toString();
	}

}
