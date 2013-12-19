package assignment3.server.Interfaces;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author Stephan Vasilev
 * 
 *
 */

@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface ITopicsCommunication
{
 /**
  *   
  * @param id
  * @return
  */
 @PermitAll
 @Path("staffProjects/{id}")
 @GET
 public String getAllStaffProposedProjects(@PathParam("id")String id);
  /**
  *   
  * @param id
  * @return
  */
 @PermitAll
 @Path("preProposed/{id}")
 @GET
 public String getUserNameAndID(@PathParam("id")String id);
 /**
  * 
  * @param proposedProject
  */
 @PermitAll
 @Path("/proposeProject")
 @POST
 public void proposeProject(String proposedProject);
 /**
  * 
  * @param id
  */
 @PermitAll
 @POST
 @Path("/assignProject/")
 public void assignProject(String id);
 /**
  * 
  * @return
  */
 @PermitAll
 @GET
 @Path("/getArtefacts")
 public String getArtifacts(); 
 /**
  * 
  * @param id
  * @return
  */
  
 @POST
 @Path("/interestedInTopic/{id}/{nextID}")
 public void interestInTopic(@PathParam("id") String id, @PathParam("nextID")String nextID);
 /**
  * 
  * @param id
  * @return
  */
 @PermitAll
 @GET
 @Path("/getTopicDetails/{id}")
 public String getTopicDetails(@PathParam("id")String id);
}
