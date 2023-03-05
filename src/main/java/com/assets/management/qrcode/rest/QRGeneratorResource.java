package com.assets.management.qrcode.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Hashtable;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Path("/generates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QRGeneratorResource {

	@Inject
	Logger LOG;

	@POST
	@Path("/qrcode")
	public Response generateQrString(@Valid URI collectionOrTransferURI) throws WriterException, IOException {
		LOG.info("Content for creating QR: " + collectionOrTransferURI);
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hintMap.put(EncodeHintType.MARGIN, 1);
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		BitMatrix bitMatrix = new QRCodeWriter().encode(String.valueOf(collectionOrTransferURI), BarcodeFormat.QR_CODE,
				150, 150, hintMap);

		MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
//		String qrString = Base64.getEncoder().encodeToString(
//				pngOutputStream.toByteArray()
//		);

		// TODO: return byte[] then convert to string in electronic-asset client service
//		LOG.info("Returnd QR String: " + collectionOrTransfer);
		return Response.ok(pngOutputStream.toByteArray()).build();
	}
}
