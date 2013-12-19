package assignment3.Interfaces.Model.DataBase;

import java.util.Vector;

import org.tmatesoft.sqljet.core.SqlJetException;

import assignment3.Interfaces.Model.Artefacts;
import assignment3.Interfaces.Model.Role;
import assignment3.Interfaces.Model.Topic;
import assignment3.Interfaces.Model.User;
import assignment3.server.Interfaces.IDBManipulator;


public class DBManipulator implements IDBManipulator
{
    public DBCreator handler;
    public UserResponder responder;
    public TopicResponder  topicResponder;
    public InterestResponder interestResponder;
    public ArtefactResponder artifactResponder;
    
    public DBManipulator() throws SqlJetException
    {
        handler = new DBCreator();
        responder = new UserResponder();
        topicResponder = new TopicResponder();
    }
    String getRole (Role role)
    {
        switch(role)
        {
            case FacultyMember:
            {
                return "FacultyMember";
            }
            
            case StaffMember:
            {
                return "StaffMember";
            }
            
            case Student:
            {
                return "Student";
            }
        }
        
        return "default";
    }
    public void addUsers()
    {
        
    }
    
    @Override
    public User selectUserProfile(String usersID)
    {
        return null;
    }

    @Override
    public Vector<Topic> selectAllFromStaffProposed()
    {
        TopicResponder topic_resp = new TopicResponder();
        Vector<Topic> topics = new Vector<Topic>();
        try {
            topics = topic_resp.getAllTopicInfo();
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
        
        return topics;
    }
    
    /**
     * get the information of all the users.
     * @return
     */
    public Vector<User> selectAllUsers()
    {
        try
        {
            Vector<User> users = responder.getAllUserInfo();
            return users;

        }
        catch (SqlJetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get all the projects that are not accepted
     */
    @Override
    public Vector<Topic> selectAllPendingProposed()
    {
    	TopicResponder topic_resp = new TopicResponder();
    	Vector<Topic> unaccep_topic = new Vector<Topic>();
    	try {
			unaccep_topic = topic_resp.getAllUnassignedTopic();
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
        
        return unaccep_topic;
    }

    /**
     * get all the titles of the projects that a student is interested in
     */
    @Override
    public Vector<String> getInterestedProjects(String userID)
    {
    	TopicResponder topic_resp = new TopicResponder();
    	Vector<String> proj_topic = new Vector<String>(); 
    	long cur_userid = Long.parseLong(userID);
    	
        try {
			proj_topic = topic_resp.getAllTopicsInterestByUserID(cur_userid);
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
    	
        return proj_topic;
    }

    /**
     * add a new project into the project list
     */
    @Override
    public void proposedProject(Topic topic, Artefacts artefact)
    {    	
    	TopicResponder topic_resp = new TopicResponder();
    	try {
			topic_resp.processAddRequest(topic);
		} catch (SqlJetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	ArtefactResponder artf_resp = new ArtefactResponder();
    	try {
			artf_resp.processAddRequest(artefact);
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
    }

    /**
     * get projects that are proposed by user with userID
     */
    @Override
    public Vector<Topic> getProposedProject(String userID)
    {
        Vector<Topic> topics = new Vector<Topic>();
        long l_userID = Long.parseLong(userID);
        TopicResponder resp = new TopicResponder();
        
        try
        {
           topics = resp.getProposedTopicByUserID(l_userID);
        }
        catch (SqlJetException e)
        {
            e.printStackTrace();
        }
        return topics;
    }

    /**
     * get all the references with a specific topicID
     */
    @Override
    public String getReferences(String topicID)
    {
    	ArtefactResponder artf_resp = new ArtefactResponder();
    	long cur_topicid = Long.parseLong(topicID);
    	String references = new String();
    	
    	try {
			artf_resp.getAllReferencesWithTopicID(cur_topicid);
		} catch (SqlJetException e) {
			e.printStackTrace();
		}
    	
        return references;
    }

    @Override
    public void assignProjectToStudent(String topicId, String userID)
    {
//        long usr = Long.parseLong(userID);
        /*TopicResponder*/
        //processEditRequest(StringBuffer buffer, Topic topic, Vector<String> vector)
        
        // the vector string here should be the same format as u insert data into topic table
        // if you want to update assigned_to with studentID, just keep the other parts
        // as what they are
    }

    @Override
    public void acceptProject(String topicId, String userID)
    {
        // TODO Auto-generated method stub
        /*TopicResponder*/
        //processEditRequest(StringBuffer buffer, Topic topic, Vector<String> vector)
        
        // the vector string here should be the same format as u insert data into topic table
        // if you want to update accepted with studentID, just keep the other parts
        // as what they are
    }

}
