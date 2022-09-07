package org.example.warehouse.infrastructure;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    public void sendEmail(Email email) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", false);
        prop.put("mail.smtp.host", "localhost");
        prop.put("mail.smtp.port", "25");

        Session session = Session.getInstance(prop);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email.from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.to));
        message.setSubject(email.subject);
        message.setText(email.message);
        Transport.send(message);
    }

    public static class Email {
        private final String to;
        private final String from;
        private final String subject;
        private final String message;

        private Email(EmailBuilder builder) {
            this.to = builder.to;
            this.from = builder.from;
            this.subject = builder.subject;
            this.message = builder.message;
        }

        public static class EmailBuilder {
            private String to;
            private String from;
            private String subject;
            private String message;

            public EmailBuilder to(String to) {
                this.to = to;
                return this;
            }

            public EmailBuilder from(String from) {
                this.from = from;
                return this;
            }

            public EmailBuilder subject(String subject) {
                this.subject = subject;
                return this;
            }

            public EmailBuilder message(String message) {
                this.message = message;
                return this;
            }

            public Email build() {
                return new Email(this);
            }
        }
    }
}
