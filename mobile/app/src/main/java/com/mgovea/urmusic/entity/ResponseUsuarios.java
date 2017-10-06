package com.mgovea.urmusic.entity;

import java.util.ArrayList;

public class ResponseUsuarios {
	private String message;
    private ArrayList<Usuario> object;

    public ResponseUsuarios() {

    }

    public ResponseUsuarios(String message, ArrayList<Usuario> object) {
        this.message = message;
        this.object = object;
    }

    public ResponseUsuarios(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Usuario> getObject() {
        return object;
    }

    public void setObject(ArrayList<Usuario> object) {
        this.object = object;
    }
}
