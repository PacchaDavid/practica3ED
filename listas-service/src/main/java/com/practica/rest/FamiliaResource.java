package com.practica.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.practica.controller.dao.services.FamiliaServices;
import com.practica.rest.response.ResponseFactory;


@Path("/familia")
public class FamiliaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllFamilias() {
        FamiliaServices fs = new FamiliaServices();
        return ResponseFactory.buildResponse(fs,"getAllObjects");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sort/{attribute}/{orden}/{typeSort}")
    public Response sort(@PathParam("attribute") String attribute, @PathParam("orden") Integer orden, @PathParam("typeSort") Integer typeSort) {
        FamiliaServices fs = new FamiliaServices();
        return ResponseFactory.buildResponse(fs,"sort", attribute, orden, typeSort);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/{attribute}/{x}")
    public Response search(@PathParam("attribute") String attribute, @PathParam("x") String x) {
        FamiliaServices fs = new FamiliaServices();
        return ResponseFactory.buildResponse(fs,"search", attribute, x);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/attributes")
    public Response attributes() {
        FamiliaServices fs = new FamiliaServices();
        return ResponseFactory.buildResponse(fs,"attributes");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getFamiliaById(@PathParam("id") Integer id) {
        FamiliaServices fs = new FamiliaServices();
        return ResponseFactory.buildResponse(fs, "getFamiliaById",id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response saveFamilia(String familiaJson) {
        FamiliaServices fs = new FamiliaServices();
        return ResponseFactory.buildResponse(fs, "save", familiaJson);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateFamilia(String familiaJson) {
        FamiliaServices fs = new FamiliaServices();
        return ResponseFactory.buildResponse(fs, "updateFamilia", familiaJson);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteFamilia(@PathParam("id") Integer id) {
        FamiliaServices fs = new FamiliaServices();
        return ResponseFactory.buildResponse(fs,"deleteFamilia",id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/enumerations")
    public Response enumerations() {
        FamiliaServices fs = new FamiliaServices();
        return ResponseFactory.buildResponse(fs.enumeraciones());
    }
    
}
