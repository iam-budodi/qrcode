package com.assets.management.qrcode.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.assets.management.qrcode.model.ErrorCorrection;
import com.assets.management.qrcode.model.QRSize;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@ApplicationScoped
public class QRCodeService {

	@Inject
	Logger LOG;
	
	public byte[] getByteQRCode(String content, QRSize size, ErrorCorrection corretionLevel)
			throws WriterException, IOException {
		String imageFormat = "png"; // could be jpeg, gif etc

		LOG.info("QR Code Texts: " + content);

		Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hintMap.put(EncodeHintType.MARGIN, 1);
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//		QRCodeWriter qrWriter = new QRCodeWriter();

		BitMatrix bitMatrix = new QRCodeWriter().encode(
				content, BarcodeFormat.QR_CODE, size.getSize(), size.getSize(),
				hintMap
		);

//		LOG.info("QR Code bit matrix: " + bitMatrix.toString());
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(
				bitMatrix, imageFormat, pngOutputStream
		);
		byte[] QRImage = pngOutputStream.toByteArray();
		
		// convert byte array into base64 encode String to be persisted 
		String qRString = Base64.getEncoder().encodeToString(QRImage);
		LOG.info("QR Code string representation: " + qRString);
		return QRImage;
	}
}
