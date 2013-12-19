package assignment3.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("serverResp")
public class ServerResponse
{

    @Context
    SecurityContext securityContext;
    
    @GET
    @Produces("text/plain")
    public String getUserInfo()
    {
        return "authenticated";
    }   
}
