package br.edu.puccamp.app.entity;

import java.util.ArrayList;

public class ResponseComentarios {
	private String message;
    private ArrayList<Comentario> object;

    public ResponseComentarios() {

    }

    public ResponseComentarios(String message) {
        this.message = message;
        //this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Comentario> getPublicacoes() {
        return object;
    }
}
