package org.acme;

import javax.enterprise.context.ApplicationScoped;

import java.util.Objects;

@ApplicationScoped
public class ByeService {

    public String getBye(String name){
        if(Objects.isNull(name)){
            return "Bye Alien";
        }
        return "Bye "+ name;
    }
    
}
