package br.edu.puccamp.app.util;

/**
 * Created by Mateus on 4/2/2017.
 */

public enum Status {
    MALE("Male", 0),
    FEMALE("Female", 1);

    private String stringValue;
    private int intValue;
    Status(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}