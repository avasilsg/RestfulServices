package assignment3.server.Classes;

import java.util.Vector;

import javax.ws.rs.Path;

import org.tmatesoft.sqljet.core.SqlJetException;

import assignment3.Controller.Interfaces.GSonParsing;
import assignment3.Interfaces.Model.User;
import assignment3.Interfaces.Model.DataBase.DBManipulator;
import assignment3.server.Interfaces.IUserRequest;
/**
 * @author Stephan Vasilev
 * 
 *
 */
@Path("/UserProfile")
public class UserRequest implements IUserRequest
{
    
    private GSonParsing    gsonParser;
    private DBManipulator  dbHandle;
    
    public UserRequest() throws SqlJetException
    {
        gsonParser = new GSonParsing();
        dbHandle   = new DBManipulator();
    }
    /**
     * 
     */
    @Override
    public Vector<String> getAllObject()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     */
    @Override
    public void rejectProject(String id)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public String getUserProfile(String id)
    {
        Vector<Vector<String>> receiveInfo = new Vector<Vector<String>>();
        Vector<User> users = dbHandle.selectAllUsers();
        for(User user:users)
        {
            receiveInfo.add(putIntoVector(user, id));
        }
        
        String ret_strString = gsonParser.parseToJSon(receiveInfo);
        return ret_strString;
    }
    
    private Vector<String> putIntoVector(User user, String id)
    {
        Vector<String> containerStrings = new Vector<String>();

        containerStrings.add(String.valueOf(user.getUserID()));
        containerStrings.add(user.getName());
        containerStrings.add(user.getRole());
        if (id.equals( "Staff" ) || id.equals( "Faculty" ))
        {
            containerStrings.add(user.getAddress());

        }
        return containerStrings;
    }
    @Override
    public String acceptProgect(String id)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
}
