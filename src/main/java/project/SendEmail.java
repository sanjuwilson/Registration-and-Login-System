package project;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
	private Session session=null;
	private MimeMessage msg=null;
	public void setupServerProperties() {
	    	Properties properties=new Properties();
	    	properties.put("mail.smtp.host", "smtp.gmail.com");
	    	properties.put("mail.smtp.port", "587");
	    	properties.put("mail.smtp.auth","true");
	    	properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    	properties.put("mail.smtp.starttls.enable","true");
	    	session=Session.getDefaultInstance(properties,null);
	    }
	 public MimeMessage draftEmail(int otp,String emailid) throws AddressException, MessagingException, IOException {
	    	String recepient=emailid;
	    	String subject="OTP";
	    	msg=new MimeMessage(session);
	    	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
	    	msg.setSubject(subject);
	    	MimeMultipart mt=new MimeMultipart();
	    	MimeBodyPart htbodyprt=new MimeBodyPart();
	    	MimeBodyPart imagePart = new MimeBodyPart();
	    	htbodyprt.setContent("<p>here is the otp "+otp+"</p>","text/html");
	    	
	    	 imagePart.attachFile("C:\\Users\\sanju\\OneDrive\\Documents\\sa.webp");
	         imagePart.setDisposition(MimeBodyPart.ATTACHMENT);
	         mt.addBodyPart(htbodyprt);
	         mt.addBodyPart(imagePart);
	    	 msg.setContent(mt);
	    	 return msg;
	    }
	 public void sendEmail() throws MessagingException {
		  String from = "fxjava92@gmail.com";
		  final String frompassword = "xppf fdbo ljps nobf";
		  String host  = "smtp.gmail.com";
	      Transport trans=session.getTransport("smtp");
	      trans.connect(host,from,frompassword);
	      trans.sendMessage(msg, msg.getAllRecipients());
	      trans.close();
	    }

}
