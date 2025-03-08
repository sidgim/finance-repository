package com.glara.application.service;

import com.glara.dto.CategoryDTO;
import com.glara.infrastructure.repository.CategoryRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CategoryService {
    private static final Logger LOGGER = Logger.getLogger(CategoryService.class);

    @Inject
    CategoryRepository repository;

    // Obtener todas las categorías con mejor manejo de errores
    public Uni<List<CategoryDTO>> getAllCategories(Integer size, Integer page) {
        return repository.findAll( size,  page)
                .onItem().ifNull().failWith(() -> new NotFoundException("Account not found"))
                .onFailure().invoke(error -> LOGGER.error("Failed to getAccount: " + error.getMessage(), error));
    }

    // Obtener una categoría por ID con mejor manejo de errores
    public Uni<CategoryDTO> getCategoryById(UUID id) {
        return repository.findById(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("category not found"))
                .onFailure().invoke(error -> LOGGER.error("Failed to getAccount: " + error.getMessage(), error));
    }

    public Uni<CategoryDTO> createCategory(CategoryDTO categoryDTO) {
        return repository.createCategory(categoryDTO)
                .onItem().ifNull().failWith(() -> new WebApplicationException("Failed to create category", Response.Status.INTERNAL_SERVER_ERROR))
                .onFailure().invoke(error -> LOGGER.error("Failed to create category: " + error.getMessage(), error));
    }

    public  Uni<CategoryDTO> updateCategory(UUID id, CategoryDTO categoryDTO) {
        return repository.findById(id).onItem().ifNull().failWith(() -> {
                    LOGGER.warn("Category with id " + id + " not found.");
                    return new NotFoundException("Category not found");
                })
                .onItem().invoke(existingCategory -> LOGGER.info("Category found, proceeding with update"))
                .onItem().transformToUni(existingCategory -> repository.updateCategory(id, categoryDTO));
    }

    public Uni<Void> deleteCategory(UUID id) {
        return repository.deleteCategory(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("Category not found"))
                .replaceWithVoid();
    }

}

