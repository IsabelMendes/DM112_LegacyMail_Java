package br.inatel.dm112.adapter;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailAdapter {

	public void sendMail(final String from, final String password, String to, byte[] content) {

		System.out.println("Enviando email para: " + to);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Pedido entregue!");

			String strContent;
			try {
				strContent = new String(content, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				strContent = "Pedido entregue";
			}
			Multipart multipart = new MimeMultipart();
			BodyPart messageBodyPartText = new MimeBodyPart(); // texto
			messageBodyPartText.setText(strContent);
			multipart.addBodyPart(messageBodyPartText);

			message.setContent(multipart);

			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
