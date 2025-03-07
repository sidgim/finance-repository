package com.glara.infrastructure.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        // Evitar capturar WebApplicationException, ya que otro mapper lo maneja
        if (exception instanceof jakarta.ws.rs.WebApplicationException) {
            return ((jakarta.ws.rs.WebApplicationException) exception).getResponse();
        }

        LOGGER.error("🚨 Error inesperado en la aplicación:", exception);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("An unexpected error occurred", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static class ErrorResponse {
        public String message;
        public int status;

        public ErrorResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }
}
