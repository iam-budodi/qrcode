package com.assets.management.qrcode.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "t_qrcode")
public class QRCode extends PanacheEntity {

	public String code;
//	public Custodian custodian;

	@Enumerated(EnumType.STRING)
	public Encoding encoding;
	
	@Enumerated(EnumType.STRING)
	public ErrorCorrection correction;
	
	@Enumerated(EnumType.STRING)
	public QRSize size;
	
}
