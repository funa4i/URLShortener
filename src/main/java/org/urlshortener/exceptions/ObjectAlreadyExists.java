package org.urlshortener.exceptions;

public class ObjectAlreadyExists extends RuntimeException{

    public ObjectAlreadyExists(String obj, String place){
        super("Object " + obj + " already exists in " + place);
    }


}
