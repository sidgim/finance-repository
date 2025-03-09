package com.glara.application.service;

import com.glara.dto.SubcategoryDTO;
import com.glara.infrastructure.repository.SubcategoryRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SubcategoryService {
    Logger LOGGER = Logger.getLogger(SubcategoryService.class);
    @Inject
    SubcategoryRepository subcategoryRepository;

    public Uni<List<SubcategoryDTO>> getAllSubcategories(Integer size, Integer page) {
        return subcategoryRepository.findAllSubcategory(size, page)
                .onItem().ifNull().failWith(() -> new NotFoundException("Subcategory not found"))
                .onFailure().invoke(error -> LOGGER.error("Failed to getSubcategory: " + error.getMessage(), error));
    }

    public Uni<SubcategoryDTO> getSubcategoryById(UUID id) {
        return subcategoryRepository.findByIdSubcategory(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("Subcategory not found"))
                .onFailure().invoke(error -> LOGGER.error("Failed to getSubcategory: " + error.getMessage(), error));
    }

    public Uni<SubcategoryDTO> createSubcategory(SubcategoryDTO subcategoryDTO) {
        System.out.println("subcategoryDTO " + subcategoryDTO);
        return subcategoryRepository.createSubcategory(subcategoryDTO)
                .onItem().ifNull().failWith(() -> new NotFoundException("Failed to create subcategory"))
                .onFailure().invoke(error -> LOGGER.error("Failed to create subcategory: " + error.getMessage(), error));
    }

    public Uni<SubcategoryDTO> updateSubcategory(UUID id, SubcategoryDTO subcategoryDTO) {
        return subcategoryRepository.findByIdSubcategory(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("Subcategory not found"))
                .onItem().invoke(existingSubcategory -> LOGGER.info("Subcategory found, proceeding with update"))
                .onItem().transformToUni(existingSubcategory -> subcategoryRepository.updateSubcategory(id, subcategoryDTO));
    }

    public Uni<Void> deleteSubcategory(UUID id) {
        return subcategoryRepository.deleteSubcategory(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("Subcategory not found"))
                .replaceWithVoid();
    }

}
