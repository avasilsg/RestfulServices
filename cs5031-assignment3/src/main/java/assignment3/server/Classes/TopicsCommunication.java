package assignment3.server.Classes;

import java.io.InvalidObjectException;
import java.util.Vector;

import javax.ws.rs.Path;

import org.tmatesoft.sqljet.core.SqlJetException;

import assignment3.Controller.Interfaces.GSonParsing;
import assignment3.Interfaces.Model.Interest;
import assignment3.Interfaces.Model.ProposeobjectData;
import assignment3.Interfaces.Model.Topic;
import assignment3.Interfaces.Model.User;
import assignment3.Interfaces.Model.DataBase.DBManipulator;
import assignment3.Interfaces.Model.DataBase.InterestResponder;
import assignment3.Interfaces.Model.DataBase.TopicResponder;
import assignment3.server.Interfaces.ITopicsCommunication;
/**
 * @author Stephan Vasilev
 * 
 *
 */
@Path("/Topics")
public class TopicsCommunication implements ITopicsCommunication
{
    private GSonParsing    gsonParser;
    private DBManipulator  dbHandle;
    
    public TopicsCommunication() throws SqlJetException
    {
        gsonParser = new GSonParsing();
        dbHandle   = new DBManipulator();
    }
    /**
     * 
     */
    @Override
    public String getAllStaffProposedProjects(String id)
    {
        TopicResponder topicResponder = new TopicResponder();
        System.out.println(id.toString());
        Vector<Vector<String>> receiveInfo = new Vector<Vector<String>>();
        Vector<Topic> topics = new Vector<Topic>();
        id = id.replaceAll("\"", "");
        if (id.equals("Student"))
        {
            try
            {
                topics .addAll(topicResponder.getNonStuTopic());
            }
            catch (SqlJetException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (id.equals( "Staff" ) || id.equals( "Faculty" ))
        {
            try
            {
                topics .addAll(topicResponder.getAllTopicInfo());
            }
            catch (SqlJetException e)
            {
                new SqlJetException("Database problem.");
            }
        }
        
        for(Topic topic: topics)
        {
            receiveInfo.add(putIntoVector(topic, id));
        }
        String ret_strString = gsonParser.parseToJSon(receiveInfo);
        
        return ret_strString;
    }
    
    private Vector<String> putIntoVector(Topic topic, String id)
    {
        Vector<String> containerStrings = new Vector<String>();

        containerStrings.add(String.valueOf(topic.getTopicID()));
        containerStrings.add(topic.getTitle());
        Vector<User> users = dbHandle.selectAllUsers();
        for(User currentUser:users)
        {
            if (topic.getProposedBy() == currentUser.getUserID())
            {
                containerStrings.add(currentUser.getPersonName());
                break;
            }
        }
//        containerStrings.add(topic.getDescription());
//        containerStrings.add(String.valueOf(topic.getPreviousExperince()));
//        containerStrings.add(String.valueOf(topic.getSupervisorID()));
//        if (id.equals( "Staff" ) || id.equals( "Faculty" ))
//        {
//            containerStrings.add(String.valueOf(topic.getAssignedTo()));
//            containerStrings.add(String.valueOf(topic.getProposedBy()));
//            containerStrings.add(String.valueOf(topic.getSupervisorID()));
//        }
        return containerStrings;
    }
    /**
     * 
     */
    @Override
    public void assignProject(String id)
    {
        System.out.println(id);
        TopicResponder responder = new TopicResponder();
        InterestResponder respInterest = new InterestResponder();
        Vector<Vector<String>> modelVector = new Vector<>();
        Vector<String> temp = new Vector<String>();

        try
        {
            modelVector = gsonParser.parserFromJsonVector(id);
            temp.addAll(modelVector.get(0));            
        }
        catch (InvalidObjectException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        try
        {
            Topic topic = responder.findTopic(Long.parseLong(temp.elementAt(0)));
            topic.setSupervisorID(Long.parseLong(temp.get(1)));
            topic.setAssignedTo(Long.parseLong(temp.get(2)));
            responder.processEditRequest(topic);
            
            respInterest.removeInterest(topic.getTopicID());
            
            Vector<Interest> interests = respInterest.getAllInterestInfo();
            for(Interest interest : interests)
            {
                if (interest.getTopicID() == Long.parseLong(temp.elementAt(0)))
                {
                    respInterest.removeInterest(interest.getInterestID());
                }
            }
        }
        catch (NumberFormatException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SqlJetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 
     */
    @Override
    public void proposeProject(String proposedProject)
    {
        Topic       proposedTopic = new Topic();
        ProposeobjectData temp = (ProposeobjectData) gsonParser.parseFromJSon(proposedProject);
        
        proposedTopic.setAccepted(false);
        proposedTopic.setTitle( temp.getTxt_project_aims());
        if (null != temp.getProposedBy())
        {
            String id = temp.getProposedBy();
            long ID = Long.parseLong(id);
            proposedTopic.setProposedBy(ID);
        }
        else {
            throw new NullPointerException("Wrong ID");
        }
        proposedTopic.setDescr(temp.getTxt_project_description() + "\n" + "\n" + temp.getDeliverables());
        if ("Y".equals(temp.getPrev_expe_require()))
        {
            proposedTopic.setPreviousExperince(true);
        }
        else 
        {
            proposedTopic.setPreviousExperince(false);
        }    
        
        try
        {
            TopicResponder respo = new TopicResponder();
            respo.processAddRequest(proposedTopic);
        }
        catch (SqlJetException e)
        {
            e.printStackTrace();
        }
        
    }
    /**
     * 
     */
    @Override
    public String getArtifacts()
    {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * 
     */
    @Override
    public void interestInTopic(String id, String nextID)
    {
        InterestResponder interest = new InterestResponder();
        long topicID = Long.parseLong(id);
        long userID = Long.parseLong(nextID);
        Interest interestInProject = new Interest(5, topicID, userID);
        try
        {
            interest.processAddRequest(interestInProject);
        }
        catch (SqlJetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public String getUserNameAndID(String id)
    {
        id = gsonParser.gSonParser.toJson(id);
        id = id.replaceAll("\"", "");
        Vector<User> users = dbHandle.selectAllUsers();
        Vector<String> containerStrings = new Vector<String>();
        for(User user:users)
        {
            if (id.equals(user.name))
            {
                containerStrings.add(String.valueOf(user.getUserID()));
                containerStrings.add(user.getPersonName());
                containerStrings.add(user.getRole());
                break;
            }
        }
        
        Vector<Vector<String>> result = new Vector<Vector<String>>();
        result.add(containerStrings);
        
        String ret_strString = gsonParser.parseToJSon(result);
        return ret_strString;  
    }
    @Override
    public String getTopicDetails(String id)
    {
        System.out.println(id.toString());
        TopicResponder topicResponder = new TopicResponder();
        Vector<Vector<String>> receiveInfo = new Vector<Vector<String>>();
        Vector<Topic> topics = new Vector<Topic>();
        id = id.replaceAll("\"", "");

        try
        {
            topics .addAll(topicResponder.getAllTopicInfo());
            for(Topic topic : topics)
            {
                Vector<String> containerStrings = new Vector<String>();
                if (Long.parseLong(id) == topic.getTopicID())
                {
                    containerStrings.add(topic.getDescription());
                    containerStrings.add(String.valueOf(topic.getPreviousExperince()));
                    receiveInfo.add(containerStrings);
                    break;
                }
            }
        }
        catch (SqlJetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String ret_strString = gsonParser.parseToJSon(receiveInfo);
        
        return ret_strString;        
    }
       
}
