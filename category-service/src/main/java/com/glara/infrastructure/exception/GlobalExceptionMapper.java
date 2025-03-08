package com.glara.infrastructure.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
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
        int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(); // 500 por defecto
        String message = "Ocurrió un error inesperado";

        if (exception instanceof ConstraintViolationException) {
            status = Response.Status.BAD_REQUEST.getStatusCode();
            message = "Error de validación: " + exception.getMessage();
        }

        if (exception instanceof WebApplicationException webEx) {
            status = webEx.getResponse().getStatus();
            message = webEx.getMessage(); // 🔥 Esto asegurará que el mensaje personalizado se use
        }

        LOGGER.error("🚨 Error en la aplicación: " + message, exception);

        return Response.status(status)
                .entity(new ErrorResponse(message, status))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public record ErrorResponse(String message, int status) {}
}

