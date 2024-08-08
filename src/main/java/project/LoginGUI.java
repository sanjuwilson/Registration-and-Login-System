package project;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class LoginGUI extends JPanel {
    JPanel centerpanel=new JPanel();
    JLabel imglabel=new JLabel();
    JPanel contentpanel=new JPanel();
    JPanel loginpanel=new JPanel();
    JPanel loadpanel=new JPanel();
    JLabel loading=new JLabel("Loading.....");
    Image imgload;
    JPanel panel2=new JPanel();
    private boolean isDigit=true;
    JLabel newpass=new JLabel("Enter New Password");
    JLabel confirmnewp=new JLabel("Confirm New Password");
    JTextField newpasstext=new JTextField();
    JTextField confirmnewptext=new JTextField();
    JButton changepass=new JButton("Reset Password");
    JLabel imgloadlabel=new JLabel();
    JLabel condition1=new JLabel("* Your password must be between 8 and 20 characters long.");
    JLabel condition2=new JLabel("* Include at least one uppercase letter (e.g., A, B, C).");
    JLabel condition3=new JLabel("* Include at least one lowercase letter (e.g., a, b, c).");
    JLabel condition4=new JLabel("* Include at least two digits (e.g., 0, 1, 2).");
    JLabel condition5=new JLabel("* Include at least one special character (e.g., @, #, $).");
    JLabel condition6=new JLabel("* Do not use the same character three times in a row (e.g., aaa, 111)");
    JLabel condition7=new JLabel("* Your password must not contain your first name or last name.");
    JPanel conditionholder=new JPanel();
    private boolean isrtotp=false;
    JLabel textmncname=new JLabel();
    private boolean isUser=false;
    JTextField nametext=new JTextField();
    JPasswordField passtext=new JPasswordField();
    JButton forgotpass=new JButton("Forgot Password");
    JLabel useridlabel=new JLabel("Enter User id,Phone number");
    JLabel passlabel=new JLabel("Enter Password");
    JLabel emaillabel=new JLabel("Enter Registered Email");
    ImageIcon icon;
    Timer timer;
    JLabel label=new JLabel();
    JLabel enterotp=new JLabel("Enter code");
    JLayeredPane pane=new JLayeredPane();
    int starttime=30;
    JLabel notitext=new JLabel();
    JButton resendbutton=new JButton("Resend Otp");
    JButton check=new JButton("Log on");
    
    private String text;
    JTextField emailverify=new JTextField();
    JPanel textandnot=new JPanel();
    
    JButton resetpass=new JButton("Reset Password");
    private boolean isuserempty=false;
    private boolean ispassmt=false;
    private boolean passusermt=false;
    JButton verifyemail=new JButton("Send code");
    JPanel notipanel=new JPanel();
    JPanel details=new JPanel();
    JPanel panellogin=new JPanel();
    JButton signin=new JButton("Sign in");
    JLabel textlabel=new JLabel("Log In");
    JPanel logohold=new JPanel();
    private boolean isEmail=false;
    private DatabaseConnector data=new DatabaseConnector();

    LoginGUI() {
    	
    	data=new DatabaseConnector();
        this.setVisible(true);
        this.setSize(500, 500);
        //this.setBackground(new Color(122, 210, 33));
        this.setLayout(new BorderLayout());
        designTextPassfields();
        positionTextPassFields();
        includeTextFields();
        
        designLabels();
        positionLabels();
        includeLabels();
        
        designButtons();
        positionButtons();
        includeButtons();
        manageActionListeners();
        logohold.setLayout(null);
        logohold.setPreferredSize(new Dimension(450, 210));
        logohold.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.BLACK));
        logohold.setBackground(Color.BLACK);
      
       loadpanel.setLayout(new FlowLayout(FlowLayout.CENTER,1,0));
       loadpanel.setBounds(100,250,200,55);
       loadpanel.setBackground(Color.BLACK);
       loadpanel.setVisible(false);
       
       conditionholder.setBounds(2,160,410,200);
       conditionholder.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
       conditionholder.setVisible(false);
       conditionholder.setBackground(Color.BLACK);

        loginpanel.add(notipanel);
        
        
      
      
      
       loginpanel.add(conditionholder);
        loginpanel.add(enterotp);
       
       
        loginpanel.add(loadpanel);
        
        loginpanel.add(resendbutton);
       
       
       
        loginpanel.setPreferredSize(new Dimension(447,300));
        loginpanel.setLayout(null);
        
            
       // loginpanel.setBackground(Color.RED);
        textandnot.setLayout(new BoxLayout(textandnot, BoxLayout.Y_AXIS));
        textandnot.add(Box.createVerticalStrut(10));
        textandnot.add(panellogin);
        textandnot.add(Box.createVerticalStrut(10));
        textandnot.add(notipanel);
        notipanel.setVisible(false);

       
        contentpanel.setLayout(new BoxLayout(contentpanel, BoxLayout.Y_AXIS));
        contentpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.BLACK));
        contentpanel.add(logohold);
        contentpanel.add(panellogin);
        contentpanel.add(notipanel);
        contentpanel.add(loginpanel);
        contentpanel.setBounds(0,0,480,650);
        loadpanel.setBounds(150, 400, 200, 50); 
        //contentpanel.setBackground(Color.BLUE);
        pane.setBounds(0, 0,480,690);
        pane.setLayout(null);

       pane.add(contentpanel,JLayeredPane.DEFAULT_LAYER);
       pane.add(loadpanel,JLayeredPane.PALETTE_LAYER);
        this.add(pane, BorderLayout.CENTER);
    }
    private void getNoti(String text,Color c) {
    	notipanel.setBackground(c);
    	notipanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
    	notitext.setText(text);
    	notitext.setForeground(Color.BLACK);
    	notitext.setFont(new Font("System-ui",Font.BOLD,13));
    	notipanel.add(notitext);
    	notipanel.setVisible(true);
    	
    }
    private void setComponentsEnabled(boolean enabled) {
            for (Component component : loginpanel.getComponents()) {
                if (component != loadpanel) { // Ensure the loading panel itself is not disabled
                    if (component.isEnabled() != enabled) {
                        component.setEnabled(enabled);
                        if (!enabled) {
                            component.setForeground(new Color(150, 150, 150)); // Simulate blur
                            component.setBackground(new Color(240, 240, 240)); // Adjust background if needed
                        } else {
                            restoreComponentDesign(component);
                        }
                    }
                }
            }
    }
  

    private void checkAndSend(String email) {
    	loadpanel.setVisible(true);
    	
    	new SwingWorker<Void,Void>(){
    		
			@Override
			protected Void doInBackground() throws Exception {
				setComponentsEnabled(false);
				try {
					if(data.checkEmail(text)) {
						isEmail=true;
					}
					else {
						isEmail=false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
				
			}
			  @Override
	            protected void done() {
				  setComponentsEnabled(true);
				  loadpanel.setVisible(false);
				  if(isEmail) {
					  getNoti("A code has been sent to your email-id",Color.GREEN);
						emailverify.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
						emailverify.setText("");
						verifyemail.setVisible(false);
						emaillabel.setVisible(false);
						resetpass.setVisible(true);
						enterotp.setVisible(true);
			   	        check.setVisible(true);
			   	        timer=new Timer(1000,new ActionListener() {

			   			@Override
			   			public void actionPerformed(ActionEvent e) {
			   				if (starttime>0) {
			   					starttime--;
				   				label.setText("The otp can be resend in "+ starttime);
			   				}
			   			  else {
		                        timer.stop();
		                        resendbutton.setEnabled(true);
		                        resendbutton.setEnabled(true);
		                        starttime=30;
		                    }
			   				
			   				
			   				
			   			}
			   	    	   
			   	       });
			   	     timer.start();
			   	     label.setBounds(200,140,300,40);
			   	     loginpanel.add(label); 
				  }
				  else {
					  getNoti("The email you entered dosen't match our records",Color.RED);
					  emailverify.setBorder(BorderFactory.createLineBorder(Color.RED,4));
						
				  }
				  
			  }
			}.execute();;

			
    }
    private void validateLogin() {
    	loadpanel.setVisible(true);
    	new SwingWorker<Void,Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				setComponentsEnabled(false);
				try {
			 		   String name=nametext.getText();
			 		   char[]pass=passtext.getPassword();
			     	   isUser=data.validateUser(name,pass);
			     	   if(passtext.getPassword().length==0 && nametext.getText().isEmpty()) {
			     		   passusermt=true;  
			     	   }
			     	   else {
			     		   passusermt=false; 
			     	   }
			     	   if(nametext.getText().isEmpty()) {
			     		   isuserempty=true;
			     		   System.out.println("hi");
			     		   //nametext.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			     	   }
			     	   else {
			     		   isuserempty=false;
			     	   }
			     	  
			     	   if(passtext.getPassword().length==0) {
			     		   ispassmt=true;
			     		   //getNoti("Password is required Click forgot password if forgot",Color.RED);
			     		   //passtext.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			     	   }
			     	   else {
			     		   ispassmt=false;
			     	   }
			     	   
			     	 }catch (SQLException e) {
			     		 getNoti("Unexpected Error Occured",Color.RED);
			     		 e.printStackTrace();
			    		}
				return null;
			}
			  @Override
	            protected void done() {
				  setComponentsEnabled(true);
				  loadpanel.setVisible(false);
				  if(passusermt) {
		     		   getNoti("Fields Cant be left blank",Color.RED);
		     		   nametext.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		     		   passtext.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		     	   }
		     	   else if(isuserempty) {
		     		   getNoti("User id can't be left blank",Color.RED);
		     		   nametext.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		     		   passtext.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		     	   }
		     	   else if(ispassmt) {
		     		   getNoti("Password is required Click forgot password if forgot",Color.RED);
		     		   nametext.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		     		   passtext.setBorder(BorderFactory.createLineBorder(Color.RED,3));
		     	   }
		     	   else if(!isUser) {
		     		   getNoti("Invalid Combination of Userid and Password",Color.RED);
		     		   nametext.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		     		   passtext.setBorder(BorderFactory.createLineBorder(Color.RED,3));
		     	   }
		     	   else {
		     		   getNoti("Welcome sir",Color.GREEN);
		     		   nametext.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		     		   passtext.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
					}
			  }
    		
    	}.execute();
    }
    private void resetPass() {
    	loadpanel.setVisible(true);
    	new SwingWorker<Void,Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				setComponentsEnabled(false);
				 try {
					  isDigit=true;
	         		  boolean isUser=data.checkLoginOtp(Integer.parseInt(emailverify.getText().trim()));
	         		  if(isUser) {
	         			  isrtotp=true;
	             	  }
	             	  else {
	             		  isrtotp=false;
	             	  }
	         	  }catch(NumberFormatException e) {
	         		  isDigit=false;
	         		  e.printStackTrace();
	         	  }
				return null;
			}
			  @Override
	            protected void done() {
				  setComponentsEnabled(true);
				  loadpanel.setVisible(false);
				  if(!isDigit) {
					  getNoti("Only digits accepted",Color.RED);
				  }
				  if(isrtotp) {
					  newpass.setVisible(true);
                      confirmnewp.setVisible(true);
	              	  resendbutton.setVisible(false);
                      emailverify.setVisible(false);
	                  verifyemail.setVisible(false);
	                  emaillabel.setVisible(false);
	                  notipanel.setVisible(false);
	                  label.setVisible(false);
	                  enterotp.setVisible(false);
	                  resetpass.setVisible(false);
	                  check.setVisible(false);
	                  newpasstext.setVisible(true);
                      confirmnewptext.setVisible(true);
                      conditionholder.setVisible(true);
                      
				  }
				  
				  else {
					  getNoti("Incorrect Otp.Try again",Color.RED);
				  }
			  }
    		
    	}.execute();
    	
    }
    	
 	      
    private void restoreComponentDesign(Component component) {
    	conditionholder.setBackground(Color.BLACK);
    	if (component instanceof JTextField) {
            JTextField textField = (JTextField) component;
            textField.setForeground(Color.BLACK);
            textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            textField.setFont(new Font("Cambria", Font.PLAIN, 20));
        } else if (component instanceof JButton) {
            JButton button = (JButton) component;
            if(button.getText().equalsIgnoreCase("Forgot Password")) {
            	designForgotPasswordButton();
            }
            else if(button.getText().equalsIgnoreCase("Reset Password")) {
                  resetpass.setBackground(Color.WHITE);
                  resetpass.setFont(new Font("System-ui",Font.BOLD,15));
                  resetpass.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                  resetpass.setFocusable(false);
                  resetpass.setForeground(Color.BLACK);
            }
            else {
            	button.setForeground(Color.BLACK);
                button.setBackground(Color.RED);
                button.setFont(new Font("System-ui", Font.BOLD, 15));
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }
        } else if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            label.setForeground(Color.BLACK);
        }
       
    }
    
    private void designTextPassfields() {
    	commonTextFieldDesign(nametext);
    	commonTextFieldDesign(emailverify);
    	commonTextFieldDesign(newpasstext);
    	commonTextFieldDesign(confirmnewptext);
        passtext.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        passtext.setFont(new Font("Cambria",Font.PLAIN,20));
        emailverify.setVisible(false); 
        newpasstext.setVisible(false);
        confirmnewptext.setVisible(false);
         
    }
    private void commonTextFieldDesign(JTextField text) {
    	text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    	text.setFont(new Font("Cambria",Font.PLAIN,20));
          
    }
    private void positionTextPassFields() {
    	nametext.setBounds(50,43,380,40);
    	newpasstext.setBounds(2,43,280,40);
    	passtext.setBounds(50,114,380,40);
    	confirmnewptext.setBounds(2,114,280,40);
    	emailverify.setBounds(11,41,290,40);
    }
    private void includeTextFields() {
    	loginpanel.add(emailverify);
    	loginpanel.add(passtext);  
    	loginpanel.add(nametext);
    	loginpanel.add(newpasstext);
    	loginpanel.add(confirmnewptext);
    }
    private void designButtons() {
        commonButtonDesign(signin);
        commonButtonDesign(verifyemail);
        commonButtonDesign(check);
        verifyemail.setVisible(false);
               
        check.setVisible(false);
        check.setBackground(Color.RED);
        check.setFont(new Font("System-ui",Font.BOLD,15));
        check.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        check.setFocusable(false);
        
        resendbutton.setVisible(false);
        resendbutton.setEnabled(false);
        
        resetpass.setVisible(false);
        resetpass.setBackground(Color.WHITE);
        resetpass.setFont(new Font("System-ui",Font.BOLD,15));
        resetpass.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        resetpass.setFocusable(false);
        
        designForgotPasswordButton();
        
   }
   private void commonButtonDesign(JButton button) {
       button.setBackground(Color.red);
       button.setFont(new Font("System-ui",Font.BOLD,15));
       button.setForeground(Color.BLACK);
       button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
       button.setFocusable(false);
   }
   private void designForgotPasswordButton(){
       forgotpass.setBorderPainted(false);
       forgotpass.setOpaque(false);
       forgotpass.setBackground(Color.WHITE);
       forgotpass.setForeground(Color.BLACK);
       forgotpass.setFont(new Font("Arial", Font.PLAIN, 15));
       forgotpass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
       forgotpass.setFocusPainted(false);
       forgotpass.setContentAreaFilled(false);
       forgotpass.setBorder(BorderFactory.createEmptyBorder());
       forgotpass.setHorizontalAlignment(SwingConstants.LEFT);
       customizeForgotPassMouseListener();
   }
   private void customizeForgotPassMouseListener() {
	   forgotpass.addMouseListener(new MouseAdapter() {
    	   @Override
    	   public void mouseEntered(MouseEvent e) {
    		   forgotpass.setText("<html><u>Forgot Password</u></html>");
    	   }
    	   @Override
    	   public void mousePressed(MouseEvent e) {
    		   forgotpass.setForeground(Color.GRAY);
    	   }
    	   @Override
    	   public void mouseReleased(MouseEvent e) {
    		   forgotpass.setForeground(Color.BLACK);
    	   }
    	   @Override
    	   public void mouseExited(MouseEvent e) {
    		   forgotpass.setText("Forgot Password");
    		  
    	   }
       });
   }

   private void positionButtons() {
	   signin.setBounds(179,188, 100, 40);
	   forgotpass.setBounds(50,149,150,30);
	   resetpass.setBounds(125,85,140,40);
	   verifyemail.setBounds(11,83,100,40);
	   check.setBounds(11,85,100,40);
	   resendbutton.setBounds(300,220,100,40);
   }
   private void includeButtons() {
	   loginpanel.add(verifyemail);
	   loginpanel.add(check);
	   loginpanel.add(signin);
	   loginpanel.add(forgotpass);
	   loginpanel.add(resetpass);
   }
   private void designLabels(){
	   imglabel.setBackground(Color.RED);
	   addImage(imglabel);
	   addLoadingImage(imgloadlabel);
	   
	   loading.setForeground(Color.red);
	   loading.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
	   
	   textmncname.setText("Eagle's Realm");
       textmncname.setFont(new Font("Comic Sans MS", Font.BOLD, 29));
       textmncname.setForeground(Color.RED);
      
       
       useridlabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
       
       passlabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
      
       emaillabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
       emaillabel.setBounds(50,89,300,50);
       emaillabel.setVisible(false);
       
       enterotp.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
       enterotp.setVisible(false);
       
       textlabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
       
       newpass.setVisible(false);
       newpass.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
       
       confirmnewp.setVisible(false);
       confirmnewp.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
       
       conditionDesign(condition1);
       conditionDesign(condition2);
       conditionDesign(condition3);
       conditionDesign(condition4);
       conditionDesign(condition5);
       conditionDesign(condition6);
       conditionDesign(condition7);
       
   }
   private void conditionDesign(JLabel label) {
	   label.setForeground(Color.RED);
       label.setFont(new Font("Times New Roman", Font.BOLD, 13));
       label.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
   }
   
	

private void addLoadingImage(JLabel label) {
	   imgload=icon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
       ImageIcon myImage=new ImageIcon(imgload);
       imgloadlabel.setIcon(myImage);
   }
   private void addImage(JLabel label) {
	   icon = new ImageIcon("C:\\Users\\sanju\\Downloads\\eagle.png");
       Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
       ImageIcon iconReal = new ImageIcon(img);
       imglabel.setIcon(iconReal);
   }
   private void positionLabels() {
	   imglabel.setBounds(20, 5, 250, 200);
	   textmncname.setBounds(210, 5, 200, 200);
	   useridlabel.setBounds(50,-1,300,50);
	   newpass.setBounds(2,-1,250,50);
	   confirmnewp.setBounds(2,73,250,50);
	   passlabel.setBounds(50,73,300,50);
	   emaillabel.setBounds(10,1,300,40);
	   enterotp.setBounds(11,2,300,50);
   }
   private void includeLabels() {
	   panellogin.add(textlabel);
	   loginpanel.add(emaillabel);
	   loginpanel.add(passlabel);
	   loginpanel.add(useridlabel);
	   loginpanel.add(newpass);
	   loginpanel.add(confirmnewp);
	   loadpanel.add(imgloadlabel);
       loadpanel.add(loading);
       logohold.add(textmncname);
       logohold.add(imglabel);
       conditionholder.add(condition1);
       conditionholder.add(condition2);
       conditionholder.add(condition3);
       conditionholder.add(condition4);
       conditionholder.add(condition5);
       conditionholder.add(condition6);
       conditionholder.add(condition7);
       
   }
   private void manageActionListeners() {
	   signin.addActionListener(m->{
    	   validateLogin();
       });
	   forgotpass.addActionListener(i->{
    	   notipanel.setVisible(false);
           useridlabel.setVisible(false);
           nametext.setVisible(false);
           passlabel.setVisible(false);
           passtext.setVisible(false);
           forgotpass.setVisible(false);
           signin.setVisible(false);  
           resendbutton.setVisible(true);
           emailverify.setVisible(true);
           verifyemail.setVisible(true);
           emaillabel.setVisible(true);
       });
	   verifyemail.addActionListener(n->{
    	   text=emailverify.getText();
    	   if(text.isEmpty()) {
    		   getNoti("This field can't be left blank",Color.RED);
    	   }
    	   else {
    		   checkAndSend(text);
    	   }
    	   
       });
	   resendbutton.addActionListener(u->{
    	   data.resendOtp();
    	   manageResendTimer();
       });  
       check.addActionListener(o->{
    	  if(emailverify.getText().isEmpty()) {
    		  getNoti("This field Cant't be left blank",Color.RED);
    	  }
    	  else {
    		  try {
        		  boolean isUser=data.checkLoginOtp(Integer.parseInt(emailverify.getText().trim()));
        		  if(!isUser) {
            		  getNoti("Incorrect Otp.Try again",Color.RED);
            	  }
            	  else {
            		  getNoti("Your in",Color.GREEN);
            	  }
        	  }catch(NumberFormatException e) {
        		  getNoti("This field only accepts numbers",Color.RED);
        		  e.printStackTrace();
        	  }
        	 
    	  }
    	 
       });
       resetpass.addActionListener(l->{
    	   if(emailverify.getText().isEmpty()) {
     		  getNoti("This field Cant't be left blank",Color.RED);
     	  }
     	  else {
         	 resetPass();
     	  } 
       });
 
   }
   private void manageResendTimer() {
	   starttime=30;
	   timer=new Timer(1000,new ActionListener() {

  			@Override
  			public void actionPerformed(ActionEvent e) {
  				if (starttime>0) {
  					resendbutton.setEnabled(false);
  					starttime--;
	   				label.setText("The otp can be resend in "+ starttime);
  				}
  			  else {
                  timer.stop();
                  resendbutton.setEnabled(true);
                  
              }
  			}
  	    	   
  	       });
	   timer.start();
	   label.setBounds(200,140,300,40);
 	   loginpanel.add(label);
   }
 
}
