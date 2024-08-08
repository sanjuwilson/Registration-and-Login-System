package project;

import org.jdesktop.swingx.JXDatePicker;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.regex.Pattern;

public class RegistrationGUI extends JPanel {
    JPanel panel=new JPanel();
    JTextField fname=new JTextField();
    JTextField lname =new JTextField();
    JXDatePicker datePicker;
    JButton register=new JButton("Register");
    JButton button=new JButton("Confirm");
    JTextField emailid=new JTextField();
    JPanel notifypanel=new JPanel();
    JPanel notifypanel2=new JPanel();
    JLabel notifylabel=new JLabel();
    JLabel notifylabel2=new JLabel();
    JLabel firstname=new JLabel("Enter First Name");
    JLabel lastname=new JLabel("Enter Last Name");
    JLabel userid=new JLabel("Enter Email-id");
    JLabel createpass=new JLabel("Create Password");
    JLabel confirmpassw=new JLabel("Confirm Password");
    JLabel dob=new JLabel("Enter Date of Birth");
    JPanel notiholder=new JPanel();
    private int digcount=0;
    private boolean upper=false;
    private boolean anouser=false;
	private boolean lower=false;
	private static final int DELAY=1000;
	private int time=8;
	private boolean special=false;
	private boolean chareq=false;
    private char[]ch;
    JLabel info=new JLabel("An email with otp is sent to the given email address. Enter Otp:");
    DatabaseConnector c=new DatabaseConnector();
    JPasswordField pass=new JPasswordField();
    JPasswordField confirmpass = new JPasswordField();
    JButton resend=new JButton("resend otp");
    private Timer timer;
    JPanel conditionholder=new JPanel();
    JLabel condition1=new JLabel("Your password must be between 8 and 20 characters long.");
    JLabel condition2=new JLabel("Include at least one uppercase letter (e.g., A, B, C).");
    JLabel condition3=new JLabel("Include at least one lowercase letter (e.g., a, b, c).");
    JLabel condition4=new JLabel("Include at least two digits (e.g., 0, 1, 2).");
    JLabel condition5=new JLabel("Include at least one special character (e.g., @, #, $).");
    JLabel condition6=new JLabel("Do not use the same character three times in a row (e.g., aaa, 111)");
    JLabel condition7=new JLabel("Your password must not contain your first name or last name.");
    private static final int delay = 1000;
    JTextField otp = new JTextField();
    JPanel top=new JPanel();
    JPanel toppanel=new JPanel();
    JPanel bottom=new JPanel();
    JPanel left=new JPanel();
    JPanel right=new JPanel();
   
    JLabel match=new JLabel("The passwords do not match");
    private Timer timer2;
    private int starttime = 30;
    private JLabel timehold = new JLabel(); // Initialize timehold
    DatabaseConnector connect = new DatabaseConnector();

    RegistrationGUI() {
        this.setSize(800, 500);
        this.setVisible(true);
        this.setLayout(new BorderLayout(20,20));
        match.setVisible(false);
        match.setForeground(Color.RED);
        
        notifypanel.setVisible(false);
        notifypanel2.setVisible(false);
        
        
        panel.setPreferredSize(new Dimension(500, 800));
        panel.setLayout(new FlowLayout(FlowLayout.LEADING,10,10));
        
        condition1.setForeground(Color.RED);
        condition2.setForeground(Color.RED);
        condition3.setForeground(Color.RED);
        condition4.setForeground(Color.RED);
        condition5.setForeground(Color.RED);
        condition6.setForeground(Color.GREEN);
        condition7.setForeground(Color.RED);
        
        conditionholder.add(condition1);
        conditionholder.add(condition2);
        conditionholder.add(condition3);
        conditionholder.add(condition4);
        conditionholder.add(condition5);
        //conditionholder.add(condition6);
        //conditionholder.add(condition7);

      notiholder.setPreferredSize(new Dimension(500,150));
        notiholder.setVisible(false);
        conditionholder.setPreferredSize(new Dimension(380,300));
        
        fname.setPreferredSize(new Dimension(300, 40));
        fname.setFont(new Font("Cambria",Font.PLAIN,20));
        fname.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        
        lname.setPreferredSize(new Dimension(300, 40));
        lname.setFont(new Font("Cambria",Font.PLAIN,20));
        lname.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        
        emailid.setPreferredSize(new Dimension(300, 40));
        emailid.setFont(new Font("Cambria",Font.PLAIN,20));
        emailid.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
       
        firstname.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lastname.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        userid.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        createpass.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        confirmpassw.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        dob.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        
        pass.setPreferredSize(new Dimension(300,40));
        pass.setFont(new Font("Cambria",Font.PLAIN,20));
        pass.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        pass.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				
				
				getUpdate();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				getUpdate();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				getUpdate();
				
			}
			public void getUpdate() {
				upper=false;
				lower=false;
				special=false;
				ch=pass.getPassword();
				digcount=0;
				if(ch.length<=20 && ch.length>=8) {
					condition1.setForeground(Color.GREEN);
					chareq=true;
				}
				else {
					condition1.setForeground(Color.RED);
				}
				for(int i=0;i<ch.length;i++) {
					if(Character.isUpperCase(ch[i])) {
						upper=true;
					}
					
					if(Character.isDigit(ch[i])) {
						digcount++;
						
					}
					if(Character.isLowerCase(ch[i])){
						lower=true;
					}
					
					if(!Character.isLetterOrDigit(ch[i])) {
						special=true;
						
					}		
					
				}
				if(digcount>=2) {
					condition4.setForeground(Color.GREEN);
				}
				else {
					condition4.setForeground(Color.RED);
				}
				if(upper) {
					condition2.setForeground(Color.GREEN);
				}
				else {
					condition2.setForeground(Color.RED);
				}
				if(lower) {
					condition3.setForeground(Color.GREEN);
				}
				else {
					condition3.setForeground(Color.RED);
				}
				if(special) {
					condition5.setForeground(Color.GREEN);
					
				}
				else {
					condition5.setForeground(Color.RED);
				}
				if(!checkPass(pass,confirmpass)) {
					match.setVisible(true);
				}
				else {
					match.setVisible(false);
				}
				
			}
			});
        confirmpass.setPreferredSize(new Dimension(300,40));
        confirmpass.setFont(new Font("Cambria",Font.PLAIN,20));
        confirmpass.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        confirmpass.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				check();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				check();
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				check();
				
			}
			public void check() {
				if(!checkPass(pass,confirmpass)) {
					match.setVisible(true);
				}
				else {
					match.setVisible(false);
				}
			}
			
		});
      
       
        otp.setPreferredSize(new Dimension(100, 30));

        resend.setEnabled(false);

        info.setVisible(false);
        otp.setVisible(false);

        register.setVisible(false);
        
        
        
     
        datePicker = new JXDatePicker();
        datePicker.setDate(new java.util.Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        datePicker.setFormats(dateFormat);
        datePicker.setPreferredSize(new Dimension(300,40));
        datePicker.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        notiholder.add(notifypanel);
        notiholder.add(notifypanel2);
        panel.add(notiholder);
        panel.add(firstname);        
        panel.add(fname);
        panel.add(lastname);
        panel.add(lname);
        panel.add(userid);
        panel.add(emailid);
        panel.add(dob);
        panel.add(datePicker);
        panel.add(createpass);
        panel.add(pass);
        panel.add(confirmpassw);
        panel.add(confirmpass);
        panel.add(match);
       
        panel.add(button);
        panel.add(conditionholder);
        panel.add(info);
        panel.add(otp);
        panel.add(register);
        panel.add(resend);
        panel.add(timehold); 
        //panel.setBackground(Color.BLUE);
        panel.setPreferredSize(new Dimension(500,500));
        
         
        button.setBackground(Color.RED);
        button.setBounds(147, 250, 100, 40);
        button.setFont(new Font("System-ui",Font.BOLD,15));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        button.setPreferredSize(new Dimension(100,40));
        button.setFocusable(false);
        button.addActionListener(i -> {
        	boolean containsname=false;
        	boolean repeat=false;
			ch=pass.getPassword();
			String passw=new String(ch);
			if(!fname.getText().trim().isEmpty() && passw.contains(fname.getText()) || !lname.getText().trim().isEmpty() && passw.contains(lname.getText())) {
				containsname=true;
			}
            try {
				if(connect.checkExistingUser(emailid.getText())){
					anouser=true;
				}
				else {
					anouser=false;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int j=0;j<ch.length;j++) {
				if (j>=2&&ch[j]==ch[j-1]&&ch[j]==ch[j-2]){
					repeat=true;
		            }
			}
			if(fname.getText().isEmpty() || lname.getText().isEmpty() || emailid.getText().isEmpty() ||  passw.isEmpty() || confirmpass.getPassword().length==0 ) {
				getNot("All fields are mandatory",true);
			}
			else if(!upper || !lower || !special || digcount<2 || !chareq) {
				getNot("Password Requirements not met",true);
			}
			else if(repeat && !containsname) {
				notifypanel2.setVisible(false);
				getNot("Same character cannot repeat multiple number of times",true);
			}
			else if(containsname && !repeat) {
				notifypanel2.setVisible(false);
				getNot("Password cannot contain first name or last name",true);
				
			}
			else if(containsname && repeat) {
				getNot("Same character cannot repeat multiple number of times","Password cannot contain first name or last name");
			}
			else if(!checkPass(pass,confirmpass)) {
				getNot("Passwords should match",true);
			}
			else if(!isValidEmail(emailid.getText())) {
				getNot("Invalid E-mail",true);
				return;
			}
			else if(anouser) {
				getNot("A user already exists with the given email",true);
			}
			else {
				try {
					notifypanel.setVisible(false);
					notifypanel2.setVisible(false);
					String email = emailid.getText();
					connect.sendRegistrationOtp(email);
					timer2=new Timer(DELAY,new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								 getNot("The otp has been sent",false);
								 time--;
								 if(timer2!=null && time<0) {
									 timer2.stop();
									 notifypanel.setVisible(false);
									 time=8;
								 }
							}});
			            timer2.start();
			            info.setVisible(true);
			            otp.setVisible(true);
			            register.setVisible(true);
			            starttime = 30; // Reset the timer count
			            resend.setEnabled(false); // Disable the resend button initially

			            timer = new Timer(delay, new ActionListener() {
			                @Override
			                public void actionPerformed(ActionEvent e) {
			                    if (starttime > 0) {
			                        timehold.setText("Time left: " + starttime + " seconds");
			                        starttime--;
			                    }
			                    else {
			                        timer.stop();
			                        timehold.setText("resend OTP.");
			                        resend.setEnabled(true); // Enable the resend button
			                    }
			                    panel.repaint(); // Repaint the panel to reflect updates
			                }
			            });
			            timer.start();
				} catch (AddressException e1) {
					e1.printStackTrace();
					
				} catch (MessagingException e1) {
					e1.printStackTrace();
					
				} catch (IOException e1) {
					e1.printStackTrace();
					
				}
			}
        });

        register.addActionListener(m -> {
        	try {
        		 int userotp = Integer.parseInt(otp.getText().trim());
        		 
        		 if (connect.checkRegistrationOtp(userotp)) {
        			 registerUser();
        		 }
        		 else {
                	 getNot("Otp Incorrect", true);
                }
        	}catch(NumberFormatException e) {
        		e.printStackTrace();
        		getNot("Only numbers allowed",true);
        	}catch (Exception e1) {
				e1.printStackTrace();	
			}        
        });
        
        resend.addActionListener(m->{
        	String email = emailid.getText();
        	starttime = 30;
        	try {
				connect.sendRegistrationOtp(email);
				timer2=new Timer(DELAY,new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						 getNot("The new otp has been sent",false);
						 time--;
						 if(timer2!=null && time<0) {
							 timer2.stop();
							 notifypanel.setVisible(false);
							 time=8;
						 }
					}});
				timer2.start();
				
			} catch (AddressException e1) {
				e1.printStackTrace();
				getNot("The E-mail Address is Invalid",true);
			} catch (MessagingException e1) {
				e1.printStackTrace();
				getNot("Error Occured.Cannot send E-mail",true);
			} catch (IOException e1) {
				e1.printStackTrace();
				getNot("Error Occured",true);
			}
        	top.setVisible(true);
        	timer = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (starttime > 0) {
                    	resend.setEnabled(false);
                        timehold.setText("Time left: " + starttime + " seconds");
                        starttime--;
                    }
                    else {
                        timer.stop();
                        timehold.setText("OTP expired. Please click resend OTP again.");
                        resend.setEnabled(true); 
                    }
                    if(starttime<26){
                    	top.setVisible(false);
                    } 
                    panel.repaint(); 
                }
            });
            timer.start();
        });
        this.add(panel,BorderLayout.CENTER);
        this.add(toppanel,BorderLayout.NORTH);
        this.add(bottom,BorderLayout.SOUTH);
        this.add(left,BorderLayout.EAST);
        this.add(right,BorderLayout.WEST);
    }

    private void registerUser() throws Exception {
        String first = fname.getText().trim();
        String last = lname.getText().trim();
        String email = emailid.getText().trim();;
        try {
            char[] passar = pass.getPassword();
            java.util.Date utilDate = datePicker.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            connect.writeInto(first, last, email,passar, sqlDate);
            getNot("Registration Sucessfull",false);
        } catch (SQLException e) {
                e.printStackTrace();
                getNot("Registration Failed",true);
        }
        
            
         
    }
    private boolean checkPass(JPasswordField pass,JPasswordField confirmpass) {
    	char[]passarr=pass.getPassword();
    	char[]conpass=confirmpass.getPassword();
    	if(Arrays.equals(passarr, conpass)) {
             Arrays.fill(passarr, ' ');
             Arrays.fill(conpass, ' ');
    		 return true;
    	}
    	else {
    		 Arrays.fill(passarr, ' ');
             Arrays.fill(conpass, ' ');
    		 return false;
    	}
    }
    private void getNot(String noti,boolean error) {
    	notifylabel.setText(noti);
    	notifypanel.add(notifylabel);
    	if(error) {
    		notifypanel.setBackground(Color.RED);
    	}
    	else {
    		notifypanel.setBackground(Color.GREEN);
    	}
    	notifypanel.setPreferredSize(new Dimension(400,50));
    	notifylabel.setForeground(Color.WHITE);
    	notiholder.setVisible(true);
    	notifypanel.setVisible(true);
    	notifypanel.revalidate();
    	notifypanel.repaint();
		
    }
    private void getNot(String noti,String noti2) {
    	notifylabel.setText(noti);
    	notifylabel2.setText(noti2);
    	notifypanel.add(notifylabel);
    	
    	notifypanel.setBackground(Color.RED);
    	notifypanel.setPreferredSize(new Dimension(400,50));
    	notifylabel.setForeground(Color.WHITE);
    	notifypanel.setVisible(true);
    	
    	notifypanel2.add(notifylabel2);
    	notifypanel2.setBackground(Color.RED);
    	notifypanel2.setPreferredSize(new Dimension(400,50));
    	notifylabel2.setForeground(Color.WHITE);
    	notifypanel2.setVisible(true);
    	notiholder.setVisible(true);
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
    
}
