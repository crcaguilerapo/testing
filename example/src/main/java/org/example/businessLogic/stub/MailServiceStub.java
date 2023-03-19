package org.example.businessLogic.stub;

import org.example.businessLogic.IMailService;
import org.example.businessLogic.dto.Message;

import java.util.ArrayList;
import java.util.List;

public class MailServiceStub implements IMailService {
    private List<Message> messages = new ArrayList<Message>();
    public void send (Message msg) {
        messages.add(msg);
    }
    public int numberSent() {
        return messages.size();
    }
}