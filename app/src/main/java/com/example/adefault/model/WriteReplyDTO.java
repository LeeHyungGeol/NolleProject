package com.example.adefault.model;

public class WriteReplyDTO {

    String context;

    public WriteReplyDTO() {
    }

    public WriteReplyDTO(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
