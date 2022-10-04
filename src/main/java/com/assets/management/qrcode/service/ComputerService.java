package com.assets.management.qrcode.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

import org.jboss.logging.Logger;

import com.assets.management.qrcode.model.Computer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@ApplicationScoped
public class ComputerService {

	@Inject
	Logger LOG;
	
	public Computer persistComputer(@Valid Computer computer) {
		Computer.persist(computer);
		return computer;
	}
	
	public byte[] getByteQRCode(String content /*, QRSize size*/)
			throws WriterException, IOException {

		LOG.info("QR Code Texts: " + content);

		Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hintMap.put(EncodeHintType.MARGIN, 1);
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
 
//		BitMatrix bitMatrix = new QRCodeWriter().encode(
//				content, BarcodeFormat.QR_CODE, size.getSize(), size.getSize(),
//				hintMap
//		);
		BitMatrix bitMatrix = new QRCodeWriter().encode(
				content, BarcodeFormat.QR_CODE, 125, 125,
				hintMap
				);
 
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(
				bitMatrix, "png", pngOutputStream
		);
		byte[] QRImage = pngOutputStream.toByteArray();
		
		// convert byte array into base64 encode String to be persisted 
		String qRString = Base64.getEncoder().encodeToString(QRImage);
		
		// TODO: persist QR details ie qrstring and qrsize
		
		LOG.info("QR Code string representation: " + qRString);
//		return QRImage;
		return Base64.getDecoder().decode(qRString);
	}
}
