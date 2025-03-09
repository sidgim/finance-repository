package com.glara.infrastructure.rest;


import com.glara.application.service.SubcategoryService;
import com.glara.dto.SubcategoryDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/subcategory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubcategoryResource {
    private final SubcategoryService subcategoryService;

    @Inject
    public SubcategoryResource(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GET
    public Uni<List<SubcategoryDTO>> getAllSubcategories(@QueryParam("size") Integer size,
                                                         @QueryParam("page") Integer page) {
        return subcategoryService.getAllSubcategories(size, page);
    }

    @GET
    @Path("/{id}")
    public Uni<SubcategoryDTO> getSubcategoryById(@PathParam("id") UUID id) {
        return subcategoryService.getSubcategoryById(id);
    }

    @POST
    public Uni<SubcategoryDTO> createSubcategory(SubcategoryDTO subcategoryDTO) {
        return subcategoryService.createSubcategory(subcategoryDTO);
    }

    @PUT
    @Path("/{id}")
    public Uni<SubcategoryDTO> updateSubcategory(@PathParam("id") UUID id, SubcategoryDTO subcategoryDTO) {
        return subcategoryService.updateSubcategory(id, subcategoryDTO);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> deleteSubcategory(@PathParam("id") UUID id) {
        return subcategoryService.deleteSubcategory(id);
    }
}
