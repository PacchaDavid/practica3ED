package com.practica.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.practica.controller.dao.CrudOperationManager;
import com.practica.rest.response.ResponseFactory;


@Path("/historial")
public class OperationCrudResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllOperationCruds() {
        CrudOperationManager ocm = new CrudOperationManager();
        return ResponseFactory.buildResponse(ocm,"getAllObjects");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sort/{attribute}/{orden}/{typeSort}")
    public Response sort(@PathParam("attribute") String attribute, @PathParam("orden") Integer orden, @PathParam("typeSort") Integer typeSort) {
        CrudOperationManager ocm = new CrudOperationManager();
        return ResponseFactory.buildResponse(ocm,"sort", attribute, orden, typeSort);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/{attribute}/{x}")
    public Response search(@PathParam("attribute") String attribute, @PathParam("x") String x) {
        CrudOperationManager ocm = new CrudOperationManager();
        return ResponseFactory.buildResponse(ocm,"search", attribute, x);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/attributes")
    public Response attributes() {
        CrudOperationManager ocm = new CrudOperationManager();
        return ResponseFactory.buildResponse(ocm,"attributes");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/{id}")
    public Response getOperationCrudById(@PathParam("id") Integer id) {
        CrudOperationManager ocm = new CrudOperationManager();
        return ResponseFactory.buildResponse(ocm, "getOperationCrudById",id);
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public Response deleteOperationCrud(@PathParam("id") Integer id) {
        CrudOperationManager ocm = new CrudOperationManager();
        return ResponseFactory.buildResponse(ocm,"deleteOperationCrud",id);
    }
    
}
