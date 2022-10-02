package com.assets.management.qrcode.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.common.constraint.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Item extends PanacheEntity {

	@Column(length = 100)
	public String name;

	@NotNull
	@Column(length = 100, nullable = false)
	public String brand;

	@NotNull
	@Column(name = "serial_number", length = 100, nullable = false)
	public String serialNumber;

	@Column(length = 100)
	public String manufacturer;

	@Column(name = "manufactured_date")
	public Instant manufacturedDate;

	@NotNull
	@Column(name = "stocked_at")
	public Instant stockedAt;

	@NotNull
	@Column(name = "commissioning_date")
	public LocalDate commissionedDate;

	@Column(length = 3000)
	public String comment;

	@Enumerated(EnumType.STRING)
	public Category category;

	@Enumerated(EnumType.STRING)
	public Status status;

	@Transient
	public Integer timeInUse;

	@PostLoad
	@PostPersist
	@PostUpdate
	protected void calculateAge() {
		if (commissioningDate == null) {
			timeInUse = null;
			return;
		}
		timeInUse = Period.between(commissioningDate, LocalDate.now())
				.getYears();
	}
}
