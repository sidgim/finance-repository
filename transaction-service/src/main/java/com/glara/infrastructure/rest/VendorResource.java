package com.glara.infrastructure.rest;

import com.glara.application.service.VendorService;
import com.glara.dto.VendorDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/vendor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VendorResource {

    @Inject
    VendorService vendorService;


    @GET
    public Uni<List<VendorDTO>> getAllVendors() {
        return vendorService.findAllVendor();
    }

    @POST
    public Uni<VendorDTO> createVendor(VendorDTO vendorDTO) {
        return vendorService.createVendor(vendorDTO);
    }

    @PUT
    @Path("/{id}")
    public Uni<VendorDTO> updateVendor(@PathParam("id") UUID id, VendorDTO vendorDTO) {
        return vendorService.updateVendor(id, vendorDTO);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> deleteVendor(@PathParam("id") UUID id) {
        return vendorService.deleteVendor(id);
    }

}
