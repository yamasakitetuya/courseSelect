package com.java1234.util;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.servlet.http.*;
import javax.servlet.*;
import com.sun.mail.util.MailSSLSocketFactory;
import java.security.GeneralSecurityException;

public class MailUtil {
	String result;
	private String to;// receiver's email
	String uid;
	String content;
	String registerRole;
	String from = "692108666@qq.com";
	String host = "localhost";
	
	public MailUtil() {
		
	};
	
	public MailUtil(String uid,String userName,String registerRole){
		this.registerRole=registerRole;
		this.uid=uid;
		this.setTo(userName);

	}
	
	public void send() throws MessagingException,GeneralSecurityException {
		
		Properties properties = new Properties();
		properties.setProperty("mail.host","smtp.qq.com");
		properties.setProperty("mail.transport.protocol","smtp");
		properties.setProperty("mail.smtp.auth","true");
	    MailSSLSocketFactory sf = new MailSSLSocketFactory();
	    sf.setTrustAllHosts(true);
	    properties.put("mail.smtp.ssl.enable", "true");
	    properties.put("mail.smtp.ssl.socketFactory", sf);
	    Session session = Session.getDefaultInstance(properties, new Authenticator() {
	    	
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("692108666@qq.com","wtuliwiekxyhbcba");
            }
        });
	    
	    session.setDebug(true);
	    Transport transport = session.getTransport();
	    transport.connect("smtp.qq.com","692108666@qq.com","wtuliwiekxyhbcba");
	    MimeMessage mimeMessage = new MimeMessage(session);
	    mimeMessage.setFrom(new InternetAddress(from));
	    mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(getTo()));
	    mimeMessage.setSubject("this is a certificate page");
	    StringBuffer sb=new StringBuffer("http://localhost:8080/Xsxk/user?action=register");
	    sb.append("&registerRole="+registerRole+"&uid="+uid+"&userName="+getTo());
	    mimeMessage.setContent("<a href="+sb.toString()+ ">"+sb.toString()+"</a>","text/html;charset=UTF-8");
	    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
	    transport.close();


			
		
	}
	
public void send(String content) throws MessagingException,GeneralSecurityException {
		
		Properties properties = new Properties();
		properties.setProperty("mail.host","smtp.qq.com");
		properties.setProperty("mail.transport.protocol","smtp");
		properties.setProperty("mail.smtp.auth","true");
	    MailSSLSocketFactory sf = new MailSSLSocketFactory();
	    sf.setTrustAllHosts(true);
	    properties.put("mail.smtp.ssl.enable", "true");
	    properties.put("mail.smtp.ssl.socketFactory", sf);
	    Session session = Session.getDefaultInstance(properties, new Authenticator() {
	    	
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("692108666@qq.com","wtuliwiekxyhbcba");
            }
        });
	    
	    session.setDebug(true);
	    Transport transport = session.getTransport();
	    transport.connect("smtp.qq.com","692108666@qq.com","wtuliwiekxyhbcba");
	    MimeMessage mimeMessage = new MimeMessage(session);
	    mimeMessage.setFrom(new InternetAddress(from));
	    mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(getTo()));
	    mimeMessage.setSubject("find your password");
	    StringBuffer sb=new StringBuffer("你的密码是： ");
	    sb.append(content);
	    mimeMessage.setContent(sb.toString(),"text/html;charset=UTF-8");
	    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
	    transport.close();


			
		
	}

public String getTo() {
	return to;
}

public void setTo(String to) {
	this.to = to;
}
	
	

}
