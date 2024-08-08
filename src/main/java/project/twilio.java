package project;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class twilio extends JPanel {
    JPanel centerpanel = new JPanel();
    JLabel imglabel = new JLabel();
    JPanel contentpanel = new JPanel();
    JPanel loginpanel = new JPanel();
    JPanel loadpanel = new JPanel();
    JLabel loading = new JLabel("Loading.....");
    Image imgload;
    JPanel panel2 = new JPanel();
    JLabel imgloadlabel = new JLabel();
    JLabel textmncname = new JLabel();
    JTextField nametext = new JTextField();
    JPasswordField passtext = new JPasswordField();
    JButton forgotpass = new JButton("Forgot Password");
    JLabel useridlabel = new JLabel("Enter User id,Phone number");
    JLabel passlabel = new JLabel("Enter Password");
    JLabel emaillabel = new JLabel("Enter Registered Email");
    ImageIcon icon;
    Timer timer;
    JLabel label = new JLabel();
    int starttime = 30;
    JLabel notitext = new JLabel();
    JButton resendbutton = new JButton("Resend Otp");
    JButton check = new JButton("check otp");
    JTextField otptext = new JTextField("OTP adikoo plz");
    private String text;
    JTextField emailverify = new JTextField();
    JPanel textandnot = new JPanel();
    ImageIcon icon2;
    private boolean isuserempty = false;
    private boolean ispassmt = false;
    private boolean passusermt = false;
    JButton verifyemail = new JButton("Send code");
    JPanel notipanel = new JPanel();
    JPanel details = new JPanel();
    JPanel labellogin = new JPanel();
    JButton signin = new JButton("Sign in");
    JLabel textlabel = new JLabel("Log In");
    JPanel logohold = new JPanel();
    private boolean isEmail = false;
    private DatabaseConnector data = new DatabaseConnector();
    JLayeredPane layeredPane = new JLayeredPane(); // Layered pane to hold panels

    twilio() {
        data = new DatabaseConnector();
        this.setVisible(true);
        this.setSize(500, 500);
        this.setLayout(new BorderLayout());

        icon = new ImageIcon("C:\\Users\\sanju\\Downloads\\eagle.png");
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon iconReal = new ImageIcon(img);
        imglabel.setIcon(iconReal);
        imglabel.setBackground(Color.RED);

        icon2 = new ImageIcon("C:\\Users\\sanju\\OneDrive\\Documents\\eagle2");

        textmncname.setText("Eagle's Realm");
        textmncname.setFont(new Font("Comic Sans MS", Font.BOLD, 29));
        textmncname.setForeground(Color.RED);
        textmncname.setBackground(Color.BLUE);

        logohold.setLayout(null);
        logohold.setPreferredSize(new Dimension(450, 210));

        logohold.add(imglabel);
        logohold.add(textmncname);
        logohold.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.BLACK));
        logohold.setBackground(Color.BLACK);

        imglabel.setBounds(20, 5, 250, 200);
        textmncname.setBounds(210, 5, 200, 200);

        useridlabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        useridlabel.setBounds(10, 10, 300, 50);

        nametext.setPreferredSize(new Dimension(200, 30));
        nametext.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        nametext.setBounds(10, 58, 380, 40);
        nametext.setFont(new Font("Cambria", Font.PLAIN, 20));

        passlabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        passlabel.setBounds(10, 90, 300, 50);

        emaillabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        emaillabel.setBounds(10, 90, 300, 50);
        emaillabel.setVisible(false);

        passtext.setPreferredSize(new Dimension(200, 30));
        passtext.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        passtext.setBounds(10, 136, 380, 40);
        passtext.setFont(new Font("Cambria", Font.PLAIN, 20));

        forgotpass.setBounds(10, 176, 150, 30);
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

        emaillabel.setBounds(10, 1, 300, 40);

        emailverify.setPreferredSize(new Dimension(100, 80));
        emailverify.setBounds(11, 41, 290, 40);
        emailverify.setVisible(false);
        emailverify.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        emailverify.setFont(new Font("Cambria", Font.PLAIN, 18));

        otptext.setVisible(false);
        check.setVisible(false);
        resendbutton.setVisible(false);
        resendbutton.setEnabled(false);
        otptext.setBounds(10, 176, 150, 30);
        check.setBounds(150, 250, 100, 40);
        resendbutton.setBounds(300, 220, 100, 40);

        forgotpass.addActionListener(i -> {
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

        verifyemail.setBounds(11, 86, 100, 40);
        verifyemail.setVisible(false);
        verifyemail.setBackground(Color.RED);
        verifyemail.setFont(new Font("System-ui", Font.BOLD, 15));
        verifyemail.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        verifyemail.setFocusable(false);
        verifyemail.addActionListener(n -> {
            text = emailverify.getText();
            checkAndSend(text);
        });

        resendbutton.addActionListener(u -> {
            data.resendOtp();
            starttime = 30;
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (starttime > 0) {
                        resendbutton.setEnabled(false);
                        starttime--;
                        label.setText("The otp can be resend in " + starttime);
                    } else {
                        timer.stop();
                        resendbutton.setEnabled(true);
                    }
                }
            });
            timer.start();
            label.setBounds(200, 140, 300, 40);
            loginpanel.add(label);
        });

        check.addActionListener(o -> {
            int otp = Integer.parseInt(otptext.getText());
            if (data.checkLoginOtp(otp)) {
                System.out.println("SUCCESS");
            } else {
                System.out.println("Uns");
            }
        });

        signin.setBackground(Color.RED);
        signin.setBounds(150, 220, 100, 40);
        signin.setFont(new Font("System-ui", Font.BOLD, 15));
        signin.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        signin.setFocusable(false);
        signin.addActionListener(m -> {
            try {
                String name = nametext.getText();
                char[] pass = passtext.getPassword();
                boolean isUser = data.validateUser(name, pass);
                if (passtext.getPassword().length == 0 && nametext.getText().isEmpty()) {
                    passusermt = true;
                } else {
                    passusermt = false;
                }
                if (nametext.getText().isEmpty()) {
                    isuserempty = true;
                    System.out.println("hi");
                } else {
                    isuserempty = false;
                }

                if (passtext.getPassword().length == 0) {
                    ispassmt = true;
                } else {
                    ispassmt = false;
                }
                System.out.println(isuserempty);
                System.out.println(ispassmt);
                if (isUser) {
                    System.out.println("Successfully loged in");

                } else {
                    // loadpanel.setBounds(0, 0, 500, 500);
                    displaynotification(passusermt, ispassmt, isuserempty);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        loginpanel.setLayout(null);
        loginpanel.setPreferredSize(new Dimension(400, 300));
        loginpanel.add(useridlabel);
        loginpanel.add(nametext);
        loginpanel.add(passlabel);
        loginpanel.add(passtext);
        loginpanel.add(forgotpass);
        loginpanel.add(resendbutton);
        loginpanel.add(otptext);
        loginpanel.add(check);
        loginpanel.add(emaillabel);
        loginpanel.add(emailverify);
        loginpanel.add(verifyemail);

        textlabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        textlabel.setBackground(Color.RED);
        textlabel.setForeground(Color.WHITE);
        textlabel.setOpaque(true);
        textlabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLACK));
        textlabel.setBounds(0, 0, 100, 40);
        labellogin.setPreferredSize(new Dimension(100, 40));
        labellogin.setLayout(new BorderLayout());
        labellogin.add(textlabel, BorderLayout.CENTER);

        contentpanel.setLayout(new BoxLayout(contentpanel, BoxLayout.Y_AXIS));
        contentpanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.BLACK));
        contentpanel.add(logohold);
        contentpanel.add(labellogin);
        contentpanel.add(loginpanel);

        // Center the loadpanel
        loadpanel.setBackground(Color.GRAY);
        loadpanel.setSize(200, 100);
        loading.setFont(new Font("Arial", Font.BOLD, 20));
        loadpanel.add(loading);
        loadpanel.setLayout(new BorderLayout());
        loadpanel.setVisible(false);

        centerpanel.setLayout(new BorderLayout());
        centerpanel.add(contentpanel, BorderLayout.CENTER);

        // LayeredPane setup
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.setLayout(null);

        // Position panels in the layered pane
        contentpanel.setBounds(0, 0, 500, 500);
        loadpanel.setBounds(150, 200, 200, 100);  // Centered within the frame

        layeredPane.add(contentpanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(loadpanel, JLayeredPane.PALETTE_LAYER); // Load panel above content

        add(layeredPane, BorderLayout.CENTER);
    }

    public void checkAndSend(String text) {
        loadpanel.setVisible(true);
        emailverify.setEnabled(false);
        verifyemail.setEnabled(false);
        System.out.println("loading");
        repaint();
        revalidate();
        new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadpanel.setVisible(false);
                emailverify.setVisible(false);
                verifyemail.setVisible(false);
                otptext.setVisible(true);
                check.setVisible(true);
                repaint();
                revalidate();
            }
        }).start();
    }

    public void displaynotification(boolean passuser, boolean pass, boolean user) {
        notipanel.setVisible(true);
        nametext.setVisible(true);
        passtext.setVisible(true);
        notipanel.setBounds(20, 70, 400, 40);
        notipanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.RED));
        notipanel.setLayout(new BorderLayout());
        textandnot.setLayout(new FlowLayout());
        notipanel.setBackground(Color.RED);
        textandnot.setBackground(Color.RED);
        textandnot.setOpaque(true);
        notitext.setForeground(Color.WHITE);
        notitext.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        if (passuser) {
            notitext.setText("Username and Password cannot be empty");
        } else if (pass) {
            notitext.setText("Password cannot be empty");
        } else if (user) {
            notitext.setText("Username cannot be empty");
        } else {
            notitext.setText("User ID and Password don't match");
        }
        notitext.setVisible(true);
        textandnot.add(notitext);
        notipanel.add(textandnot, BorderLayout.CENTER);
        loginpanel.add(notipanel);
        notipanel.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setSize(500, 500);
        frame.add(new twilio());
        frame.setVisible(true);
    }
}
