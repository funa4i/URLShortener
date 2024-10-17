package org.urlshortener.Enums;

public enum Roles {
    BUN(-1),
    USER(0),
    ADMIN(1),
    GOD(2);

    private final int value;

    public int getValue(){
        return value;
    }

    Roles(int val){
        this.value = val;
    }
}
