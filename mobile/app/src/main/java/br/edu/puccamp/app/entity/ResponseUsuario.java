package br.edu.puccamp.app.entity;

public class ResponseUsuario {
	private String message;
    private Usuario object;

    public ResponseUsuario() {
        
    }
    
    public ResponseUsuario(String message, Usuario object) {
        this.message = message;
        this.object = object;
    }
    
    public ResponseUsuario(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Usuario getObject() {
        return object;
    }

    public void setObject(Usuario object) {
        this.object = object;
    }
}
