package assignment3.client.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import assignment3.client.datahandling.ProposeobjectData;

import com.google.gson.Gson;

import java.awt.Color;
import java.util.Vector;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * @Stephan
 * @author asif this class is handling the propose project functionality
 * 
 */
public class ProposeProjects extends JFrame
{
    
    /**
     * 
     */
    private static final long serialVersionUID     = 1L;
    
    private final JPanel      contentPane;
    private JTextField        projectDescription;
    private JTextField        projectAims;
    
    private JComboBox<String> typeOfProject;
    
    JCheckBox                 previousExpCheckBox;
    
    String                    json_propose_project ;
    public String             returnStatus         ;
    
    /**
     * Gson parser object
     */
    private Gson              gson;
    private ClientInterface   clientApplicationInterface;
    private JTextField        ID;
    private JTextField        proposedByTxt;
    private JLabel            BrandLabel;

    private JComboBox<String> fieldBox;

    private JTextArea         referenceArea;
    
    
    /**
     * Create the frame
     */
    public ProposeProjects()
    {
        returnStatus = new String();
        setResizable(false);
        setTitle("Propose Project");
        
        setBounds(0, 275, 506, 497);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        initLabels();
        initTextFields();
        
        typeOfProject = new JComboBox<String>();
        typeOfProject.setBounds(33, 360, 424, 20);
        typeOfProject.setModel(new DefaultComboBoxModel<String>(new String[] { "Select project", "Research Paper", "Dissertation", "Prototype",
                "Working System" }));
        contentPane.add(typeOfProject);
        
        previousExpCheckBox = new JCheckBox("Yes");
        previousExpCheckBox.setBounds(249, 389, 100, 23);
        contentPane.add(previousExpCheckBox);
        
        JButton proposeProjectBtn = new JButton("Propose");
        proposeProjectBtn.setForeground(new Color(102, 153, 204));
        proposeProjectBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                returnStatus = "nsuccess";
                proposeNewProject();
                
            }
        });
        proposeProjectBtn.setBounds(176, 436, 159, 23);
        contentPane.add(proposeProjectBtn);
        
        referenceArea = new JTextArea();
        referenceArea.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        referenceArea.setBounds(126, 201, 331, 121);
        contentPane.add(referenceArea);
        
        this.clientApplicationInterface = new ClientNetworkController();
        gson                            = new Gson();
        initInformationRequest();
        
    }
    
    private void initInformationRequest()
    {
        String response = this.clientApplicationInterface.proposeProjectInit();
        System.out.println(response);
        JSonParser parser = new JSonParser();
        Vector<Vector<String>> model = parser.gsonToVector(response);
        for (Vector<String> info : model)
        {
            this.ID.setText(info.get(0));
            this.proposedByTxt.setText(info.get(1));
        }
    }
    
    private void initTextFields()
    {
        ID = new JTextField();
        ID.setDisabledTextColor(new Color(100, 149, 237));
        ID.setEnabled(false);
        ID.setEditable(false);
        ID.setColumns(10);
        ID.setBackground(new Color(230, 230, 250));
        ID.setBounds(126, 79, 331, 20);
        contentPane.add(ID);
        
        projectDescription = new JTextField();
        projectDescription.setBackground(new Color(230, 230, 250));
        projectDescription.setBounds(126, 109, 331, 20);
        contentPane.add(projectDescription);
        projectDescription.setColumns(10);
        
        projectAims = new JTextField();
        projectAims.setBackground(new Color(230, 230, 250));
        projectAims.setBounds(126, 139, 331, 20);
        contentPane.add(projectAims);
        projectAims.setColumns(10);
        
        proposedByTxt = new JTextField();
        proposedByTxt.setDisabledTextColor(new Color(100, 149, 237));
        proposedByTxt.setEditable(false);
        proposedByTxt.setEnabled(false);
        proposedByTxt.setColumns(10);
        proposedByTxt.setBackground(new Color(230, 230, 250));
        proposedByTxt.setBounds(125, 49, 331, 20);
        contentPane.add(proposedByTxt);
    }
    
    private void initLabels()
    {
        JLabel lblProposedBy = new JLabel("Proposed By");
        lblProposedBy.setBounds(10, 51, 74, 14);
        contentPane.add(lblProposedBy);
        
        JLabel lblStudentid = new JLabel("ID");
        lblStudentid.setBounds(10, 81, 74, 14);
        contentPane.add(lblStudentid);
        
        JLabel lblProjectDescription = new JLabel("Description");
        lblProjectDescription.setBounds(10, 111, 91, 14);
        contentPane.add(lblProjectDescription);
        
        JLabel lblReferences = new JLabel("References");
        lblReferences.setBounds(10, 173, 74, 14);
        contentPane.add(lblReferences);
        
        JLabel lblDeliverables = new JLabel("Type of Project");
        lblDeliverables.setBounds(33, 335, 424, 14);
        contentPane.add(lblDeliverables);
        
        JLabel lblProjectAims = new JLabel("Project Aims");
        lblProjectAims.setBounds(10, 143, 74, 14);
        contentPane.add(lblProjectAims);
        
        JLabel lblPreviousExperienceRequired = new JLabel("Previous Experience Required");
        lblPreviousExperienceRequired.setBounds(33, 393, 197, 14);
        contentPane.add(lblPreviousExperienceRequired);
        
        BrandLabel = new JLabel("Proposal Management System");
        BrandLabel.setFont(new Font("Stencil", Font.PLAIN, 12));
        BrandLabel.setBounds(10, 11, 431, 13);
        contentPane.add(BrandLabel);
        
        fieldBox = new JComboBox<String>();
        fieldBox.setBounds(126, 170, 331, 20);
        fieldBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Select field", "Artificial Inteligence", "Computer Architectures", "Software Engineering",
                "High Performance Computing" }));
        contentPane.add(fieldBox);
    }
    
    /**
     * @return sucess or failure method is responsible for proposing the new
     *         projects getting data from the GUI, storing into the Object and
     *         pushing data towards the server
     */
    public String proposeNewProject()
    {
        
        if (!this.returnStatus.equals("success"))
        {
            boolean flag = false;
            
            if (typeOfProject.getSelectedItem().toString().equals("Select Project"))
            {
                JOptionPane.showMessageDialog(null, "Please select the proposer");
                flag = true;
            }
            
            if (fieldBox.getSelectedItem().toString().equals("Select field"))
            {
                JOptionPane.showMessageDialog(null, "Please select the proposer");
                flag = true;
            }
            
            if ((null == referenceArea.getText()) || ("".equals(referenceArea.getText())))
            {
                JOptionPane.showMessageDialog(null, "Please enter references.");
                flag = true;
            }
            if ((null == projectAims.getText()) || ("".equals(projectAims.getText())))
            {
                JOptionPane.showMessageDialog(null, "Please enter a project aims.");
                flag = true;
            }
            if ((null == projectDescription.getText()) || ("".equals(projectDescription.getText())))
            {
                JOptionPane.showMessageDialog(null, "Please enter a project description.");
                flag = true;
            }
            if (false == flag)
            {
                try
                {
                    ProposeobjectData proposeobjectData = new ProposeobjectData();
                    proposeobjectData.setID(ID.getText());
                    proposeobjectData.setTxt_project_description(projectDescription.getText());
                    proposeobjectData.setTxt_references(referenceArea.getText() + ":" + fieldBox.getSelectedItem());
                    proposeobjectData.setTxt_project_aims(projectAims.getText());
                    proposeobjectData.setProposedBy(ID.getText());
                    
                    if (typeOfProject.getSelectedItem().toString().equals("Select Project"))
                    {
                        JOptionPane.showMessageDialog(null, "Please select the proposer");
                        return json_propose_project;
                    }
                    proposeobjectData.setDeliverables(typeOfProject.getSelectedItem().toString());
                    String prev_ex_req = null;
                    if (previousExpCheckBox.isSelected())
                    {
                        prev_ex_req = "Y";
                    }
                    else
                        prev_ex_req = "N";
                    
                    proposeobjectData.setPrev_expe_require(prev_ex_req);
                    
                    json_propose_project = gson.toJson(proposeobjectData);
                    
                    if (this.clientApplicationInterface.proposeProjects(json_propose_project).equals("success"))
                    {
                        this.returnStatus = "success";
                        JOptionPane.showMessageDialog(null, "Project Proposed");
                        this.dispose();
                        
                        System.out.println("success");
                        return "success";
                    }
                    else
                    {
                        System.out.println("record already exists");
                        this.returnStatus = "not-success";
                        JOptionPane.showMessageDialog(null, "Error Occured");
                        return "record already existis";
                    }
                }
                catch (WebApplicationException ex)
                {
                    throw new WebApplicationException("Connection Refused");
                }
            }
            
        }
        return "";
    }
    
    /**
     * @param clientApplicationInterface
     *            binding the class
     */
    public void setInterface(ClientInterface clientApplicationInterface)
    {
        this.clientApplicationInterface = clientApplicationInterface;
    }
}
