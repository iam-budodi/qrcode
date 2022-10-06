package com.assets.management.qrcode.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.jboss.logging.Logger;

import com.assets.management.qrcode.model.Computer;
import com.assets.management.qrcode.model.SmartPhone;
import com.assets.management.qrcode.model.Status;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import io.quarkus.hibernate.orm.panache.Panache;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class DeviceService {

	@Inject
	Logger LOG; 

	public byte[] persistPhone(@Valid SmartPhone phone) throws WriterException,
			IOException {
		phone.qrString = genQrString(phone);
		phone.stockedAt = Instant.now(); 
//		phone.commissionedDate = Instant.now();
		SmartPhone.persist(phone);
		return Base64.getDecoder().decode(phone.qrString);
	}

	public byte[] persistComputer(@Valid Computer computer)
			throws WriterException, IOException {
		computer.qrString = genQrString(computer);
		Computer.persist(computer);
		return Base64.getDecoder().decode(computer.qrString);
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	public List<SmartPhone> findAllPhones() {
		return SmartPhone.listAll();
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	public SmartPhone findPhoneById(Long id) {
		Optional<SmartPhone> phone = SmartPhone.findByIdOptional(id);
		return phone.orElseThrow(() -> new NotFoundException());
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	public List<SmartPhone> listAvailablePhones() {
		return SmartPhone.list("status", Status.Available);
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	public List<SmartPhone> listTakenPhones() {
		return SmartPhone.list("status", Status.Taken);
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	public List<SmartPhone> listFaultyPhones() {
		return SmartPhone.list("status", Status.Faulty);
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	public List<SmartPhone> listEOLPhones() {
		return SmartPhone.list("status", Status.EOL);
	}

	@Transactional(Transactional.TxType.SUPPORTS)
	public Long countAllPhones() {
		return SmartPhone.count();
	}

	public SmartPhone updatePhone(@Valid SmartPhone phone, Long id) {
		SmartPhone sPhone = Panache.getEntityManager().getReference(
				SmartPhone.class, id
		);

		Panache.getEntityManager().merge(phone);
		return sPhone;
	}

	public void deletePhone(Long id) {
		SmartPhone phone = Panache.getEntityManager().getReference(
				SmartPhone.class, id
		);

		phone.delete();
	}

	private String genQrString(Object item) throws WriterException,
			IOException {

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hintMap.put(EncodeHintType.MARGIN, 1);
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		BitMatrix bitMatrix = null;

		LOG.info("QR Code Texts: " + item);

		if (item instanceof SmartPhone phone) {
			bitMatrix = new QRCodeWriter().encode(
					String.valueOf(phone), BarcodeFormat.QR_CODE, 150, 150,
					hintMap
			);
		} else if (item instanceof Computer computer) {
			bitMatrix = new QRCodeWriter().encode(
					String.valueOf(computer), BarcodeFormat.QR_CODE, 150, 150,
					hintMap
			);
		}

		MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);

		return Base64.getEncoder().encodeToString(
				pngOutputStream.toByteArray()
		);
	}
}
