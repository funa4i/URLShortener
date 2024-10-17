package org.urlshortener.Excemptions;

public class AccessRightsException  extends RuntimeException{
    public AccessRightsException(String role){
        super("The " + role + "role wasn't enough to perform the action");
    }
}
