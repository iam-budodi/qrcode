package com.assets.management.qrcode.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.assets.management.qrcode.model.Computer;
import com.assets.management.qrcode.service.ComputerService;
import com.assets.management.qrcode.service.PhoneService;
import com.google.zxing.WriterException;

@Path("/codes")
public class QRCodeResource {

	@Inject
	ComputerService qrService;
	
	@Inject
	PhoneService phoneService;

	@GET
	@Produces("image/png")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generateQR() throws WriterException, IOException {
//		String qrText = "Japhet Should Pass items Object";
//		int size = 125;

		// TODO: pass this body on the method Parameter and change it to POST
		// persist to the database then retrieve only few details for creating 
		// QR, use select statement to filter few columns of table Computer/Phone
		Computer pc = new Computer();
		pc.brand = "APPLE"; 
		pc.comment = "trial";
		pc.manufacturedDate = Instant.now();
		pc.manufacturer = "APPLE Inc";
		pc.commissionedDate = LocalDate.now();
		pc.serialNumber = "XYZ123";
		pc.mouse = true;
		
//		byte[] qrStream = qrService.getByteQRCode(String.valueOf(pc), QRSize.Large);
		byte[] qrStream = qrService.getByteQRCode(String.valueOf(pc));
		return Response.ok(new ByteArrayInputStream(qrStream)).build();

	}

	// very lower level implementaion detals
//	@GET
//	@Produces("image/png")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response generateQRCode() {
//		String qrText = "Japhet Sebastian";
//		int size = 125;
//		String filePath = "japhet.png";
//		String fileType = "png";
//
//		File qrFile = new File(filePath);
//		// create a ByteMatrix for the QR Code that encodes a given string
//		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
//		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//		QRCodeWriter qrWriter = new QRCodeWriter();
//		BitMatrix byteMatrix;
//		BufferedImage image;
//		try {
//			byteMatrix = qrWriter.encode(
//					qrText, BarcodeFormat.QR_CODE, size, size, hintMap
//			);
//
//			// Make BufferedImage that are to hold the QRCode
//			int matrixWidth = byteMatrix.getWidth();
//			image = new BufferedImage(
//					matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB
//			);
//			image.createGraphics();
//
//			Graphics2D graphics = (Graphics2D) image.getGraphics();
//			graphics.setColor(Color.WHITE);
//			graphics.fillRect(0, 0, matrixWidth, matrixWidth);
//
//			// Paint and save the image using the ByteMatrix
//			graphics.setColor(Color.BLACK);
//
//			for (int i = 0; i < matrixWidth; i++) {
//				for (int j = 0; j < matrixWidth; j++) {
//					if (byteMatrix.get(i, j)) {
//						graphics.fillRect(i, j, 1, 1);
//					}
//				}
//			}
//			
//			ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//			ImageIO.write(image, fileType, pngOutputStream);
//			byte[] qrStream = pngOutputStream.toByteArray();
//
//			return Response.ok(new ByteArrayInputStream(qrStream)).build();
//
//		} catch (WriterException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//
//	}
}
