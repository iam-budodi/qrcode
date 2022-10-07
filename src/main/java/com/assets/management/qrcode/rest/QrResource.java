package com.assets.management.qrcode.rest;

import java.net.URI;
import java.util.Base64;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import com.assets.management.qrcode.service.QrContent;
import com.assets.management.qrcode.service.QrService;

@Path("/devices/qr")
public class QrResource {

	@Inject
	QrService service;
	
	@Inject
	Logger LOG;

	@POST 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces("image/png") 
	public Response CreateQrString(@Valid QrContent content, @Context UriInfo uriInfo) {
 
		LOG.info("Resource for creating QR: " + content); 
		
		byte[] qrStream; // = null;
		try {
			qrStream = service.generateQrString(content);
		} catch (Exception rollbackException) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} 
		
		URI createdUri = uriInfo.getAbsolutePathBuilder().path(
				Long.toString(content.id)
		).build();
		
		LOG.info("Returnd Resource String: " + Base64.getEncoder().encodeToString(qrStream));
		 
		return Response.created(createdUri).entity(qrStream).build(); 

	}
	
	@GET
	@Produces("image/png") 
	public Response getQrCode() {
		// TODO: figure out how to get QR String
		// byte [] qrStream = Base64.getDecoder().decode(qrStream); 
		
		return Response.noContent().build();
	}

}
