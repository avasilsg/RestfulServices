package assignment3.server;

import java.io.IOException;
import org.glassfish.grizzly.http.server.HttpServer;

public class DissertationDBServer
{    
    public static void main(String[] args) throws IOException
    {
        HttpServer httpServer = Server.createServer();
        System.out.println("Starting grizzly2...");
        httpServer.start();
        System.out.println(String.format(
                "Jersey app started with WADL available at "
                        + "%sapplication.wadl\nHit enter to stop it...",
                        Server.BASE_URI));
        System.in.read();
        httpServer.stop();
    }
}  

