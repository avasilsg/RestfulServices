package assignment3.server.Interfaces;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * 
 * @author Stephan Vasilev
 *
 */
public interface IInterestRequests
{
    @PermitAll
    @GET
    @Path("/getCommentsHistory/{id}")
    public String getComments(@PathParam("id")String id);   
    
    /**s
     * 
     * @param proposedProject
     */
    @PermitAll
    @POST
    @Path("/updateComments/{id}/{content}")
    public void addComment(@PathParam("id")String id,@PathParam("content")String content);
    
    /**
     * 
     * @param id
     * @return
     */
    @RolesAllowed({ "Faculty" })
    @GET
    @Path("/interestedStudents/{id}")
    public String getAllInterestedObject(@PathParam("id")String id);
    
    
}
