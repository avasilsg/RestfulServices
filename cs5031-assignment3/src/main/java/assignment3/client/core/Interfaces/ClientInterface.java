package assignment3.client.core.Interfaces;

import javax.ws.rs.WebApplicationException;
/**
 * @author asif this interface is providing the higher level call from GUI to
 *         server
 * @author Stephan
 * 
 */
public interface ClientInterface {

    /**
     * @param userName username for the login
     * @param password password for the login
     * @param hostIP ip_address to be used to connect with the server
     * @param port port of the server
     * @param resourcePath path need to get the resources from the server using
     *            GET
     * @return String will be returned
     * @throws WebApplicationException
     */
    public String login(String userName, String password, String hostIP,
            String port, String resourcePath) throws WebApplicationException;

    /**
     * 
     * @return
     */
    public String proposeProjectInit();
    /**
     * @param ProposeobjectData Object
     * @return
     */
    public String proposeProjects(String Object);

    /**
     * @param role will be student/staff
     * @return
     */
    public String loadDataToView(String role);

    /**
     * @param post_string expressing the interests for the project
     * @return String for sucess and failure
     * @throws WebApplicationException
     */
    public String expressInterests(String express_projid_stdid)
            throws WebApplicationException;

    /**
     * @param string posting faculty id to fetch the data
     * @return string sucess and failure
     */
    public String loadFacultyDataView();

    /**
     * @param postInformation for sending updated table data back to DB
     * @return
     */
    public String updateFacultyProfile(String postInformation);

    /**
     * @param projectID for getting the interests table
     * @return
     */
    public String checkProjectStatuses(String projectID);

    /**
     * @param gsonInput project allocation object AllocationObjectdata
     * @return
     */
    public String projectAllocation(String gsonInput);

    /**
     * @param studentID for loading student data based on the studetn id
     * @return
     */
    public String loadStudentData(String studentID);

    /**
     * @param question for posting the question
     * @return
     */
    public String postQuestion(String question);

    /**
     * @param answer for posting the answer
     * @return
     */
    public String answerQuestion(String id, String answer);

    /**
     * @return String includes the updated forum conversation
     */
    public String updateForum(String id);
    /**
     * 
     * @param id
     * @return
     */
    public String getTopicDetails(String id);
    
    /**
     * @param gsonInput project allocation object projectAcceptance
     * @return
     */
    public String projectAcceptance(String gsonInput);
    
    /**
     * 
     * @param id - userID
     * @return check for assigned topics with the user ID
     */
    public String checkForAssignTopics(String id);

}
