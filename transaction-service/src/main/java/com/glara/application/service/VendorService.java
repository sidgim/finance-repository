package com.glara.application.service;

import com.glara.dto.VendorDTO;
import com.glara.infrastructure.repository.VendorRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class VendorService {

    @Inject
    VendorRepository vendorRepository;

    public Uni<List<VendorDTO>> findAllVendor() {
        return vendorRepository.findAllVendor()
                .onItem().ifNull().failWith(new Exception("Vendors not found"));
    }

    public Uni<VendorDTO> createVendor(VendorDTO vendorDTO) {
        return vendorRepository.createVendor(vendorDTO)
        .onItem().ifNull().failWith(new Exception("Vendor not created"));
    }

    public Uni<VendorDTO> updateVendor(UUID id, VendorDTO vendorDTO) {
        return vendorRepository.updateVendor(id, vendorDTO)
                .onItem().ifNull().failWith(new Exception("Vendor not updated"));
    }

    public Uni<Void> deleteVendor(UUID id) {
        return vendorRepository.deleteVendor(id)
                .onItem().ifNull().failWith(new Exception("Vendor not deleted"))
                .onItem().ignore().andContinueWithNull();
    }

}
