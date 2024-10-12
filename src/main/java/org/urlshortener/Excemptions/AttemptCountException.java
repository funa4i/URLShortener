package org.urlshortener.Excemptions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AttemptCountException extends RuntimeException{


    public AttemptCountException(LocalDateTime date){
        super("The link creation limit has been reached. "
                + "Next create after: "
                + " at: " + date.format(DateTimeFormatter.ofPattern("HH.mm dd-MM-yyyy"))
                + " " + ZoneId.systemDefault());
    }
}
