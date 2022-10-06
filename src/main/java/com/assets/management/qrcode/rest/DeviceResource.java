package com.assets.management.qrcode.rest;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;
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
import com.assets.management.qrcode.service.DeviceService;

@Path("/devices")
public class DeviceResource {

	@Inject
	DeviceService deviceService;

	@POST
	@Path("/qr/phone")
	@Produces("image/png")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSmartPhone(@Valid SmartPhone phone,
			@Context UriInfo uriInfo) {
		byte[] qrStream; // = null;
		try {
			qrStream = deviceService.persistPhone(phone);
		} catch (Exception rollbackException) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

//		if (qrStream == null) {
//			return Response.status(Response.Status.NOT_FOUND).build();
//
//		}
		URI createdUri = uriInfo.getAbsolutePathBuilder().path(
				Long.toString(phone.id)
		).build();
		return Response.created(createdUri).entity(qrStream).build();

	}

	@POST
	@Path("/qr/computer")
	@Produces("image/png")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComputer(@Valid Computer computer,
			@Context UriInfo uriInfo) {
		byte[] qrStream; // = null;
		try {
			qrStream = deviceService.persistComputer(computer);
		} catch (Exception rollbackException) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		URI createdUri = uriInfo.getAbsolutePathBuilder().path(
				Long.toString(computer.id)
		).build();
		return Response.created(createdUri).entity(qrStream).build();

	}
}
