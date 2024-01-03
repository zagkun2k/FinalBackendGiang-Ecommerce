package com.truongiang.ecommerceweb.common.configuration.mail;

import com.truongiang.ecommerceweb.dto.MailInfo;

import javax.mail.MessagingException;
import java.io.IOException;

public interface SendMailService {

	void run();

	void queue(String to, String subject, String body);

	void queue(MailInfo mail);

	void send(MailInfo mail) throws MessagingException, IOException;

}
