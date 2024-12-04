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

import com.practica.controller.dao.services.GeneradorServices;
import com.practica.rest.response.ResponseFactory;

@Path("/generador")
public class GeneradorResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllGeneradors() {
        GeneradorServices gs = new GeneradorServices();
        return ResponseFactory.buildResponse(gs,"getAllObjects");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sort/{attribute}/{orden}/{typeSort}")
    public Response sort(@PathParam("attribute") String attribute, @PathParam("orden") Integer orden, @PathParam("typeSort") Integer typeSort) {
        GeneradorServices gs = new GeneradorServices();
        return ResponseFactory.buildResponse(gs,"sort", attribute, orden, typeSort);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/{attribute}/{x}")
    public Response search(@PathParam("attribute") String attribute, @PathParam("x") String x) {
        GeneradorServices gs = new GeneradorServices();
        return ResponseFactory.buildResponse(gs,"search", attribute, x);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/attributes")
    public Response attributes() {
        GeneradorServices gs = new GeneradorServices();
        return ResponseFactory.buildResponse(gs,"attributes");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getGeneradorById(@PathParam("id") Integer id) {
        GeneradorServices gs = new GeneradorServices();
        return ResponseFactory.buildResponse(gs, "getGeneradorById",id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response saveGenerador(String generadorJson) {
        GeneradorServices gs = new GeneradorServices();
        return ResponseFactory.buildResponse(gs, "save", generadorJson);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateGenerador(String generadorJson) {
        GeneradorServices gs = new GeneradorServices();
        return ResponseFactory.buildResponse(gs, "updateGenerador", generadorJson);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteGenerador(@PathParam("id") Integer id) {
        GeneradorServices gs = new GeneradorServices();
        return ResponseFactory.buildResponse(gs,"deleteGenerador",id);
    }
}
