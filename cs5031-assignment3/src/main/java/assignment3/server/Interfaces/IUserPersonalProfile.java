package assignment3.server.Interfaces;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface IUserPersonalProfile
{
    /**
     * 
     * @return String converted in json format with a information about the particular user;
     */
    @PermitAll
    @Path("userPersonalRequest/{id}")
    @GET
    public String getUserProfile(@PathParam("id")String id);
    /**
     */
    @RolesAllowed({ "Faculty" })
    @Path("updateUserRequest")
    @POST
    public void updateProfile(String id);
    /**
     * 
     * @return
     */
//    @RolesAllowed({ "Student" })
    @PermitAll
    @POST
    @Path("/acceptProgect/{id}")
    public void acceptProgect(@PathParam("id")String id);
    
    @PermitAll
    @Path("checkProjectStatus/{id}")
    @GET
    public String checkForAssignTopics(@PathParam("id")String id);
}
