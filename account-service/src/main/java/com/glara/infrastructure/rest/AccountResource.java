package com.glara.infrastructure.rest;

import com.glara.application.service.AccountService;
import com.glara.dto.AccountDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;


@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
    @Inject
    AccountService accountService;

    @GET
    @Path("/{id}")
    public Uni<AccountDTO> getAccountById(@PathParam("id") UUID id) {
        return accountService.getAccount(id);
    }

    @GET
    public Uni<List<AccountDTO>> getAllAccount(@QueryParam("page") int page,
                                               @QueryParam("size") int size) {
        return accountService.findAllAccounts(page, size);
    }

    @POST
    public Uni<Response> createAccount(AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO)
                .map(account -> Response.created(URI.create("/api/account/" + account.id()))
                        .entity(account)
                        .build());
    }

    @PUT
    @Path("/{id}")
    public Uni<AccountDTO> updateAccount(@PathParam("id") UUID id, AccountDTO account) {
        return accountService.updateAccount(account, id);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> deleteAccountById(@PathParam("id") UUID id) {
        return accountService.deleteAccountById(id);
    }

}