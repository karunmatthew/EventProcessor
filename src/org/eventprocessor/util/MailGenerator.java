package org.eventprocessor.util;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailGenerator {
	public static void sendEmail(String toAddress) throws MessagingException {
		
		Properties properties = System.getProperties();
		properties.setProperty(MailConstants.MAIL_HOST_KEY,
				MailConstants.MAIL_HOST);
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(MailConstants.FROM_ADDRESS));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				toAddress));
		message.setSubject(MailConstants.SUBJECT);
		message.setContent("<h1>IT WORKS MAN !!</h1>", "text/html");
		Transport.send(message);

	}
	public static void main(String[] args) throws MessagingException {
		MailGenerator.sendEmail("karunmatthew@live.in");
	}
}
