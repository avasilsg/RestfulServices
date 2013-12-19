package assignment3.client.datahandling;

/**
 * @author Asif
 * @author Stephan
 *
 */
/*This class is handling the data for project allocation*/
public class AllocationObjectData
{
    
    /**
     * project id for allocated project
     */
    private String projectID;
    
    /**
     * student id for the allocated project
     */
    private String studentID;
    
    /**
     * supervisior/faculty id of the project
     */
    private String supervisiorID;
    
    /**
     * project title
     */
    private String projectTitle;
    
    /**
     * @return String project_id in the form of string
     */
    public String getProjectID()
    {
        return projectID;
    }
    
    /**
     * @param project_id
     *            setting the project id
     */
    public void setProjectID(String project_id)
    {
        this.projectID = project_id;
    }
    
    /**
     * @return String student_id
     */
    public String getStudentID()
    {
        return studentID;
    }
    
    /**
     * @param studentID
     *            for setting the student id
     */
    public void setStudentID(String studentID)
    {
        this.studentID = studentID;
    }
    
    /**
     * @return String will be returned as supervisior id
     */
    public String getSupervisiorID()
    {
        return supervisiorID;
    }
    
    /**
     * @param supervisiorID
     *            setting the supervisior id
     */
    public void setSupervisiorID(String supervisiorID)
    {
        this.supervisiorID = supervisiorID;
    }
    
    /**
     * @return String getting the project title
     */
    public String getProjectTitle()
    {
        return projectTitle;
    }
    
    /**
     * @param projectTitle
     *            setting the project title
     */
    public void setProjectTitle(String projectTitle)
    {
        this.projectTitle = projectTitle;
    }
    
}
