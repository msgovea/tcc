package br.edu.puccamp.app.entity;

public class Response {
	private String message;
    private Usuario object;

    public Response() {
        
    }
    
    public Response(String message, Usuario object) {
        this.message = message;
        this.object = object;
    }
    
    public Response(String message) {
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
