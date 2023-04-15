package com.masters.coding.common.exception;

import jakarta.persistence.EntityNotFoundException;

public class NotFoundException extends EntityNotFoundException {

    private String type;
    private int id;

    public NotFoundException(String type, int id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return type + " with id=" + id + " not found";
    }
}
