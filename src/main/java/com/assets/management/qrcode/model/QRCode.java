package com.assets.management.qrcode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "qr_code")
public class QRCode extends PanacheEntity {
//
//	@NotNull
//	@Column(name = "serial_number", length = 100, nullable = false)
//	public String serialNumber;
	
	@Column(name = "qr_string", length = 4000)
	public String qrString;
//	public Custodian custodian; 
	
	@OneToOne(fetch = FetchType.LAZY) // this is needed for bidirectional r/ship
	@MapsId
	@JoinColumn(name = "phone_id")
	public SmartPhone phone;

	public void setPhone(SmartPhone phone) {
		this.phone = phone;
	}
	
}
