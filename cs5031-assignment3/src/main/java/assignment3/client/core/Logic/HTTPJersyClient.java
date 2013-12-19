package assignment3.client.core.Logic;

import java.net.ConnectException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;

import assignment3.client.core.Interfaces.JerseyClientApi;

/**
 * @author asif implementation class of the Facade Interface
 * 
 */
public class HTTPJersyClient implements JerseyClientApi
{
    
    Client    client = null;
    WebTarget target = null;
    
    @Override
    public Client clientBuilder()
    {
        client = ClientBuilder.newClient();
        return client;
    }
    
    /* register client method for authentication filter */
    @Override
    public void registerClient(String username, String password)
            throws WebApplicationException
    {
        this.client.register(new HttpBasicAuthFilter(username, password));
    }
    
    @Override
    public WebTarget target(String ip_address, String port, String resource_path)
                            throws ConnectException
    {
        System.out.println(ip_address);
        System.out.println(port);
        System.out.println(resource_path);
        target = this.client.target("http://" + ip_address + ":" + port).path(
                resource_path);
        return target;
    }
    
    /* Invoking the GET method */
    @Override
    public String invocationBuilderGet()
    {
        return this.target.request().get(String.class);
    }
    
    /* Invoking the GET method with parameters */
    @Override
    public String invocationBuilderGetWithParam(String param, String value)
    {
        return this.target.queryParam(param, value).request().get(String.class);
    }
    
    /* Invoking the Post Method */
    @Override
    public String invocationBuilderPost(String post_string)
    {
        String response = this.target.request().post(Entity.entity(post_string, MediaType.APPLICATION_JSON),
                                                     String.class);
        
        return response;
        
    }
}
