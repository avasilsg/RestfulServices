package assignment3.Interfaces.Model.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

import assignment3.Interfaces.Model.Interest;
import assignment3.Interfaces.Model.Topic;
import assignment3.Interfaces.Model.User;

public class TopicResponder
{
    
    private void printReturnToStudent(StringBuffer buffer)
    {
        buffer.append("<hr><a href='/'>Return to student table</a>");
    }
    
    /**
     * process add request from the server
     * 
     * @param buffer
     * @param vector
     * @throws SqlJetException
     */
    public void processAddRequest(Topic topic) throws SqlJetException
    {
        
        StringBuffer buffer = new StringBuffer();
        
        if (false == checkRepeatSubmission(topic))
        {
            
            try
            {
                
                Topic newTopic = new Topic();
                
                Vector<Topic> allTopic = getAllTopicInfo();
                Topic tempTopic = allTopic.get((allTopic.size() - 1));
                newTopic.setTopicID(tempTopic.getTopicID() + 1);
                
                if (newTopic.getTopicID() == 0)
                {
                    throw new IllegalArgumentException("topic id is not specified.");
                }
                
                newTopic.setProposedBy(topic.getProposedBy());
                
                newTopic.setTitle(topic.getTitle());
                if (newTopic.getTitle().equals(""))
                {
                    throw new IllegalArgumentException("topic title is not specified.");
                }
                
                newTopic.setDescr(topic.getDescription());
                
                newTopic.setAssignedTo(topic.getAssignedTo());
                newTopic.setSupervisorID(topic.getSupervisorID());
                newTopic.setAccepted(topic.isAccepted());
                newTopic.setPreviousExperince(topic.getPreviousExperince());
                
                DBCreator db = new DBCreator();
                try
                {
                    db.addTopic(newTopic);
                }
                finally
                {
                    db.close();
                }
                buffer.append("Topic was added.");
            }
            catch (IllegalArgumentException e)
            {
                buffer.append("Invalid input! " + e.getMessage());
            }
            printReturnToStudent(buffer);
        }
        
    }
    
    public boolean checkRepeatSubmission(Topic topic) throws SqlJetException
    {
        
        boolean reSub = false;
        
        Vector<Topic> allTopic = getAllTopicInfo();
        Topic curTopic = new Topic();
        
        for (int index = 0; index < allTopic.size(); index++)
        {
            
            curTopic = allTopic.get(index);
            
            if (curTopic.getTitle().equals(topic.getTitle()))
            {
                reSub = true;
            }
            
        }
        
        return reSub;
    }
    
    /**
     * find a topic information with a specific ID
     * 
     * @param topicId
     * @return
     * @throws SqlJetException
     */
    public Topic findTopic(final long topicId) throws SqlJetException
    {
        
        StringBuffer buffer = new StringBuffer();
        
        Topic topic = new Topic();
        DBCreator db = new DBCreator();
        try
        {
            topic = db.getTopic(topicId);
        }
        finally
        {
            db.close();
        }
        if (topic == null)
        {
            buffer.append("User with ID '");
            buffer.append(topicId);
            buffer.append("' is not found.");
            printReturnToStudent(buffer);
            return null;
        }
        return topic;
    }
    
    /**
     * get all information in the topics table
     * 
     * @return
     * @throws SqlJetException
     */
    public Vector<Topic> getAllTopicInfo() throws SqlJetException
    {
        
        Vector<Topic> topicVector = new Vector<Topic>();
        
        DBCreator db = new DBCreator();
        
        try
        {
            db.beginTransaction(false);
            try
            {
                ISqlJetCursor cursor;
                cursor = db.getAllTopic();
                try
                {
                    while (!cursor.eof())
                    {
                        Topic topic = new Topic();
                        topic.read(cursor);
                        topicVector.add(topic);
                        cursor.next();
                    }
                    
                }
                finally
                {
                    cursor.close();
                }
                // printShowUsers(db, buffer);
            }
            finally
            {
                db.commit();
            }
        }
        finally
        {
            db.close();
        }
        
        return topicVector;
    }
    
    /**
     * update a project's information
     * 
     * @param buffer
     * @param topic
     * @param vector
     * @throws SqlJetException
     */
    public void processEditRequest(Topic topic) throws SqlJetException
    {
        StringBuffer buffer = new StringBuffer();
        
        try
        {
            Map<String, Object> values = new HashMap<String, Object>();
            
            values.put("proposer", topic.getProposedBy());
            
            String title = topic.getTitle();
            if (title.equals(""))
            {
                throw new IllegalArgumentException("topic's title is not specified.");
            }
            else
            {
                values.put("title", title);
            }
            
            String description = topic.getDescription();
            values.put("description", description);
            
            values.put("assigned_to", topic.getAssignedTo());
            values.put("supervisor_id", topic.getSupervisorID());
            values.put("accepted", topic.isAccepted());
            values.put("pre_exp", topic.getPreviousExperince());
            
            DBCreator db = new DBCreator();
            try
            {
                db.updateTopic(topic.getTopicID(), values);
            }
            finally
            {
                db.close();
            }
            buffer.append("topic was updated.");
        }
        catch (IllegalArgumentException e)
        {
            buffer.append("Invalid input! " + e.getMessage());
        }
    }
    
    /**
     * get the all the unaccepted topics.
     * 
     * @return
     * @throws SqlJetException
     */
    public Vector<Topic> getAllUnacceptedTopic() throws SqlJetException
    {
        Vector<Topic> allTopic = getAllTopicInfo();
        Vector<Topic> unacceptedTopic = new Vector<Topic>();
        Topic curTopic = new Topic();
        
        for (int index = 0; index < allTopic.size(); index++)
        {
            
            curTopic = allTopic.get(index);
            if (curTopic.isAccepted() == false)
            {
                
                unacceptedTopic.add(curTopic);
            }
        }
        
        return unacceptedTopic;
    }
    
    /**
     * get the all the unassigned topics.
     * 
     * @return
     * @throws SqlJetException
     */
    public Vector<Topic> getAllUnassignedTopic() throws SqlJetException
    {
        Vector<Topic> allTopic = getAllTopicInfo();
        Vector<Topic> unacceptedTopic = new Vector<Topic>();
        Topic curTopic = new Topic();
        
        for (int index = 0; index < allTopic.size(); index++)
        {
            
            curTopic = allTopic.get(index);
            if (curTopic.getAssignedTo() == 0)
            {
                
                unacceptedTopic.add(curTopic);
            }
        }
        return unacceptedTopic;
    }
    
    /**
     * remove a project in the topics table.
     * 
     * @param topicId
     */
    public void removeTopic(final long topicId) throws SqlJetException
    {
        
        StringBuffer buffer = new StringBuffer();
        
        if (topicId != 0)
        {
            DBCreator db = new DBCreator();
            try
            {
                db.removeTopic(topicId);
            }
            finally
            {
                db.close();
            }
            buffer.append("topic was removed.");
            printReturnToStudent(buffer);
        }
    }
    
    /**
     * get all the topics that are not proposed by students
     * 
     * @return
     * @throws SqlJetException
     */
    public Vector<Topic> getNonStuTopic() throws SqlJetException
    {
        Vector<Topic> nonStuTopic = new Vector<Topic>();
        Vector<Topic> allTopic = getAllTopicInfo();
        Topic curTopic = new Topic();
        User curUser = new User();
        UserResponder userRespon = new UserResponder();
        
        for (int index = 0; index < allTopic.size(); index++)
        {
            
            curTopic = allTopic.get(index);
            
            curUser = userRespon.findUser(curTopic.getProposedBy());
            
            if (curUser != null)
            {
                
                if (curUser.getRole().equals("Staff") || curUser.getRole().equals("Faculty"))
                {
                    if (0 == curTopic.getAssignedTo())
                    {
                        nonStuTopic.add(curTopic);
                    }
                }
            }
            
        }
        return nonStuTopic;
    }
    
    /**
     * get projects proposed by userID
     * 
     * @param userID
     * @return
     * @throws SqlJetException
     */
    public Vector<Topic> getProposedTopicByUserID(long userID) throws SqlJetException
    {
        
        Vector<Topic> allTopic = getAllTopicInfo();
        Vector<Topic> propTopic = new Vector<Topic>();
        Topic curTopic = new Topic();
        
        for (int index = 0; index < allTopic.size(); index++)
        {
            
            curTopic = allTopic.get(index);
            
            if (curTopic.getProposedBy() == userID)
            {
                
                propTopic.add(curTopic);
            }
        }
        return propTopic;
    }
    
    /**
     * get the student interested topics
     * 
     * @param userID
     * @return
     * @throws SqlJetException
     */
    public Vector<String> getAllTopicsInterestByUserID(final long userID) throws SqlJetException
    {
        
        Vector<String> allTitles = new Vector<String>();
        
        InterestResponder intResponder = new InterestResponder();
        Vector<Interest> allInterest = intResponder.getAllInterestInfo();
        Interest curInterest = new Interest();
        
        Vector<Long> allTopicsID = new Vector<Long>();
        Vector<Topic> allTopic = this.getAllTopicInfo();
        
        Topic curTopic = new Topic();
        
        for (int index = 0; index < allInterest.size(); index++)
        {
            
            curInterest = allInterest.get(index);
            
            if (curInterest.getStudentID() == userID)
            {
                
                allTopicsID.add(curInterest.getInterestID());
            }
        }
        
        long curTopicID;
        
        for (int topIndex = 0; topIndex < allTopicsID.size(); topIndex++)
        {
            curTopicID = allTopicsID.get(topIndex);
            
            for (int count = 0; count < allTopic.size(); count++)
            {
                
                curTopic = allTopic.get(count);
                
                if (curTopic.getTopicID() == curTopicID)
                {
                    
                    allTitles.add(curTopic.getTitle());
                }
            }
        }
        
        return allTitles;
    }
    
  
}