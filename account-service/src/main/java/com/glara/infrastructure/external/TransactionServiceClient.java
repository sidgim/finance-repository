package com.glara.infrastructure.external;


import com.glara.dto.TransactionDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@Path("/transaction")
@RegisterRestClient(configKey = "transaction-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TransactionServiceClient {
    @GET
    @Path("/account/{id}")
    @Retry(maxRetries = 3)
    @Timeout(3000)
    @Fallback(fallbackMethod = "fallbackGetTransactionsByAccount")
    Uni<List<TransactionDTO>> getTransactionsByAccount(UUID id);

    private Uni<List<TransactionDTO>> fallbackGetTransactionsByAccount(UUID id) {
        return Uni.createFrom().item(List.of());
    }

}

