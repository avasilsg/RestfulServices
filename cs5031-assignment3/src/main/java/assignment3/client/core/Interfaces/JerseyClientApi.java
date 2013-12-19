package assignment3.client.core.Interfaces;


import java.net.ConnectException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * @author asif Facade for handling Jersy Client API
 * @author Stephan
 * 
 */
public interface JerseyClientApi
{
    
    /**
     * @return client builder client object
     */
    public Client clientBuilder();
    
    /**
     * @param userName
     *            for authentication username
     * @param password
     *            for authentication password
     * @throws WebApplicationException
     */
    public void registerClient(String userName, String password)
            throws WebApplicationException;
    
    /**
     * @param hostAddress
     *            address of the target
     * @param port
     *            for contacting
     * @param resource_path
     *            path for accessing at the server
     * @return will return the target on which get method will be called
     * @throws ConnectException
     */
    public WebTarget target(String hostAddress, String port, String resource_path)
            throws ConnectException;
    
    /**
     * @return return the string after invoking get
     */
    public String invocationBuilderGet();
    
    /**
     * @param param
     *            query parameter
     * @param value
     *            query value
     * @return return the string after invoking get
     */
    public String invocationBuilderGetWithParam(String param, String value);
    
    /**
     * @param post_string
     *            for posting to server
     * @return will return the response
     */
    public String invocationBuilderPost(String post_string);
}
