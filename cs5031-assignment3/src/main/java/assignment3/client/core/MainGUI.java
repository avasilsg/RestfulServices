package assignment3.client.core;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;

/**
 * @author asif this GUI is acting as main handler from which every GUI is being
 *         called
 * @author Stefan
 * 
 */
public class MainGUI extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private JPanel            contentPane;
    private JMenu             proposeProjectForm;
    private JMenu             loginForm;
    private JMenuItem         viewProjectForm;
    private JMenu             userProfileForm;
    private JLabel            lblProposalManagementSystem;
    
    /**
     * Create the frame.
     */
    public MainGUI()
    {
        
        init();
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        loginForm = new JMenu("Login");
        menuBar.add(loginForm);
        
        JMenuItem mntmUserpassword = new JMenuItem("User/Password");
        mntmUserpassword.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                LoginForm lf;
                lf = new LoginForm();
                lf.setLocation(500, 160);
                lf.setVisible(true);
                enableMenus();
            }
        });
        loginForm.add(mntmUserpassword);
        
        proposeProjectForm = new JMenu("Projects");
        menuBar.add(proposeProjectForm);
        JMenuItem mntmPropose = new JMenuItem("Propose");
        mntmPropose.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                
                ProposeProjects pP = new ProposeProjects();
                pP.setLocation(500, 160);
                pP.setVisible(true);
                
            }
        });
        proposeProjectForm.add(mntmPropose);
        
        viewProjectForm = new JMenuItem("View");
        viewProjectForm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ViewProjects vP = new ViewProjects();
                vP.setLocation(500, 160);
                vP.setVisible(true);
                
            }
        });
        proposeProjectForm.add(viewProjectForm);
        
        userProfileForm = new JMenu("Profiles");
        menuBar.add(userProfileForm);
        
        JMenuItem mntmFaculty = new JMenuItem("View User Profile");
        mntmFaculty.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                UserProfile fpL = new UserProfile();
                fpL.setLocation(500, 160);
                fpL.setVisible(true);
            }
        });
        userProfileForm.add(mntmFaculty);
        
        DisableMenus();
    }
    
    private void init()
    {
        setAlwaysOnTop(true);
        setResizable(false);
        setTitle("Project Proposal Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 447, 283);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        lblProposalManagementSystem = new JLabel("Proposal Management System");
        lblProposalManagementSystem.setFont(new Font("Stencil", Font.PLAIN, 12));
        contentPane.add(lblProposalManagementSystem, BorderLayout.SOUTH);
    }
    
    /**
     * Disable Menus
     */
    public void DisableMenus()
    {
        proposeProjectForm.setEnabled(false);
        
        viewProjectForm.setEnabled(false);
        
        userProfileForm.setEnabled(false);
    }
    
    /**
     * Enable Menus
     */
    public void enableMenus()
    {
        proposeProjectForm.setEnabled(true);
        
        viewProjectForm.setEnabled(true);
        
        userProfileForm.setEnabled(true);
    }
    
}
