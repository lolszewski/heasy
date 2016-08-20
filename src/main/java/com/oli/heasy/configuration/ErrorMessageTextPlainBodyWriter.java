package com.oli.heasy.configuration;

import com.google.common.base.Charsets;
import io.dropwizard.jersey.errors.ErrorMessage;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
@Provider
@Produces(MediaType.TEXT_PLAIN)
public class ErrorMessageTextPlainBodyWriter implements MessageBodyWriter<ErrorMessage> {

    @Override
    public boolean isWriteable(
            Class<?> type,
            Type genericType,
            Annotation[] annotations,
            MediaType mediaType) {
        return ErrorMessage.class.isAssignableFrom(type) && MediaType.TEXT_PLAIN_TYPE.equals(mediaType);
    }

    @Override
    public long getSize(
            ErrorMessage t,
            Class<?> type,
            Type genericType,
            Annotation[] annotations,
            MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(
            ErrorMessage t,
            Class<?> type,
            Type genericType,
            Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders,
            OutputStream entityStream) throws IOException, WebApplicationException {
        String message = t.getMessage();
        entityStream.write(message.getBytes(Charsets.UTF_8));
    }
}
