package assignment3.client.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.WebApplicationException;

import assignment3.client.core.Interfaces.ClientInterface;
import assignment3.client.core.Logic.ClientNetworkController;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JPasswordField;

import java.awt.Font;

/**
 * @author asif this class is handling the Login
 *
 */
public class LoginForm extends JFrame
{
    
    /**
     * 
     */
    private static final long serialVersionUID  = 1L;
    private final String      loginPathRosource = "serverResp";
    private final JPanel      contentPane;
    private final JTextField  username_txt;
    private JPasswordField    passwordField;
    
    private final JButton     btn_login;
    
    public String             username;
    public String             password;
    public String             hostIP;
    public String             port;
    public String             authenticated_string;
    public boolean            menu_enable_flag  = false;
    
    ClientInterface           clientInterface;
    private final JLabel      lbl_new_label;
    private JLabel            BrandLabel;
    
    public void setControllerInterface(ClientInterface nCI)
    {
        this.clientInterface = nCI;
        
    }
    
    /**
     * creating the Login Class
     * 
     * @throws IOException
     */
    public LoginForm()
    {
        initLoginWindow();
        
        this.clientInterface = new ClientNetworkController();
        username = new String();
        hostIP = new String();
        port = new String();
        authenticated_string = new String();
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        username_txt = new JTextField();
        username_txt.setBackground(new Color(255, 250, 205));
        username_txt.setToolTipText("Enter User Name");
        username_txt.setBounds(469, 180, 132, 26);
        
        contentPane.add(username_txt);
        username_txt.setColumns(10);
        
        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(255, 250, 205));
        passwordField.setBounds(469, 217, 132, 26);
        passwordField.setToolTipText("Enter Password");
        passwordField.setColumns(15);
        contentPane.add(passwordField);
        
        JLabel lblUserName = new JLabel("User Name");
        lblUserName.setBounds(393, 186, 66, 14);
        contentPane.add(lblUserName);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(393, 223, 66, 14);
        contentPane.add(lblPassword);
        
        btn_login = new JButton("Login");
        btn_login.setForeground(new Color(210, 105, 30));
        btn_login.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                username = username_txt.getText();
                if (username.length() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Please Enter User Name");
                    username_txt.requestFocus();
                    return;
                }
                password = new String(passwordField.getPassword());
                if (password.length() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Please Enter Password");
                    passwordField.requestFocus();
                    return;
                }
                hostIP = "localhost";
                port = "9998";
                authenticated_string = "authenticated";
                
                try
                {
                    login();
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(null, "Invalid user name or password.");
                }
                
            }
        });
        btn_login.setBounds(469, 254, 132, 23);
        contentPane.add(btn_login);
        
        lbl_new_label = new JLabel("");
        lbl_new_label.setBounds(231, 272, 132, 14);
        contentPane.add(lbl_new_label);
        
        BrandLabel = new JLabel("Proposal Management System");
        BrandLabel.setFont(new Font("Stencil", Font.PLAIN, 12));
        BrandLabel.setBounds(10, 11, 431, 13);
        contentPane.add(BrandLabel);
    }
    
    private void initLoginWindow()
    {
        setResizable(false);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBounds(100, 100, 630, 330);
        
        Image customIcon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Stephan\\Desktop\\16156phoenix.png");
        setIconImage(customIcon);
    }
    
    /* This method is being tested in the Test Client class */
    /**
     * @throws WebApplicationException
     *             logining to application
     */
    public void login() throws WebApplicationException
    {
        if (!authenticated_string.equals("authenticated"))
        {
            throw new WebApplicationException("The problem with user name or password.");
        }
        
        try
        {
            
            String login_response = this.clientInterface.login(this.username, this.password, this.hostIP, this.port, this.loginPathRosource);
            
            if (false == login_response.equals("authenticated"))
            {
                
                lbl_new_label.setText("Un-Authorized");
                System.out.println("coming here");
                throw new WebApplicationException("no such username and password-2");
            }
            else
            {
                this.dispose();
            }
            
        }
        catch (Exception ex)
        {
            lbl_new_label.setText("Un-Authorized");
            throw new WebApplicationException("Invalid resource path");
        }
        
    }
}
