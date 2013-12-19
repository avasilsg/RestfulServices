package assignment3.client.core;

import java.util.Vector;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.WebApplicationException;
import javax.swing.JLabel;

import assignment3.client.core.Interfaces.ClientInterface;
import assignment3.client.core.Logic.ClientNetworkController;
import assignment3.client.datahandling.AllocationObjectData;
import assignment3.client.datahandling.JSonParser;

import com.google.gson.Gson;
import javax.swing.JComboBox;
import java.awt.Font;

/**
 * @author asif this class is responsible for Project Allocations
 * 
 */
/**
 * @author asif
 * @author Stephan
 * 
 */
public class ProjectAllocation extends JFrame
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private JPanel            contentPane;
    public String             supervisorID;
    public String             approved;
    public String             stid;
    JSonParser                parser;
    public String             staff_id;
    
    /**
     * Gson object for parsing
     */
    Gson                      gson             = new Gson();
    
    /**
     * making the object of the class
     */
    AllocationObjectData      allocationObjectData;
    
    /**
     * binding the interface
     */
    ClientInterface           clientApplicationInterface;
    
    private JTextField        projectIDTextFiled;
    private JTextField        supervisorIDTextField;
    private JTextField        projectProfile;
    public String             allocation_str;
    private String            id;
    private String            supervisorIDdef;
    private JComboBox<String> comboBox;
    
    /**
     * creating the frame
     */
    public ProjectAllocation(String id, String supervisorID)
    {
        this.allocationObjectData = new AllocationObjectData();
        this.clientApplicationInterface = new ClientNetworkController();
        this.parser = new JSonParser();
        this.id = id;
        this.supervisorIDdef = supervisorID;
        /**
         * inits basic properties of the current frame window
         */
        initFrameProperties();
        
        JLabel lblProjectId = new JLabel("Project ID");
        lblProjectId.setBounds(10, 47, 87, 14);
        contentPane.add(lblProjectId);
        
        initTextFields();
        initLabels();
        
        JButton btnAllocate = new JButton("Assign Project");
        btnAllocate.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAllocate.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boolean flag = true;
                allocation_str = "allocated";
                if ((null == comboBox.getSelectedItem()) || (comboBox.getSelectedItem().toString().equals("")))
                {
                    JOptionPane.showMessageDialog(null, "Please select a student.");
                    flag = false;
                }
                if (supervisorIDTextField.getText().equals("") || (null == supervisorIDTextField.getText()))
                {
                    JOptionPane.showMessageDialog(null, "Please enter a supervisor.");
                    flag = false;
                }
                if (true == flag)
                {
                    Vector<Vector<String>> result = new Vector<Vector<String>>();
                    Vector<String> inputStrings = new Vector<String>();
                    inputStrings.add(projectIDTextFiled.getText());
                    inputStrings.add(supervisorIDTextField.getText());
                    inputStrings.add(comboBox.getSelectedItem().toString());
                    result.add(inputStrings);
                    String gson_str_obj = parser.gson.toJson(result);
                    projectAllocationExec(gson_str_obj);
                }
            }
        });
        btnAllocate.setBounds(99, 191, 243, 23);
        contentPane.add(btnAllocate);
        comboBox = new JComboBox<String>();
        comboBox.setBounds(186, 106, 243, 20);
        checkProjectStatuses();
        
    }
    
    private void initTextFields()
    {
        projectIDTextFiled = new JTextField();
        projectIDTextFiled.setDisabledTextColor(new Color(100, 149, 237));
        projectIDTextFiled.setEnabled(false);
        projectIDTextFiled.setEditable(false);
        projectIDTextFiled.setBackground(new Color(255, 204, 255));
        projectIDTextFiled.setBounds(186, 44, 243, 20);
        contentPane.add(projectIDTextFiled);
        projectIDTextFiled.setColumns(10);
        
        supervisorIDTextField = new JTextField();
        supervisorIDTextField.setDisabledTextColor(new Color(100, 149, 237));
        supervisorIDTextField.setBackground(new Color(255, 204, 255));
        supervisorIDTextField.setColumns(10);
        supervisorIDTextField.setBounds(186, 137, 243, 20);
        contentPane.add(supervisorIDTextField);
        
        projectProfile = new JTextField();
        projectProfile.setDisabledTextColor(new Color(100, 149, 237));
        projectProfile.setEditable(false);
        projectProfile.setEnabled(false);
        projectProfile.setBackground(new Color(255, 204, 255));
        projectProfile.setColumns(10);
        projectProfile.setBounds(186, 75, 243, 20);
        contentPane.add(projectProfile);
    }
    
    private void initLabels()
    {
        JLabel lblStudentid = new JLabel("Interested Students");
        lblStudentid.setBounds(10, 109, 170, 14);
        contentPane.add(lblStudentid);
        
        JLabel lblSupervisiorId = new JLabel("Supervisior ID");
        lblSupervisiorId.setBounds(10, 140, 87, 14);
        contentPane.add(lblSupervisiorId);
        
        JLabel lblNewLabel = new JLabel("Project Title");
        lblNewLabel.setBounds(10, 78, 68, 14);
        contentPane.add(lblNewLabel);
        
        JLabel logoLbl = new JLabel("Proposal Management System");
        logoLbl.setFont(new Font("Stencil", Font.PLAIN, 12));
        logoLbl.setBounds(218, 11, 211, 13);
        contentPane.add(logoLbl);
    }
    
    private void initFrameProperties()
    {
        setTitle("Project Status Information");
        setResizable(false);
        setBounds(100, 100, 445, 280);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }
    
    /**
     * @param project_id
     *            for getting the project lists
     * @return returining the String as gson format
     */
    public String checkProjectStatuses()
    {
        try
        {
            
            String response = this.clientApplicationInterface.checkProjectStatuses(id);
            System.out.println(response);
            // this model will go for rendering the table
            Vector<Vector<String>> model = parser.gsonToVector(response);
            renderData(model);
            return model.toString();
            
        }
        catch (WebApplicationException ex)
        {
            throw new WebApplicationException("Connection Refuse/Faculty-Id not found");
        }
    }
    
    /**
     * @param clientApplicationInterface
     *            binding the interface
     */
    public void setClientApplicationInterface(ClientInterface clientApplicationInterface)
    {
        this.clientApplicationInterface = clientApplicationInterface;
        
    }
    
    /**
     * @param model
     *            data model of the table
     */
    public void renderData(Vector<Vector<String>> model)
    {
        for (Vector<String> current : model)
        {
            this.projectIDTextFiled.setText(id);
            this.projectProfile.setText(current.get(0));
            this.supervisorIDTextField.setText(supervisorIDdef);
            
            if (1 < current.size())
            {
                String[] interestedUsers = new String[current.size()];
                for (int i = 1; i < current.size(); i++)
                {
                    interestedUsers[i] = current.get(i);
                }
                comboBox.setModel(new DefaultComboBoxModel<String>(interestedUsers));
                contentPane.add(comboBox);
            }
        }
    }
    
    /**
     * @param gson_input_string
     *            sending data to server
     * @return success or failure
     */
    public String projectAllocationExec(String gson_input_string)
    {
        if (!this.allocation_str.equals("allocated"))
        {
            throw new WebApplicationException("error while allocating the prject");
        }
        this.clientApplicationInterface.projectAllocation(gson_input_string);
        return "success";
        
    }
}
