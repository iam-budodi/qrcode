package com.assets.management.qrcode.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.assets.management.qrcode.model.Computer;
import com.assets.management.qrcode.model.SmartPhone;
import com.assets.management.qrcode.service.PhoneService;
import com.google.zxing.WriterException;

@Path("/phones")
public class PhoneResource {
	
	@Inject
	PhoneService phoneService;

	@POST
	@Produces("image/png")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generateQR(/*@Valid*/ SmartPhone phone, @Context UriInfo uriInfo) throws WriterException, IOException {
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
		
		byte[] qrStream = phoneService.persistPhone(phone);
		return Response.ok(new ByteArrayInputStream(qrStream)).build();

	}
}
