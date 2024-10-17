package org.urlshortener.Excemptions;


public class NullObjectException extends RuntimeException{

    public NullObjectException(String obj, String param){
        super("Object" + " " + obj + " doesn't found by " + param);
    }
}
