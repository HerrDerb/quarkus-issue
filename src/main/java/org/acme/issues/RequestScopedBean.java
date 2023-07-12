package org.acme.issues;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@Slf4j
public class RequestScopedBean {

    private String info;

    public void setInfo(String info){
        this.info = info;
    }

    public String getInfo(){
        return info;
    }    
    
    @PostConstruct
    void create(){
        log.warn("## New RequestScopedBean created");
    }

    @PreDestroy
    void destroy(){
        log.warn("## RequestScopedBean destroyed");
    }
}
