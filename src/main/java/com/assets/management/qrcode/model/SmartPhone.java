package com.assets.management.qrcode.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "smart_phones")
public class SmartPhone extends Item {

	@NotNull
	@Column(name = "topped_up")
	public Boolean toppedUp;

	@Column(name = "topup_amount")
	public BigDecimal topupAmout;

	@NotNull
	public Boolean paid;

	// Baeldung
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "qrcode_id", referencedColumnName = "id")
//	public QRCode code;

	// Vlad
	@OneToOne(
			mappedBy = "phone", cascade = CascadeType.ALL,
			fetch = FetchType.LAZY, optional = false
	)
	@PrimaryKeyJoinColumn
	public QRCode code;

	public void addQR(QRCode code) {
		if (code == null) {
			if (this.code != null) {
				this.code.setPhone(null);
			}
		} else {

			code.setPhone(this);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n{");
		builder.append("\nSN: ").append(serialNumber).append("\t");
		builder.append("\nBRAND: ").append(brand).append("\t");
//		builder.append("\nCOMMISSIONED DATE: ").append(
//				commissionedDate.equals(null) ? "Still in stock"
//						: commissionedDate
//		).append("\t");
		builder.append("\nTOPPED UP: ").append(toppedUp ? "Yes" : "No");
		builder.append("\nPAID: ").append(paid ? "Yes" : "No");
		builder.append("\n}");
		return builder.toString();
	}
}
