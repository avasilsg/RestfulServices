package uk.ac.standrews.cs.cs5031.cs5031_exercise_week09;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.core.header.MediaTypes;

import static junit.framework.Assert.*;

public class MainTest {

	private HttpServer httpServer;

	private WebTarget target;	

	@Before
	protected void setUp() throws Exception {

		httpServer = Main.createServer();
		httpServer.start();

		Client c = ClientBuilder.newClient();
		c.register(new HttpBasicAuthFilter("stefan", "stefan"));
		target = c.target(Main.BASE_URI);
		
        String response = target.request().get(String.class);

	}

	@After
	protected void tearDown() throws Exception {		
		httpServer.stop();
	}

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	public void _testMyResource() {
		String responseMsg = target.path("myresource").request().get(String.class);
		assertEquals("Got it!", responseMsg);
	}

	/**
	 * Test if a WADL document is available at the relative path
	 * "application.wadl".
	 */
	public void testApplicationWadl() {
		String serviceWadl = target.path("application.wadl").request().accept(MediaTypes.WADL).get(String.class);
		assertTrue(serviceWadl.length() > 0);
	}
	
	@Test
	public void validateXML()
	{
	       String response = target.request().get(String.class);
	       assertEqals.
	}
	
}
