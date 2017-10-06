package com.mgovea.urmusic.entity;

import com.mgovea.urmusic.gosto_musical.Gosto;

import java.util.ArrayList;

public class ResponseGosto {
	private String message;
    private ArrayList<Gosto> object;

    public ResponseGosto() {

    }

    public ResponseGosto(String message) {
        this.message = message;
        //this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Gosto> getGostos() {
        return object;
    }
}
