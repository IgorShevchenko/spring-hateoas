package com.igor.setup;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class DefaultControllerAdvice {

    private static final Map<Class<? extends Exception>, HttpStatus> STATUS_CODES = new HashMap<>();

    static {
        STATUS_CODES.put(NoSuchElementException.class, NOT_FOUND);
        STATUS_CODES.put(HttpRequestMethodNotSupportedException.class, METHOD_NOT_ALLOWED);
        STATUS_CODES.put(HttpMediaTypeNotSupportedException.class, UNSUPPORTED_MEDIA_TYPE);
        STATUS_CODES.put(HttpMediaTypeNotAcceptableException.class, NOT_ACCEPTABLE);
        STATUS_CODES.put(AccessDeniedException.class, FORBIDDEN);
        STATUS_CODES.put(IllegalArgumentException.class, BAD_REQUEST);
        STATUS_CODES.put(HttpMessageNotReadableException.class, BAD_REQUEST);
        STATUS_CODES.put(MethodArgumentNotValidException.class, BAD_REQUEST);
        STATUS_CODES.put(MissingServletRequestPartException.class, BAD_REQUEST);
        STATUS_CODES.put(TypeMismatchException.class, BAD_REQUEST);
        STATUS_CODES.put(ServletRequestBindingException.class, BAD_REQUEST);
        STATUS_CODES.put(MissingServletRequestParameterException.class, BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<DefaultErrorMessage> defaultExceptionHandler(HttpServletRequest request, Exception ex) {
        // If exception is annotated with @ResponseStatus then use annotated HTTP status
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        HttpStatus status = (responseStatus == null) ? getHttpStatus(ex) : responseStatus.value();

        DefaultErrorMessage errorMessage = new DefaultErrorMessage(request, ex);
        return new ResponseEntity<>(errorMessage, status);
    }

    private static HttpStatus getHttpStatus(Exception ex) {
        // Check if this exception is explicitly mapped, assign otherwise
        return STATUS_CODES.computeIfAbsent(ex.getClass(), DefaultControllerAdvice::getAssignableHttpStatus);
    }

    private static HttpStatus getAssignableHttpStatus(Class<? extends Exception> clazz) {
        for (Entry<Class<? extends Exception>, HttpStatus> entry : STATUS_CODES.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return entry.getValue();
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
