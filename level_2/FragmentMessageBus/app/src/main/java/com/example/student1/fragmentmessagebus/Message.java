package com.example.student1.fragmentmessagebus;

// Сообщение, которыми будут обмениваться фрагменты
// Содержит таг отправителя и текст сообщения
public class Message {
    public Message(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

	// Будет использоваться класс отправителя
    public String sender;
    public String message;
}
