package com.igor.setup;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchMemberException extends NoSuchElementException {

    private static final long serialVersionUID = 7996516744853733268L;
    private static final String MESSAGE_FORMAT = "Member with id '%d' does not exist.";

    public NoSuchMemberException(long id) {
        super(String.format(MESSAGE_FORMAT, id));
    }

}
