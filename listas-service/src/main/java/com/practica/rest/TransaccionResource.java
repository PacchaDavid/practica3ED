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

import com.practica.controller.dao.services.TransaccionServices;
import com.practica.rest.response.ResponseFactory;


@Path("/transaccion")
public class TransaccionResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllTransaccions() {
        TransaccionServices ts = new TransaccionServices();
        return ResponseFactory.buildResponse(ts,"getAllObjects");
    }

     @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sort/{attribute}/{orden}/{typeSort}")
    public Response sort(@PathParam("attribute") String attribute, @PathParam("orden") Integer orden, @PathParam("typeSort") Integer typeSort) {
        TransaccionServices ts = new TransaccionServices();
        return ResponseFactory.buildResponse(ts,"sort", attribute, orden, typeSort);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/{attribute}/{x}")
    public Response search(@PathParam("attribute") String attribute, @PathParam("x") String x) {
        TransaccionServices ts = new TransaccionServices();
        return ResponseFactory.buildResponse(ts,"search", attribute, x);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getTransaccionById(@PathParam("id") Integer id) {
        TransaccionServices ts = new TransaccionServices();
        return ResponseFactory.buildResponse(ts, "getTransaccionById",id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response saveTransaccion(String familiaJson) {
        TransaccionServices ts = new TransaccionServices();
        return ResponseFactory.buildResponse(ts, "save", familiaJson);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateTransaccion(String familiaJson) {
        TransaccionServices ts = new TransaccionServices();
        return ResponseFactory.buildResponse(ts, "updateTransaccion", familiaJson);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteTransaccion(@PathParam("id") Integer id) {
        TransaccionServices ts = new TransaccionServices();
        return ResponseFactory.buildResponse(ts,"deleteTransaccion",id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/attributes")
    public Response attributes() {
        TransaccionServices ts = new TransaccionServices();
        return ResponseFactory.buildResponse(ts,"attributes");
    }

    @GET
    @Produces
    @Path("/enumerations")
    public Response deleteTransaccion() {
        TransaccionServices ts = new TransaccionServices();
        return ResponseFactory.buildResponse(ts,"enumerations");
    }
}
