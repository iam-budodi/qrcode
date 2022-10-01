package com.assets.management.qrcode.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.enterprise.context.ApplicationScoped;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@ApplicationScoped
public class QRCodeService {

	public byte[] getByteQRCode(String qrText, int width, int height)
			throws WriterException, IOException {
		String imageFormat = "png"; // could be jpeg, gif etc

		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrWriter = new QRCodeWriter();

		BitMatrix bitMatrix = qrWriter.encode(
				qrText, BarcodeFormat.QR_CODE, width, height, hintMap
		);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(
				bitMatrix, imageFormat, pngOutputStream
		);
		return pngOutputStream.toByteArray();
	}
}
