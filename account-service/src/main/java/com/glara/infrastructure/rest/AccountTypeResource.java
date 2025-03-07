package com.glara.infrastructure.rest;

import com.glara.application.service.AccountTypeService;
import com.glara.dto.AccountTypeDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/account-type")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountTypeResource {

    @Inject
    AccountTypeService accountTypeService;

    @GET
    @Path("/{id}")
    public Uni<AccountTypeDTO> getAccountTypeById(@PathParam("id") UUID id) {
        return accountTypeService.getAccountType(id);
    }

    @GET
    public Uni<List<AccountTypeDTO>> getAllAccountTypes() {
        return accountTypeService.findAllAccountTypes();
    }

    @POST
    public Uni<Response> createAccountType(AccountTypeDTO accountTypeDTO) {
        return accountTypeService.createAccountType(accountTypeDTO)
                .map(accountType -> Response.created(URI.create("/account-type/" + accountType.id()))
                        .entity(accountType)
                        .build());
    }

    @PUT
    @Path("/{id}")
    public Uni<AccountTypeDTO> updateAccountType(@PathParam("id") UUID id, AccountTypeDTO accountTypeDTO) {
        return accountTypeService.updateAccountType(accountTypeDTO, id);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> deleteAccountTypeById(@PathParam("id") UUID id) {
        return accountTypeService.deleteAccountTypeById(id);
    }
}