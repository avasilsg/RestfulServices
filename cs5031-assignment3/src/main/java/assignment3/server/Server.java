package assignment3.server;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class Server
{
    public static final URI BASE_URI = UriBuilder.fromUri("http://localhost/")
                                             .port(getPort(9998)).build();
    
    private static int getPort(int defaultPort)
    {
        String httpPort = System.getProperty("jersey.test.port");
        if (null != httpPort)
        {
            try
            {
                return Integer.parseInt(httpPort);
            }
            catch (NumberFormatException e)
            {
                throw new NumberFormatException();
            }
        }
        return defaultPort;
    }
    
    public static HttpServer createServer() throws IOException
    {
        final ResourceConfig rc = new PackagesResourceConfig("assignment3.server");
        rc.getProperties().put(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS,
                UserAuthentication.class.getName());
        return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
    }
    

}
