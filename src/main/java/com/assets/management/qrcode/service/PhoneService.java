package com.assets.management.qrcode.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.jboss.logging.Logger;

import com.assets.management.qrcode.model.QRCode;
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
public class PhoneService {

	@Inject
	Logger LOG;

	@Inject
	Event<SmartPhone> phoneEvt;
//
//	@Inject
//	EntityManager entityManager;

	public byte[] persistPhone(/* @Valid */ SmartPhone phone)
			throws WriterException, IOException {
		SmartPhone.persist(phone);
		String qrString = genPhoneQR(phone);
		phoneEvt.fire(phone);
		// return book;
		return persistQrString(qrString, phone);
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

	private byte[] persistQrString(String qrCodeString, SmartPhone phone) {
		QRCode qrCode = new QRCode();
		qrCode.qrString = qrCodeString;
		qrCode.phone    = phone;       // if it fails try setPhone(phone)

		QRCode.persist(qrCode);
		LOG.info("QR Code Object : " + qrCode);
		return Base64.getDecoder().decode(qrCodeString);
	}

	// TODO: returm summary of all phones based on status

	public void onPhonePersistOrUpdate(@Observes(
			notifyObserver = Reception.IF_EXISTS
	) SmartPhone phone) {

		LOG.info("Event received with Phone object: " + phone);

		// TODO: phone object assign to the variable
	}

	private String genPhoneQR(SmartPhone phone) throws WriterException,
			IOException {

		LOG.info("QR Code Texts: " + phone);

		Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hintMap.put(EncodeHintType.MARGIN, 1);
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		BitMatrix bitMatrix = new QRCodeWriter().encode(
				String.valueOf(phone), BarcodeFormat.QR_CODE, 150, 150, hintMap
		);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
		byte[] QRImage = pngOutputStream.toByteArray();

		// convert byte array into base64 encode String to be persisted
		String qrString = Base64.getEncoder().encodeToString(QRImage);

		LOG.info("QR Code string representation: " + qrString);
		return qrString;
	}

//	public byte[] getPhoneQR(SmartPhone phone) {
//		
//	}

//	public byte[] genPhoneQR(String content, QRSize size)
//			throws WriterException, IOException {
//		
//		LOG.info("QR Code Texts: " + content);
//		
//		Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
//		hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//		hintMap.put(EncodeHintType.MARGIN, 1);
//		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//		
//		BitMatrix bitMatrix = new QRCodeWriter().encode(
//				content, BarcodeFormat.QR_CODE, size.getSize(), size.getSize(),
//				hintMap
//				);
//		
//		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//		MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
//		byte[] QRImage = pngOutputStream.toByteArray();
//		
//		// convert byte array into base64 encode String to be persisted
//		String qRString = Base64.getEncoder().encodeToString(QRImage);
//		
//		// TODO: persist QR details ie qrstring and qrsize
//		
//		LOG.info("QR Code string representation: " + qRString);
//		return QRImage;
//	}
}
