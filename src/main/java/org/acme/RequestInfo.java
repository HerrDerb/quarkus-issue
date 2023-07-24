package org.acme;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class RequestInfo {
    
    private String info;

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}