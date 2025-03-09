package com.glara.infrastructure.rest;


import com.glara.application.service.TransactionService;
import com.glara.dto.TransactionDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    TransactionService transactionService;

    @GET
    @Path("/{id}")
    public Uni<TransactionDTO> findByIdTransaction(UUID id) {
        return transactionService.findByIdTransaction(id);
    }

    @GET
    public Uni<List<TransactionDTO>> findAllTransaction(@QueryParam("size") Integer size, @QueryParam("page") Integer page) {
        return transactionService.findAllTransaction(size, page);
    }

    @POST
    public Uni<TransactionDTO> createTransaction(TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }

    @PUT
    @Path("/{id}")
    public Uni<TransactionDTO> updateTransaction(UUID id, TransactionDTO transactionDTO) {
        return transactionService.updateTransaction(id, transactionDTO);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> deleteTransaction(UUID id) {
        return transactionService.deleteTransaction(id);
    }

}
