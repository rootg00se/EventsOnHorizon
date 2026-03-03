package com.deg00se.events.services;

public interface MailService {
    void sendConfirmationMail(String toEmail, String confirmLink);
}
