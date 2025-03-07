package com.glara.infrastructure.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    private static final Logger LOGGER = Logger.getLogger(WebApplicationExceptionMapper.class);

    @Override
    public Response toResponse(WebApplicationException exception) {
        Response originalResponse = exception.getResponse();
        LOGGER.error("🚨 Error HTTP en la aplicación:", exception);

        return Response.status(originalResponse.getStatus())
                .entity(new GlobalExceptionMapper.ErrorResponse(exception.getMessage(), originalResponse.getStatus()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
