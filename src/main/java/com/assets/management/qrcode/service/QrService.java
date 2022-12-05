package com.assets.management.qrcode.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

import org.jboss.logging.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@ApplicationScoped
public class QrService {

	@Inject
	Logger LOG;

	public byte[] generateQrString(@Valid QrContent content)
			throws WriterException, IOException {
		LOG.info("Content for creating QR: " + content);
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hintMap.put(EncodeHintType.MARGIN, 1);
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		
		BitMatrix bitMatrix = new QRCodeWriter().encode(
				String.valueOf(content), BarcodeFormat.QR_CODE, 150, 150,
				hintMap
		);

		MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
//		String qrString = Base64.getEncoder().encodeToString(
//				pngOutputStream.toByteArray()
//		);
		
		// TODO: return byte[] then convert to string in electronic-asset client service
		LOG.info("Returnd QR String: " + content);
		return pngOutputStream.toByteArray();
	}
}
