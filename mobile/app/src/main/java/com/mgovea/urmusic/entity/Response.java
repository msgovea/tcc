package com.mgovea.urmusic.entity;


public class Response<T> {
	private String message;
    private T object;

    public Response() {

    }

    public Response(String message, T object) {
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

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
