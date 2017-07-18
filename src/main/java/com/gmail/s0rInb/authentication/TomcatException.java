package com.gmail.s0rInb.authentication;

import org.springframework.dao.NonTransientDataAccessException;

public class TomcatException extends NonTransientDataAccessException {
    public TomcatException(String message) {
        super(message);
    }
}