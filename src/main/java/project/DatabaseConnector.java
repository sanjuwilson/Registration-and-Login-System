package project;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.crypto.SecretKey;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.mindrot.jbcrypt.BCrypt;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
public class DatabaseConnector {
	private static final String url="jdbc:mysql://localhost:3306/User";
	private static final String userid="root";
	private static final String pass="Password";
	private static final String PEPPER="HBF&@#!@*3928dbiwfdbfeHBVDE$#&$*Y@3213198fbd";
	private GoogleAuthenticator gauth=new GoogleAuthenticator();
	private static int tempOtp;
	private static final int otpex=5;
	private String key;
	private String user;
	private String decrotpkey;
	private LocalDateTime otpgeneratedtime;
	private int Otp;
	private String realotpkey;
	private boolean validPass=false;
	private boolean validUser=false;
	private LocalDateTime otptime;
	private int times=0;
	private SendEmail em=new SendEmail();
	SecretKey indkey;
    public void writeInto(String fname,String lname,String emailid,char[]passw,Date date) throws Exception {
		String query="insert into user_details values (?,?,?,?,?,?)";
		Connection con=DriverManager.getConnection(url,userid,pass);
		String passwo=Encryptpassword(passw);
		String otpkey=getOtpKey();
		String enckey=AESUtils.encryptOTPKey(otpkey);
		PreparedStatement st=con.prepareStatement(query);
		st.setString(1,fname);
		st.setString(2,lname);
		st.setString(3,emailid);
		st.setString(4,passwo);
		st.setString(5,enckey);
		st.setDate(6,date);
		st.executeUpdate();
		st.close();
		con.close();
		
	}

	private String getOtpKey() {
		GoogleAuthenticatorKey key = gauth.createCredentials();
		return key.getKey();
	}

	private String Encryptpassword(char[] passw) {
		StringBuilder passBuilder = new StringBuilder();
		for(char x:passw) {
			passBuilder.append(x);
		}
		String pass=passBuilder.toString();
		passBuilder.setLength(0);
		String hash=BCrypt.hashpw(pass+PEPPER, BCrypt.gensalt());
		java.util.Arrays.fill(passw, ' ');
		return hash;
		
	}
	public void sendRegistrationOtp(String id) throws AddressException, MessagingException, IOException {
			 tempOtp = gauth.createCredentials().getVerificationCode();
			 otptime=LocalDateTime.now();
			 em.setupServerProperties();
			 em.draftEmail(tempOtp, id);
			 em.sendEmail();
	}
	public boolean checkRegistrationOtp(int userotp) {
		LocalDateTime now=LocalDateTime.now();
		long minutes= ChronoUnit.MINUTES.between(otptime, now);
		if(minutes>otpex) {
			JOptionPane.showMessageDialog(null, "Otp expired Request new otp to register", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(userotp!=tempOtp) {
			JOptionPane.showMessageDialog(null, "Otp is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean checkExistingUser(String email) throws SQLException{
		boolean equal=false;
		String query="Select email_id from user_details";
		Connection con=DriverManager.getConnection(url,userid,pass);
		Statement st=con.createStatement();
		st.execute(query);
		ResultSet rs=st.getResultSet();
		
		while(rs.next()) {
			 if(rs.getString(1).equalsIgnoreCase(email.trim())) {
				 equal=true;
			 }
		}
		if(equal) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean validateUser(String name,char[] password) throws SQLException {
		String query="Select email_id,pass_key from user_details where email_id=?";
		Connection con=DriverManager.getConnection(url,userid,pass);
		PreparedStatement st=con.prepareStatement(query);
		st.setString(1, name);
		ResultSet rs=st.executeQuery();
		StringBuilder passBuilder = new StringBuilder();
		for(char x:password) {
			passBuilder.append(x);
		}
		String pass=passBuilder.toString();
		java.util.Arrays.fill(password, ' ');
		while(rs.next()) {
			user=rs.getString(1);
			key=rs.getString(2);
		}
		if(key!=null && BCrypt.checkpw(pass+PEPPER,key)) {
			validPass=true;
		}
		if(user!=null && user.equalsIgnoreCase(name)) {
			validUser=true;
		}
		if(validUser && validPass) {
			return true;
		}
		else {
			return false;
		}
		
	}
	public boolean checkEmail(String name) throws Exception {
		user=null;
		validUser=false;
		String query="Select email_id,otp_key from user_details where email_id=?";
		Connection con=DriverManager.getConnection(url,userid,pass);
		PreparedStatement st=con.prepareStatement(query);
		st.setString(1, name);
		
		ResultSet rs=st.executeQuery();
		while(rs.next()) {
			user=rs.getString(1);
			decrotpkey=rs.getString(2);
		}
		if(user!=null && user.equalsIgnoreCase(name)) {
			realotpkey=AESUtils.decryptOTPKey(decrotpkey);
			validUser=true;
			Otp=gauth.getTotpPassword(realotpkey);
			otpgeneratedtime=LocalDateTime.now();
			em.setupServerProperties();
			em.draftEmail(Otp, name);
			System.out.println(Otp);
		    //em.sendEmail();
		}
		if(validUser) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public boolean checkLoginOtp(int userresp) {
		boolean isEqual=false;
		boolean isValid=false;
		if(Otp==userresp) {
			isEqual=true;
		}
		LocalDateTime otpchecktime=LocalDateTime.now();
		long minutes= ChronoUnit.MINUTES.between(otpgeneratedtime,otpchecktime);
		System.out.println(minutes);
		if(minutes<3) {
			isValid=true;
		}
		return isValid && isEqual;
		
	}
	public void resendOtp() {
		times++;
		if(times>4) {
			System.out.println("Acess Permitted");
		}
		else {
			Otp=gauth.getTotpPassword(realotpkey);
			otpgeneratedtime=LocalDateTime.now();
			System.out.println(Otp);
		}
	}
	
	
	}
