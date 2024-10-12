package org.urlshortener.Excemptions;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class NullObjectException extends RuntimeException{

    public NullObjectException(String obj, String param){
        super("Object" + " " + obj + " doesn't found by " + param);
    }
}
