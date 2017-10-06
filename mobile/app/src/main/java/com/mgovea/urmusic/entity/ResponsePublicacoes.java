package com.mgovea.urmusic.entity;

import java.util.ArrayList;

public class ResponsePublicacoes {
	private String message;
    private ArrayList<Publicacao> object;

    public ResponsePublicacoes() {

    }

    public ResponsePublicacoes(String message) {
        this.message = message;
        //this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Publicacao> getPublicacoes() {
        return object;
    }
}
