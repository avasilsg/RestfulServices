package assignment3.client.core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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
import assignment3.client.datahandling.JSonParser;
import java.awt.Font;

/**
 * 
 * @author Stephan
 * @author Asif
 * 
 */
public class UserProfile extends JFrame
{
    
    private static final long serialVersionUID = 1L;
    
    private JPanel            contentPane;
    private ClientInterface   clientInterface;
    
    public String             updateStatus;
    private JButton           btnUpdateProfile;
    private JSonParser        parser;
    private JTextField        RoleTextField;
    private JTextField        IDField;
    private JTextField        NameField;
    private JTextField        AddressTextField;
    private JTextField        researchTextField;
    private JTextField        ProposedTopicsTextField;
    private JLabel            lblProposedTopics;
    private JLabel            BrandLabel;
    
    public UserProfile()
    {
        
        init();
        
        clientInterface = new ClientNetworkController();
        parser = new JSonParser();
        
        initLabels();
        
        initTextFields();
        
        loadUserData();
        
    }
    
    private void initLabels()
    {
        JLabel label_1 = new JLabel("ID");
        label_1.setEnabled(false);
        label_1.setBounds(20, 97, 53, 14);
        contentPane.add(label_1);
        
        JLabel lblRole = new JLabel("Role");
        lblRole.setEnabled(false);
        lblRole.setBounds(20, 62, 53, 14);
        contentPane.add(lblRole);
        
        JLabel lblName = new JLabel("Name");
        lblName.setEnabled(false);
        lblName.setBounds(20, 132, 53, 14);
        contentPane.add(lblName);
        
        JLabel lblAddress = new JLabel("Address");
        lblAddress.setEnabled(false);
        lblAddress.setBounds(223, 97, 53, 14);
        contentPane.add(lblAddress);
        
        JLabel lblResearchInterest = new JLabel("Research Interest");
        lblResearchInterest.setEnabled(false);
        lblResearchInterest.setBounds(222, 63, 128, 14);
        contentPane.add(lblResearchInterest);
        
        lblProposedTopics = new JLabel("Proposed Topics:");
        lblProposedTopics.setEnabled(false);
        lblProposedTopics.setBounds(20, 171, 727, 14);
        contentPane.add(lblProposedTopics);
    }
    
    private void initTextFields()
    {
        AddressTextField = new JTextField();
        AddressTextField.setDisabledTextColor(new Color(100, 149, 237));
        AddressTextField.setColumns(10);
        AddressTextField.setBounds(360, 95, 387, 20);
        contentPane.add(AddressTextField);
        
        IDField = new JTextField();
        IDField.setDisabledTextColor(new Color(100, 149, 237));
        IDField.setEnabled(false);
        IDField.setColumns(10);
        IDField.setBounds(80, 95, 107, 20);
        contentPane.add(IDField);
        
        NameField = new JTextField();
        NameField.setDisabledTextColor(new Color(100, 149, 237));
        NameField.setColumns(10);
        NameField.setBounds(80, 130, 270, 20);
        contentPane.add(NameField);
        
        RoleTextField = new JTextField();
        RoleTextField.setDisabledTextColor(new Color(100, 149, 237));
        RoleTextField.setEnabled(false);
        RoleTextField.setEditable(false);
        RoleTextField.setColumns(10);
        RoleTextField.setBounds(80, 60, 107, 20);
        contentPane.add(RoleTextField);
        
        researchTextField = new JTextField();
        researchTextField.setDisabledTextColor(new Color(100, 149, 237));
        researchTextField.setEnabled(false);
        researchTextField.setEditable(false);
        researchTextField.setColumns(10);
        researchTextField.setBounds(360, 60, 387, 20);
        contentPane.add(researchTextField);
        
        ProposedTopicsTextField = new JTextField();
        ProposedTopicsTextField.setDisabledTextColor(new Color(100, 149, 237));
        ProposedTopicsTextField.setEditable(false);
        ProposedTopicsTextField.setEnabled(false);
        ProposedTopicsTextField.setColumns(10);
        ProposedTopicsTextField.setBounds(20, 199, 727, 61);
        contentPane.add(ProposedTopicsTextField);
        
        BrandLabel = new JLabel("Proposal Management System");
        BrandLabel.setFont(new Font("Stencil", Font.PLAIN, 12));
        BrandLabel.setBounds(10, 11, 431, 13);
        contentPane.add(BrandLabel);
    }
    
    private void init()
    {
        setResizable(false);
        
        setTitle("User Profile");
        setSize(768, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }
    
    /**
     * @param clientInterface - setting ClientApplicationInterface
     */
    public void setClientApplicationInterface(ClientInterface clientInterface)
    {
        this.clientInterface = clientInterface;
    }
    
    /**
     * @param facult_id
     *            for query the data from the database
     * @return
     */
    public String loadUserData()
    {
        try
        {
            String response = this.clientInterface.loadFacultyDataView();
            if (null == response)
            {
                throw new NullPointerException("Invalid data");
            }
            
            Vector<Vector<String>> model = parser.gsonToVector(response);
            String topicsString = "";
            for (Vector<String> currentUser : model)
            {
                this.IDField.setText(currentUser.get(0));
                this.NameField.setText(currentUser.get(1));
                this.RoleTextField.setText(currentUser.get(2));
                this.AddressTextField.setText(currentUser.get(3));
                if (RoleTextField.getText().equals("Faculty"))
                {
                    processFacultyAndStaffData(currentUser);
                }
                else
                {
                    processStudentData();
                }
                for (int i = 4; i < currentUser.size(); i++)
                {
                    topicsString += currentUser.get(i) + "\n";
                }
                this.ProposedTopicsTextField.setText(topicsString);
            }
            btnUpdateProfile.setEnabled(true);
            return model.toString();
        }
        catch (WebApplicationException ex)
        {
            throw new WebApplicationException("Connection Refuse/Faculty-Id not found");
        }
    }

    private void processStudentData()
    {
        this.NameField.setEditable(false);
        btnUpdateProfile = new JButton("Accept Proposal");
        btnUpdateProfile.setForeground(new Color(51, 153, 204));
        AddressTextField.setEditable(false);
        researchTextField.setEditable(false);
        AddressTextField.setEditable(false);
        researchTextField.setEditable(false);
        btnUpdateProfile.setEnabled(false);
        btnUpdateProfile.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String compareStatusString = parser.gson.fromJson(clientInterface.checkForAssignTopics(IDField.getText()), String.class);

                if ("true".equals(compareStatusString))
                {
                    JOptionPane.showMessageDialog(null, "The user has already accepted the topic.");
                }
                else
                {
                    clientInterface.projectAcceptance(IDField.getText());
                }
            }
        });
        
        btnUpdateProfile.setBounds(619, 11, 128, 22);
        contentPane.add(btnUpdateProfile);
    }

    private void processFacultyAndStaffData(Vector<String> currentUser)
    {
        btnUpdateProfile = new JButton("Update Profile");
        btnUpdateProfile.setForeground(new Color(51, 153, 204));
        this.researchTextField.setText(currentUser.get(4));
        btnUpdateProfile.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                updateStatus = "success";
                updateFacultyProfile("");
            }
        });
        btnUpdateProfile.setBounds(619, 11, 128, 22);
        contentPane.add(btnUpdateProfile);
    }
    
    /**
     * @param gson_string_table - data of the table in gson string format
     */
    public void updateFacultyProfile(String gson_string_table)
    {
        
        if (!this.updateStatus.equals("success"))
        {
            throw new WebApplicationException("can not update the DB");
        }
        
        try
        {
            Vector<Vector<String>> data = new Vector<Vector<String>>();
            Vector<String> userStrings = new Vector<String>();
            userStrings.add(this.IDField.getText());
            userStrings.add(this.AddressTextField.getText());
            userStrings.add(this.NameField.getText());
            data.add(userStrings);
            String gson_string_for_updating_table = parser.gson.toJson(data);
            gson_string_table = gson_string_for_updating_table;
            
            this.clientInterface.updateFacultyProfile(gson_string_table);
            JOptionPane.showMessageDialog(null, "Updated");
        }
        catch (WebApplicationException ex)
        {
            JOptionPane.showMessageDialog(null, "Error while updating profile");
        }
        
    }
}
