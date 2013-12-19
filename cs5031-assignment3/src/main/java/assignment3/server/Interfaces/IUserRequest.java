package assignment3.server.Interfaces;

import java.util.Vector;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface IUserRequest
{
    @PermitAll
    @Path("userRequest/{id}")
    @GET
    public String getUserProfile(@PathParam("id")String id);
    /**
     * 
     * @return
     */
    @GET
    @Path("/interestedFromProjects")
    public Vector<String> getAllObject();
    /**
     * 
     * @return
     */
    @GET
    @Path("/acceptProgect/{id}")
    public String acceptProgect(@PathParam("id")String id);
    
    /**
     * 
     * @param id
     */
    @DELETE
    @Path("/delete")
    public void rejectProject(@PathParam("reject") String id);
}
