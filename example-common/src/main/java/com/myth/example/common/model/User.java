package com.myth.example.common.model;

import java.io.Serializable;

/**
 * @author Ethan
 * @version 1.0
 */
public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
