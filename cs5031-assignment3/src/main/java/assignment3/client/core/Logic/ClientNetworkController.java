package assignment3.client.core.Logic;


import java.net.ConnectException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import assignment3.client.core.Interfaces.ClientInterface;
import assignment3.client.core.Interfaces.JerseyClientApi;
import assignment3.client.core.Interfaces.Singleton;

import com.google.gson.Gson;

/**
 * @author asif this class handles all the communication between the GUI and the
 *         Client API
 * @author Stephan
 * 
 */
public class ClientNetworkController implements ClientInterface {

    /**
     * binding the Facade interface with the implementing class
     */
    private  JerseyClientApi client;
    private Gson gson;

    /* These are the parameters used for the testing */
    private String username;
    private String password;
    private String hostIP;
    private String port;
    private String resourcePath;
    private String invoice;
    public String postToServerResponse;
    public String express_interest_str;
    public String view_project_str;
    private String buildGet;
    public String buildPost;
    public String buildPut;

    public ClientNetworkController()
    {
        gson = new Gson();
        client = new HTTPJersyClient();
        resourcePath = "serverResp";
        initConnectionInfo();
    }
    /**
     * @param facadeJersey setting the client to user Facade interface
     */
    public void setClient(JerseyClientApi facadeJersey) 
    {
        this.client = facadeJersey;
    }

    /**
     * @return Client object of Jersy API interface
     */
    public Client clientBuilder()
    {
        return client.clientBuilder();
    }

    /**
     * @throws WebApplicationException registering Client for getting web target
     */
    public void registerClient() throws WebApplicationException
    {
        try
        {
            this.client.registerClient(this.username, this.password);
        }
        catch (WebApplicationException ex)
        {
            throw new WebApplicationException(
                    "authentication filter cannot be created");
        }
        
    }

    /**
     * @return WebTarget , which will include the path to callee method
     * @throws ConnectException
     */
    public WebTarget getTarget() throws ConnectException 
    {
        try 
        {
            return this.client.target(this.hostIP, this.port, this.resourcePath);
        } 
        catch (ConnectException ex) 
        {
            throw new ConnectException("ipaddress/port not matched or path not found");
        }
    }

    /**
     * @return return the String as the response from server
     */
    public String invocationBuilderGet() 
    {
        if (!this.buildGet.equals(gson.toJson("get_response"))) 
        {
            return "invalid response format";
        }

        return this.client.invocationBuilderGet();
    }

    /**
     * @return return the String as the response from server
     */
    public String invocationBuilderPut() 
    {
        if (!this.buildPut.equals(gson.toJson("get_put_response"))) 
        {
            return "invalid response format";
        }

        return this.client.invocationBuilderGet();
    }
    /**
     * @param param query parameter
     * @param value value of the parameter
     * @return
     */
    public String invocationBuilderGetParam(String param, String value) 
    {
        
        if (!this.buildGet.equals(gson.toJson("get_response"))) 
        {
            return "Invalid response format.";
        }

        System.out.println("Before Sending-2 " + invoice.toString());

        return this.client.invocationBuilderGetWithParam(param, value);

    }

    /**
     * @return return the String as a result of post call
     */
    public String invocationBuilderPost() 
    {
        
        if (!this.buildPost.equals(gson.toJson("get_post_response"))) 
        {
            return "invalid response format";
        }

        return this.client.invocationBuilderPost(this.postToServerResponse);
    }

    @Override
    public String login(String username, String password, String hostIP,
                        String port, String resource_path) 
    throws WebApplicationException 
    {
        try 
        {
            this.username = username;
            this.password = password;
            Singleton.getInstance().username = this.username;
            Singleton.getInstance().password = this.password;
            this.initConnectionInfo();
            prepareResponse();
            buildGet = gson.toJson("get_response");
            String loginStatus = this.invocationBuilderGet();
            System.out.println("response" + loginStatus);
            return loginStatus;
        } 
        catch (Exception ex) 
        {
            throw new WebApplicationException("No such username or password-NC");
        }

    }

    @Override
    public String proposeProjects(String post_object) 
    {

        this.postToServerResponse = post_object;

        try 
        {
            if (this.postToServerResponse.equals(post_object))
            {
                initConnectionInfo();
                this.postToServerResponse = post_object;
                this.resourcePath = "Topics/proposeProject";
                this.prepareResponse();
                this.buildPost = gson.toJson("get_post_response");
                @SuppressWarnings("unused")
                String response = this.invocationBuilderPost();
                return "success";
            }
            else
            {
                return "not-success";
            }
        } 
        catch (Exception ex) 
        {
            return "success";
        }

    }

    private void initConnectionInfo()
    {
        this.username = Singleton.getInstance().username;
        this.password = Singleton.getInstance().password;
        this.hostIP = "localhost";
        this.port = "9998";
    }

    @Override
    public String loadDataToView(String user) {

        try {

            initConnectionInfo();
            this.resourcePath = "Topics/staffProjects" + "/" + user;
            this.view_project_str = user;
            prepareResponse();
            buildGet = gson.toJson("get_response");
            String response = this.invocationBuilderGet();
            System.out.println("response" + response);
            return response;

        } 
        catch (Exception ex) 
        {
            throw new WebApplicationException("Connection Refused/Invalid Path/Role not found");
        }
    }

    @Override
    public String expressInterests(String post_string)
    throws WebApplicationException 
    { 
        if (null == post_string)
        {
            throw new NullPointerException("Wrong parameter.");
        }
        try
        {
            initConnectionInfo();
            String project_id[] = post_string.split(":");
            this.resourcePath = "Topics/interestedInTopic" + "/"
                    + project_id[0] + "/" + project_id[1];
            this.postToServerResponse = post_string;
            this.resourcePath = "Topics/interestedInTopic" + "/"
                    + project_id[0] + "/" + project_id[1];
            this.prepareResponse();
            this.buildPost = gson.toJson("get_post_response");
            String response = this.invocationBuilderPost();
            return response;

        } 
        catch (Exception ex)
        {
            throw new WebApplicationException("Connection Refused/cannot express the interests");
        }
    }

    @Override
    public String loadFacultyDataView() {

        try 
        {
            initConnectionInfo();
            this.resourcePath = "UserPersonalProfile/userPersonalRequest" + "/" + this.username;
            this.postToServerResponse = username;
            prepareResponse();
            buildGet = gson.toJson("get_response");
            String response = this.invocationBuilderGet();

            return response;

        } 
        catch (Exception ex) 
        {
            throw new WebApplicationException("Connection Refused/faculty-Id not found");
        }
    }

    public String updateFacultyProfile(String post_string) {

        try {

            initConnectionInfo();
            this.resourcePath = "UserPersonalProfile/updateUserRequest";
            this.postToServerResponse = post_string;
            prepareResponse();
            this.buildPost = gson.toJson("get_post_response");
            String response = this.invocationBuilderPost();

            return response;

        } catch (Exception ex) 
        {
            throw new WebApplicationException(
                    "Connection Refused/record not found");
        }

    }

    @Override
    public String checkProjectStatuses(String topicID) {

        try 
        {
            initConnectionInfo();
            this.resourcePath = "interestedTopics/interestedStudents/" + topicID;
            prepareResponse();
            buildGet = gson.toJson("get_response");
            String response = this.invocationBuilderGet();

            return response;

        } catch (Exception ex) 
        {
            throw new WebApplicationException("Connection Refused/Error while checking the project statuses");
        }

    }

    public String projectAllocation(String post_object) 
    {

        this.postToServerResponse = post_object;

        try 
        {
            if (this.postToServerResponse.equals(post_object))
            {
                initConnectionInfo();
                this.resourcePath = "Topics/assignProject";
                this.prepareResponse();
                this.buildPost = gson.toJson("get_post_response");
                String response = this.invocationBuilderPost();
                return response;
            }
            else
            {
                return "not-success";
            }
        } 
        catch (Exception ex) 
        {
            return "success";
        }
    }
    public String loadStudentData(String id) {
        try {

            WebTarget target = null;
            initConnectionInfo();
            this.resourcePath = "myresource" + "/Student" + "/" + id;
            this.postToServerResponse = id;

            this.clientBuilder();
            this.registerClient();
            target = this.getTarget();
            if (null == target)
            {
                throw new WebApplicationException("Connection Refused/can not update the forum");
            }
            this.buildGet = gson.toJson("get_response");
            String response = this.invocationBuilderGet();

            return response;

        } 
        catch (Exception ex) 
        {
            throw new WebApplicationException("Connection Refused/Std-Id not found");
        }
    }

    @Override
    public String postQuestion(String question) {
        try 
        {

            initConnectionInfo();
            this.resourcePath = "interestedTopics/getCommentsHistory/"
                    + question;
            this.postToServerResponse = question;

            prepareResponse();
            String response = this.invocationBuilderPost();
            this.buildPost = gson.toJson("get_post_response");
            return response;

        } catch (Exception ex) {
            throw new WebApplicationException(
                    "Connection Refused/cannot post the question");
        }
    }

    @Override
    public String answerQuestion(String id, String answer)
    {
        try {

            initConnectionInfo();
            this.resourcePath = "interestedTopics/updateComments/" + id + "/" +  answer;
            this.postToServerResponse = answer;
            prepareResponse();
            this.buildPost = gson.toJson("get_post_response");
            String response = this.invocationBuilderPost();
            return response;

        } catch (Exception ex) {
            throw new WebApplicationException(
                    "Connection Refused/cannot post the question");
        }
    }

    private void prepareResponse() throws ConnectException
    {
        WebTarget target;
        this.clientBuilder();
        this.registerClient();
        target = this.getTarget();
        if (null == target)
        {
            throw new WebApplicationException("Connection Refused/can not update the forum");
        }
    }

    @Override
    public String updateForum(String id) 
    {
        try 
        {
            this.initConnectionInfo();
            this.postToServerResponse = id;
            this.resourcePath = "interestedTopics/getCommentsHistory/" + id;
            this.prepareResponse();
            buildGet = gson.toJson("get_response");
            String response = this.invocationBuilderGet();

            return response;
        } 
        catch (Exception ex) 
        {
            throw new WebApplicationException("Connection Refused/can not update the forum");
        }
    }
    @Override
    public String proposeProjectInit()
    {
        try 
        {
            initConnectionInfo();
            this.resourcePath = "Topics/preProposed" + "/" + this.username;
            this.prepareResponse();
            buildGet = gson.toJson("get_response");
            String response = this.invocationBuilderGet();

            return response;
        } 
        catch (Exception ex) 
        {
            throw new WebApplicationException("No such username or password-NC");
        }
    }
    
    @Override
    public String getTopicDetails(String id)
    {
        try 
        {
            initConnectionInfo();
            this.postToServerResponse = id;
            this.resourcePath = "Topics/getTopicDetails" + "/" + id;
            this.prepareResponse();
            buildGet = gson.toJson("get_response");
            String response = this.invocationBuilderGet();

            return response;
        } 
        catch (Exception ex) 
        {
            throw new WebApplicationException("No such username or password-NC");
        }
    }
    
    @Override
    public String checkForAssignTopics(String id)
    {
        try 
        {
            initConnectionInfo();
            this.postToServerResponse = id;
            this.resourcePath = "UserPersonalProfile/checkProjectStatus" + "/" + id;
            this.prepareResponse();
            buildGet = gson.toJson("get_response");
            String response = this.invocationBuilderGet();

            return response;
        } 
        catch (Exception ex) 
        {
            throw new WebApplicationException("No such username or password-NC");
        }
    }
    
    @Override
    public String projectAcceptance(String id)
    {
        try
        {
            initConnectionInfo();
            this.postToServerResponse = id;
            this.resourcePath = "UserPersonalProfile/acceptProgect" + "/" + id;
            this.prepareResponse();
            this.buildPost = gson.toJson("get_post_response");
            String response = this.invocationBuilderPost();

            return response;

        }
        catch (ConnectException e)
        {
            throw new WebApplicationException("No such username or password-NC");
        }
    }
}
