package br.edu.puccamp.app.entity;

import java.util.ArrayList;

import br.edu.puccamp.app.gosto_musical.Gosto;

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
