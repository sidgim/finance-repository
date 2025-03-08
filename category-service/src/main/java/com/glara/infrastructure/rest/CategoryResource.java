package com.glara.infrastructure.rest;

import com.glara.application.service.CategoryService;
import com.glara.dto.CategoryDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    public Uni<List<CategoryDTO>> getAllCategories(@QueryParam("size") Integer size,
                                                   @QueryParam("page") Integer page) {
        return categoryService.getAllCategories(size, page);
    }

    @GET
    @Path("/{id}")
    public Uni<CategoryDTO> getCategoryById(@PathParam("id") UUID id) {
        return categoryService.getCategoryById(id);
    }

    @POST
    public Uni<CategoryDTO> createCategory(CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @PUT
    @Path("/{id}")
    public Uni<CategoryDTO> updateCategory(@PathParam("id") UUID id, CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> deleteCategory(@PathParam("id") UUID id) {
        return categoryService.deleteCategory(id);
    }

}
