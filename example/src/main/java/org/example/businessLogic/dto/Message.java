package org.example.businessLogic.dto;

public class Message {
    private String subject;
    private String receiver;
    private String body;

    public Message(String subject, String receiver, String body) {
        this.subject = subject;
        this.receiver = receiver;
        this.body = body;
    }
}
