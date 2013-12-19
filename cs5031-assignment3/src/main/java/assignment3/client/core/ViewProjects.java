package assignment3.client.core;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import assignment3.client.core.Interfaces.ClientInterface;
import assignment3.client.core.Logic.ClientNetworkController;
import assignment3.client.datahandling.JSonParser;

import java.awt.Color;

import javax.swing.JTextField;
import javax.ws.rs.WebApplicationException;

import java.awt.SystemColor;

import javax.swing.UIManager;

import java.awt.ComponentOrientation;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * @author Stefan
 * @author asif this class is responsible for handling the projects views
 * 
 */
public class ViewProjects extends JFrame
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private JPanel            contentPane;
    JButton                   expressInterestBtn;
    
    /**
     * Converting To and From to Gson API
     */
    JSonParser                parser;
    
    /**
     * binding the class
     */
    ClientInterface           clientNetworkController;
    private JTextField        topicIDfield;
    
    public String             facultyID;
    public String             project;
    public String             proposed_by;
    public String             express_interest_str;
    public String             gson_server_input_stid_str;
    public String             gson_server_input_pid_str;
    public String             gson_combine_input_server;
    private JTextField        ID;
    private JTextField        roleField;
    private JLabel            LogoLbl;
    private JTextField        advanceLbl;
    private JTextField        descrLbl;
    
    private JButton           DetailBtn;
    private JTextField        messageField;
    private JTextArea         MessageHistoryArea;
    
    /**
     * Create the frame.
     */
    public ViewProjects()
    {
        init();
        
        JButton viewProjectsBtn = new JButton("View Projects");
        viewProjectsBtn.setForeground(SystemColor.inactiveCaptionText);
        viewProjectsBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loadDataToView();
            }
        });
        viewProjectsBtn.setBounds(227, 4, 264, 23);
        contentPane.add(viewProjectsBtn);
        
        initTextFields();
        
        initLabels();
        
        DetailBtn = new JButton("Details");
        DetailBtn.setForeground(new Color(102, 153, 204));
        DetailBtn.setBounds(227, 34, 92, 23);
        DetailBtn.setForeground(new Color(102, 153, 204));
        DetailBtn.setBounds(227, 34, 92, 23);
        DetailBtn.setEnabled(false);
        contentPane.add(DetailBtn);
        
        messageField = new JTextField();
        messageField.setColumns(10);
        messageField.setBackground(new Color(255, 239, 213));
        messageField.setBounds(401, 289, 413, 20);
        contentPane.add(messageField);
        
        JButton button = new JButton("Submit");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                if ((null != messageField.getText()) && (null != topicIDfield.getText()))
                {
                    if ((false == messageField.getText().equals("")) && (false == topicIDfield.getText().equals("")))
                    {
                        String messages = clientNetworkController.updateForum(topicIDfield.getText()) + "\n" + ID.getText() + ":" + messageField.getText();
                        MessageHistoryArea.setText(messages);
                        clientNetworkController.answerQuestion(topicIDfield.getText(), messages);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Please enter Topic ID or message");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please enter Topic ID or message");
                }
                
            }
        });
        button.setForeground(new Color(255, 69, 0));
        button.setBounds(541, 421, 135, 23);
        contentPane.add(button);
        
        JLabel messageLbl = new JLabel("Message");
        messageLbl.setBounds(401, 269, 67, 14);
        contentPane.add(messageLbl);
        
        JLabel lblTopicHistory = new JLabel("Topic History");
        lblTopicHistory.setBounds(401, 320, 90, 14);
        contentPane.add(lblTopicHistory);
        
        MessageHistoryArea = new JTextArea();
        MessageHistoryArea.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        MessageHistoryArea.setBounds(401, 341, 413, 69);
        contentPane.add(MessageHistoryArea);
        
        this.clientNetworkController = new ClientNetworkController();
        parser = new JSonParser();
        initInformationRequest();
    }
    
    private void initTextFields()
    {
        JLabel TopicIDLbl = new JLabel("Topic ID");
        TopicIDLbl.setBounds(27, 38, 67, 14);
        contentPane.add(TopicIDLbl);
        
        advanceLbl = new JTextField();
        advanceLbl.setSelectedTextColor(new Color(51, 102, 204));
        advanceLbl.setEnabled(false);
        advanceLbl.setDisabledTextColor(Color.BLACK);
        advanceLbl.setColumns(10);
        advanceLbl.setBounds(27, 289, 358, 20);
        contentPane.add(advanceLbl);
        
        topicIDfield = new JTextField();
        topicIDfield.setDisabledTextColor(UIManager.getColor("Tree.textForeground"));
        topicIDfield.setSelectedTextColor(new Color(51, 102, 204));
        topicIDfield.setBounds(100, 35, 107, 20);
        contentPane.add(topicIDfield);
        topicIDfield.setColumns(10);
        
        ID = new JTextField();
        ID.setDisabledTextColor(new Color(100, 149, 237));
        ID.setSelectionColor(new Color(51, 102, 204));
        ID.setEditable(false);
        ID.setEnabled(false);
        ID.setColumns(10);
        ID.setBounds(384, 35, 107, 20);
        contentPane.add(ID);
        
        roleField = new JTextField();
        roleField.setDisabledTextColor(new Color(100, 149, 237));
        roleField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        roleField.setSelectedTextColor(new Color(51, 102, 204));
        roleField.setSelectionColor(new Color(51, 102, 204));
        roleField.setEditable(false);
        roleField.setEnabled(false);
        roleField.setColumns(10);
        roleField.setBounds(100, 7, 107, 20);
        contentPane.add(roleField);
        
        descrLbl = new JTextField();
        descrLbl.setSelectionColor(new Color(51, 102, 204));
        descrLbl.setEnabled(false);
        descrLbl.setEditable(false);
        descrLbl.setDisabledTextColor(new Color(100, 149, 237));
        descrLbl.setColumns(10);
        descrLbl.setBounds(27, 341, 358, 69);
        contentPane.add(descrLbl);
    }
    
    private void initLabels()
    {
        JLabel AdvancedLbl = new JLabel("Previous Experince Required");
        AdvancedLbl.setBounds(27, 269, 180, 14);
        contentPane.add(AdvancedLbl);
        
        LogoLbl = new JLabel("Proposal Management System");
        LogoLbl.setFont(new Font("Stencil", Font.PLAIN, 12));
        LogoLbl.setBounds(606, 12, 208, 13);
        contentPane.add(LogoLbl);
        
        JLabel descriptionLbl = new JLabel("Additional Information");
        descriptionLbl.setBounds(27, 320, 153, 14);
        contentPane.add(descriptionLbl);
        
        JLabel lblStudentid = new JLabel("User ID");
        lblStudentid.setBounds(329, 38, 43, 14);
        contentPane.add(lblStudentid);
    }
    
    private void init()
    {
        setResizable(false);
        setTitle("View Projects");
        setBounds(100, 100, 830, 484);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JLabel lblSelectRole = new JLabel("Select Role");
        lblSelectRole.setBounds(27, 10, 67, 14);
        contentPane.add(lblSelectRole);
    }
    
    private void initInformationRequest()
    {
        String response = this.clientNetworkController.proposeProjectInit();
        System.out.println(response);
        JSonParser parser = new JSonParser();
        Vector<Vector<String>> model = parser.gsonToVector(response);
        for (Vector<String> info : model)
        {
            this.ID.setText(info.get(0));
            this.roleField.setText(info.get(2));
        }
        
        if (roleField.getText().equals("Faculty"))
        {
            showFacultyAndStaffProfile();
        }
        else
        {
            showStudentProfile();
        }
    }
    
    private void showStudentProfile()
    {
        enableDetails();
        expressInterestBtn = new JButton("Interested");
        expressInterestBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                String compareStatusString = parser.gson.fromJson(clientNetworkController.checkForAssignTopics(ID.getText()), String.class);
                
                if ("true".equals(compareStatusString))
                {
                    JOptionPane.showMessageDialog(null, "The user has already accepted the topic.");
                }
                else
                {
                    express_interest_str = "5008,25";
                    gson_server_input_pid_str = ID.getText();
                    try
                    {
                        if (null == topicIDfield.getText() || (topicIDfield.getText().equals("")))
                        {
                            JOptionPane.showMessageDialog(null, "Please choose Topic ID.");
                        }
                        else
                        {
                            String result = topicIDfield.getText() + ":" + ID.getText();
                            gson_combine_input_server = result;
                            exPressInterests();
                        }
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Illegal ID/P-ID");
                        return;
                    }
                }
            }
        });
        expressInterestBtn.setEnabled(true);
        expressInterestBtn.setForeground(new Color(102, 153, 204));
        expressInterestBtn.setBounds(502, 32, 92, 23);
        contentPane.add(expressInterestBtn);
    }
    
    private void showFacultyAndStaffProfile()
    {
        expressInterestBtn = new JButton("Assign");
        expressInterestBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if ((null == topicIDfield.getText()) || ("".equals(topicIDfield.getText())))
                {
                    JOptionPane.showMessageDialog(null, "Please select a topic ID.");
                }
                else
                {
                    ProjectAllocation projectAllocation = new ProjectAllocation(topicIDfield.getText(), ID.getText());
                    projectAllocation.setLocation(500, 160);
                    projectAllocation.setVisible(true);
                }
            }
        });
        expressInterestBtn.setForeground(new Color(102, 153, 204));
        expressInterestBtn.setBounds(502, 32, 92, 23);
        contentPane.add(expressInterestBtn);
        expressInterestBtn.setEnabled(true);
        enableDetails();
    }
    
    private void enableDetails()
    {
        DetailBtn.setEnabled(true);
        DetailBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                if (false == "".equals(topicIDfield.getText()))
                {
                    String response = clientNetworkController.getTopicDetails(topicIDfield.getText());
                    Vector<Vector<String>> parseVector = parser.gsonToVector(response);
                    if (1 == parseVector.size())
                    {
                        if ("false".equals(parseVector.get(0).get(1)))
                            advanceLbl.setText("Not required.");
                        else
                            advanceLbl.setText("Required.");
                        descrLbl.setText(parseVector.get(0).get(0));
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid topic ID.");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please select a topic ID or enter a valid one.");
                }
            }
        });
    }
    
    /**
     * @param clientInterface
     *            - binding the interface
     */
    public void setClientApplicationInterface(ClientInterface clientInterface)
    {
        this.clientNetworkController = clientInterface;
    }
    
    /**
     * @return gson format string
     */
    public String loadDataToView()
    {
        if (this.roleField.getText().toString().equals("Student"))
        {
            
            String gson_str_student = this.parser.gson.toJson(this.roleField.getText());
            
            /* Calling the Mock Server Object */
            String gson_response_string = this.clientNetworkController.loadDataToView(gson_str_student);
            
            Vector<String> header = new Vector<String>();
            header.add("Topic ID");
            header.add("Title");
            header.add("Proposed By");
            
            this.renderingDataToTable(parser.gsonToVector(gson_response_string), header);
            return gson_response_string.toString();
            
        }
        else if (this.roleField.getText().toString().equals("Faculty"))
        {
            
            String gson_str_faculty = parser.gson.toJson(this.roleField.getText().toString());
            
            /* Calling the Mock Server Object */
            String gson_response_string = this.clientNetworkController.loadDataToView(gson_str_faculty);
            
            Vector<String> header = new Vector<String>();
            header.add("Topic ID");
            header.add("Title");
            header.add("Proposed By");
            
            this.renderingDataToTable(parser.gsonToVector(gson_response_string), header);
            return gson_response_string.toString();
            
        }
        
        return "{student_id:123,faculty_id=580,project=Web Application, proposed_by=faculty}";
    }
    
    /**
     * @param model
     *            for data model
     * @param header
     *            columns information
     */
    public void renderingDataToTable(Vector<Vector<String>> model, Vector<String> header)
    {
        
        DefaultTableModel dtM = new DefaultTableModel(model, header);
        JTable project_table;
        project_table = new JTable(dtM);
        contentPane.add(project_table);
        project_table.setPreferredSize(new Dimension(780, 400));
        JScrollPane scrollPane = new JScrollPane(project_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(4, 65, 810, 200);
        getContentPane().add(scrollPane);
        project_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        project_table.setEnabled(false);
    }
    
    /**
     * @throws WebApplicationException
     *             express the interests in the project
     */
    public void exPressInterests() throws WebApplicationException
    {
        
        if (!this.express_interest_str.equals("5008,25"))
        {
            throw new WebApplicationException("Connection Refused/Std-id do not matches");
        }
        
        try
        {
            this.clientNetworkController.expressInterests(this.gson_combine_input_server);
            JOptionPane.showMessageDialog(null, "Logged");
        }
        catch (WebApplicationException ex)
        {
            JOptionPane.showMessageDialog(null, "Connection Refused/Std-id do not matches");
            throw new WebApplicationException("Connection Refused/Std-id do not matches-Exception");
            
        }
        
    }
}
