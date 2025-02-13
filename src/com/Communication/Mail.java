package com.Communication;

import com.Codes.Commons;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail extends Thread {

    private String[] Recipients;
    private String Password;
    private String Sender;
    private String Subject;
    private String Body;
    private boolean FileAttached;
    private Multipart multipart = new MimeMultipart();
    private MimeBodyPart messageBodyPart = new MimeBodyPart();
    private boolean PasswordAuth;
    private Properties MailProperties = new Properties();
    private Session session;
    private Message Message = new MimeMessage(session);

    public Mail(String[] Recipients, String Sender, String Password) {
        this.PasswordAuth = true;
        this.Recipients = Recipients;
        this.Password = Password;
        this.Sender = Sender;
        MailProperties.put("mail.smtp.auth", "true");
        MailProperties.put("mail.smtp.starttls.enable", "true");
        MailProperties.put("mail.smtp.host", "smtp.gmail.com");
        MailProperties.put("mail.smtp.port", "587");
        session = Session.getDefaultInstance(MailProperties, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(Sender, Password);
            }
        }
        );
    }

    public Mail(String Recipient, String Sender, String Password) {
        this.PasswordAuth = true;
        this.Recipients = new String[]{Recipient};
        this.Password = Password;
        this.Sender = Sender;
        MailProperties.put("mail.smtp.auth", "true");
        MailProperties.put("mail.smtp.starttls.enable", "true");
        MailProperties.put("mail.smtp.host", "smtp.gmail.com");
        MailProperties.put("mail.smtp.port", "587");
        session = Session.getDefaultInstance(MailProperties, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(Sender, Password);
            }
        }
        );
    }

    public Mail(String Recipient, String Sender) {
        this.PasswordAuth = false;
        this.Recipients = new String[]{Recipient};
        this.Sender = Sender;
        MailProperties.put("mail.smtp.auth", "true");
        MailProperties.put("mail.smtp.starttls.enable", "true");
        MailProperties.put("mail.smtp.host", "smtp.gmail.com");
        MailProperties.put("mail.smtp.port", "587");
    }

    public Mail(String[] RECIPIENT, String SENDER) {
        this.PasswordAuth = false;
        this.Recipients = RECIPIENT;
        this.Sender = SENDER;
        MailProperties.put("mail.smtp.auth", "true");
        MailProperties.put("mail.smtp.starttls.enable", "true");
        MailProperties.put("mail.smtp.host", "smtp.gmail.com");
        MailProperties.put("mail.smtp.port", "587");
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public void setBody(String Body) {
        this.Body = Body;
    }

    public void setMailProperties(Properties MailProperties) {
        this.MailProperties = MailProperties;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSender(String sender) {
        this.Sender = sender;
    }

    public boolean send() throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(Sender));
        if (Recipients.length == 0) {
            return false;
        }

        for (String Recipient : Recipients) {
            msg.setRecipient(RecipientType.TO, new InternetAddress(Recipient));
            msg.setSubject(Subject);
            msg.setText(Body);
            if (FileAttached) {
                msg.setContent(multipart);
            }
            System.out.println("Rec = " + Arrays.toString(Recipients) + "\nSender = " + Sender);
            Transport.send(Message);
        }
        return true;
    }

    public void AttachNew(String file) throws IOException, MessagingException {
        FileAttached = true;
        messageBodyPart = new MimeBodyPart();
        messageBodyPart.attachFile(file);
        if (new File(file).list() == null) {
            multipart.addBodyPart(messageBodyPart);
        } else {
            for (String f : new File(file).list()) {
                messageBodyPart.attachFile(file);
                multipart.addBodyPart(messageBodyPart);
            }
        }
    }

    public void RemoveAttachments() {
        FileAttached = true;
    }

    public void setMessage(Message msg) {
        Message = msg;
    }

    public void setPassword(String password) {
        session = Session.getDefaultInstance(MailProperties, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(Sender, password);
            }
        }
        );
    }

    public static void send(String smail, String spsw, String rmail, String subj, String txt) throws AddressException, MessagingException {
        Properties prt = new Properties();
        prt.put("mail.smtp.auth", "true");
        prt.put("mail.smtp.starttls.enable", "true");
        prt.put("mail.smtp.host", "smtp.gmail.com");
        prt.put("mail.smtp.port", "587");
        System.out.println("Sending mail with SMTP:\n\tSender:" + smail + "\n\tRecipient:" + rmail + "\n\t\tSubject:" + subj + "\n\t\tBody:" + txt);
        Session ses = Session.getDefaultInstance(prt, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(smail, spsw);
            }
        }
        );
        ses.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Message msg = new MimeMessage(ses);
        msg.setFrom(new InternetAddress(smail));
        msg.setRecipient(RecipientType.TO, new InternetAddress(rmail));
        msg.setSubject(subj);
        msg.setText(txt);
        Transport.send(msg);
        System.out.println("Mail sending successful......! :)");
    }

    public static void sendWithAttachment(String smail, String spsw, String rmail, String subj, String txt, String file) throws AddressException, MessagingException, IOException {
        Properties prt = new Properties();
        prt.put("mail.smtp.auth", "true");
        prt.put("mail.smtp.starttls.enable", "true");
        prt.put("mail.smtp.host", "smtp.gmail.com");
        prt.put("mail.smtp.port", "587");
        prt.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session ses = Session.getDefaultInstance(prt, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(smail, spsw);
            }
        }
        );
        ses.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Multipart multipart = new MimeMultipart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.attachFile(file);
        if (new File(file).list() == null) {
            multipart.addBodyPart(messageBodyPart);
        } else {
            for (String f : new File(file).list()) {
                System.out.println(Arrays.toString(new File(file).list()));
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.attachFile(new File(file).getAbsolutePath() + "\\" + f);
                multipart.addBodyPart(messageBodyPart);
            }
        }
        System.out.println("Sending attachment mail with SMTP:\n\tSender:" + smail + "\n\tRecipient:" + rmail + "\n\t\tSubject:" + subj + "\n\t\tBody:" + txt);
        Message msg = new MimeMessage(ses);
        msg.setFrom(new InternetAddress(smail));
        msg.setRecipient(RecipientType.TO, new InternetAddress(rmail));
        msg.setSubject(subj);
        msg.setText(txt);
        msg.setContent(multipart);
        Transport.send(msg);
        System.out.println("Mail sending successful......! :)");
    }

    public static void main(String[] args) {
        try {
            sendWithAttachment("adithyawi3@gmail.com", "adi2004wika", "poornimaygs@gmail.com", "Biosphere.docx", "Sending mail with java", "C:\\Users\\Dell\\Desktop\\school docs\\Biospere.docx");
            Commons.showMsg("Sending mail was successful!");
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
