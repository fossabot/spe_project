package web.globalbeershop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import web.globalbeershop.data.ActivationToken;
import web.globalbeershop.data.ResetToken;
import web.globalbeershop.data.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Email;

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(String email) throws MailException {
        //send email
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom("globalbeershopmail@gmail.com");
        mail.setSubject("WARNING");
//        mail.setText("Dear Mr Raf, \n" +
//                "Error: Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.\n\n\n\n" +
//                "JK it worked\n\n" +
//                "The email you are reading at this present moment in time is not just a test but a celebration of the great work achieved over the last 3 months. " +
//                "Although you may simply see words on a screen, please believe me when I say this message hides unbounded levels of complexity and programming skill." +
//                "In fact the greatest minds of our generation have sometimes described this email service as a symphony of placid beauty.\n \n" +
//                "It is my greatest hope that this item of mail does reach you, however one can argue the true genius of this sacred work is that you won't know if it doesn't work.\n\n" +
//                "Your's truly,\n" +
//                "Charles Lewis Figuero, Architect and Friend.");
        mail.setText("Order Complete.");


        javaMailSender.send(mail);
    }

    public void sendActivationEmail(ActivationToken token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        User user = token.getUser();

        String activationUrl;

        String htmlMsg = "<br><h4>Hi "+ user.getFirstName() +",</h4>" +
                        "<br><a href=\"http://localhost:8080/register/activate?token="+token.getToken()+"\">Click Here to activate your account</a>" +
                        "<br><h4>Global Beer Shop</h4>";
        mimeMessage.setContent(htmlMsg, "text/html");
        helper.setTo(token.getUser().getEmail());
        helper.setSubject("Activate your Global Beer Shop account");
        helper.setFrom("globalbeershopmail@gmail.com");
        javaMailSender.send(mimeMessage);
    }

    public void sendResetEmail (ResetToken token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        User user = token.getUser();

        String activationUrl;

        String htmlMsg = "<br><h4>Hi "+ user.getFirstName() +",</h4>" +
                "<br><a href=\"http://localhost:8080/reset/set?token="+token.getToken()+"\">Click Here to reset your account password</a>" +
                "<br><h4>Global Beer Shop</h4>";
        mimeMessage.setContent(htmlMsg, "text/html");
        helper.setTo(token.getUser().getEmail());
        helper.setSubject("Reset your Global Beer Shop password");
        helper.setFrom("globalbeershopmail@gmail.com");
        javaMailSender.send(mimeMessage);
    }

}