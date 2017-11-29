package net.blesso.secretsanta;

import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

/**
 * Email implementation of {@link SantaNotifier}. Currently only supports gmail accounts.
 */
public class EmailNotifier implements SantaNotifier {

    private final String username;
    private final String password;
    
    /** The email subject. */
    private final String subject;
    private final String messageBody;
    private final String smtpHost;
    
    private final Session session;
    
    /**
     * Creates an {@link EmailNotifier} from the builder. 
     * @param builder
     */
    private EmailNotifier(Builder builder) {
        this.password = builder.password;
        this.username = builder.username;
        this.subject = builder.subject;
        this.messageBody = builder.messageBody;
        this.smtpHost = builder.smtpHost;
        
       // Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", smtpHost);
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        /*
        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
        to true (the default), causes the transport to wait for the response to the QUIT command.

        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                http://forum.java.sun.com/thread.jspa?threadID=5205249
                smtpsend.java - demo program from javamail
        */
        props.put("mail.smtps.quitwait", "false");
        session = Session.getInstance(props, null);
    }

    /** 
     * Sends an email to each giver, telling them their recipient.
     */
    @Override
    public void notify(Collection<GiftMatch> matches) {
        for (GiftMatch match : matches) {
            final StringBuffer body = new StringBuffer("Dear " + match.getGiver() + "\n");
            body.append(messageBody);
            body.append("Your Secret Blesso Santa match is " + match.getReceiver());
            send(match.getGiver().getEmail(), body.toString());
        }
    }

    /**
     * Send email using GMail SMTP server.
     *
     * @param recipientEmail TO recipient
     * @param message message to be sent
     */
    private void send(String recipientEmail, String message)  {

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        try {
			msg.setFrom(new InternetAddress(username + "@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

			msg.setSubject(subject);
			msg.setText(message, "utf-8");
			msg.setSentDate(new Date());

			SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

			t.connect(smtpHost, username, password);
			msg.getMessageID();
			t.sendMessage(msg, msg.getAllRecipients());
			msg.getMessageID();
			t.close();
		} catch (MessagingException e) {
			throw new RuntimeException("could not send email to " + recipientEmail, e);
		}
    }
    
    /**
     * Fluent builder for this class.
     */
    public static final class Builder {
    	private String username;
    	private String password;
    	private String subject;
    	private String messageBody;
    	private String smtpHost = "smtp.gmail.com";
    	
    	public EmailNotifier build() {
    		return new EmailNotifier(this);
    	}
    	
    	public Builder withUsername(String value) {
    		username = value;
    		return this;
    	}
    	
    	public Builder withPassword(String value) {
    		password = value;
    		return this;
    	}
    	
    	public Builder withSubject(String value) {
    		subject = value;
    		return this;
    	}
    	
    	public Builder withMessageBody(String value) {
    		messageBody = value;
    		return this;
    	}
    	
    	public Builder withSmtpHost(String value) {
    		smtpHost = value;
    		return this;
    	}
    }
}