package assignment3.server.Classes;

import java.io.InvalidObjectException;
import java.util.Vector;

import javax.ws.rs.Path;

import org.tmatesoft.sqljet.core.SqlJetException;

import assignment3.Controller.Interfaces.GSonParsing;
import assignment3.Interfaces.Model.Topic;
import assignment3.Interfaces.Model.User;
import assignment3.Interfaces.Model.DataBase.DBManipulator;
import assignment3.Interfaces.Model.DataBase.UserResponder;
import assignment3.server.Interfaces.IUserPersonalProfile;

@Path("/UserPersonalProfile")
public class UserPersonalProfile implements IUserPersonalProfile
{
    private GSonParsing    gsonParser;
    private DBManipulator  dbHandle;
    
    public UserPersonalProfile()
    {
        try
        {
            dbHandle = new DBManipulator();
            gsonParser = new GSonParsing();
        }
        catch (SqlJetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public String getUserProfile(String id)
    {
        id = gsonParser.gSonParser.toJson(id);
        id = id.replaceAll("\"", "");
        long userID = 0;
        Vector<String> containerStrings = new Vector<String>();
        Vector<User> users = dbHandle.selectAllUsers();
        for(User user:users)
        {
            if (id.equals(user.getName()))
            {
                containerStrings.add(String.valueOf(user.getUserID()));
                userID = user.getUserID();
                containerStrings.add(user.getPersonName());
                containerStrings.add(user.getRole());
                containerStrings.add(user.getAddress()); 
                containerStrings.add(user.getResInterest());
                break;
            }
        }
        Vector<Topic> topics = new Vector<Topic>();
        topics .addAll(dbHandle.selectAllFromStaffProposed());
        for(Topic topic : topics)
        {
            if (userID == (topic.getProposedBy()))
            {
                containerStrings.add(topic.getTitle());
                containerStrings.add(String.valueOf(topic.getTopicID()));
            }
        }
        
        Vector<Vector<String>> result = new Vector<Vector<String>>();
        result.add(containerStrings);
        
        String ret_strString = gsonParser.parseToJSon(result);
        return ret_strString;
    }
    
    @Override
    public void updateProfile(String id)
    {
        UserResponder responder = new UserResponder();
        
        try
        {
            Vector<Vector<String>> result =  gsonParser.parserFromJsonVector(id);
            Vector<String> updatedProfile = new Vector<String>();
            updatedProfile.addAll(result.get(0));
            User user = new User();
            Vector<User> users = dbHandle.selectAllUsers();
            for(User currentUser:users)
            {
                if (Long.parseLong(updatedProfile.get(0)) == currentUser.getUserID())
                {
                    user = currentUser;
                    user.setAddress(updatedProfile.get(1));
                    user.setPersonName(updatedProfile.get(2));
                }
            }
            try
            {
                responder.processEditRequest(user);
            }
            catch (SqlJetException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        catch (InvalidObjectException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    public String checkForAssignTopics(String id)
    {
        id = id.replaceAll("\"", "");
        Vector<Topic> topics = new Vector<Topic>();
        topics .addAll(dbHandle.selectAllFromStaffProposed());
        for(Topic topic : topics)
        {
            if (Long.parseLong(id) == (topic.getAssignedTo()))
            {
                if (false == topic.isAccepted())
                {
                    return this.gsonParser.gSonParser.toJson("true");
                }
                else 
                {
                    return gsonParser.gSonParser.toJson("false");
                }
            }
        }
        
        return gsonParser.gSonParser.toJson("false");
    }
    @Override
    public void acceptProgect(String id)
    {
        id = id.replaceAll("\"", "");
        id = id.replaceAll("\"", "");
        Vector<Topic> topics = new Vector<Topic>();
        topics .addAll(dbHandle.selectAllFromStaffProposed());
        for(Topic topic : topics)
        {
            if (Long.parseLong(id) == (topic.getAssignedTo()))
            {
                if (false == topic.isAccepted())
                {
                    topic.setAccepted(true);
                }
            }
        }
    }
    
}
