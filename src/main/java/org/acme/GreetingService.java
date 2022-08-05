package org.acme;

import javax.enterprise.context.ApplicationScoped;

import java.util.Objects;

@ApplicationScoped
public class GreetingService {

    public String getGreeting(String name){
        if(Objects.isNull(name)){
            return "Hi Alien";
        }
        return "Hi " + name;
    }
    
}
