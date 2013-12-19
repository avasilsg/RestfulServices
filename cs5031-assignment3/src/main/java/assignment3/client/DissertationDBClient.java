package assignment3.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;

public class DissertationDBClient {

	public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        client.register(new HttpBasicAuthFilter("Harry Potter", "123"));
        WebTarget target = client.target("http://localhost:9998").path("serverResp");
        String response = target.request().get(String.class);	
	    if (null == response)
	    {
	        System.out.println("Error. Bad Input!");
	    }
	}
}
