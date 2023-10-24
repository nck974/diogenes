package dev.nichoko.diogenes.model;

import java.io.Serializable;

public class JsonWebToken implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwt;

    public JsonWebToken(String jwt) {
        this.jwt = jwt;
    }

    public String getToken() {
        return this.jwt;
    }
}