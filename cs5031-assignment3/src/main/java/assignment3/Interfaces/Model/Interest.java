package assignment3.Interfaces.Model;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;


public class Interest
{
    private long interestID;
    private long topicID;
    private long studentID;
    
    public Interest () {
        
    }
    
    public Interest (long interestID, long topicID, long studentID) {
        
        this.interestID = interestID;
        this.topicID = topicID;
        this.studentID = studentID;
    }
    
    public void read(ISqlJetCursor cursor) throws SqlJetException {
        
        this.interestID = cursor.getInteger("interest_id");
        this.studentID = cursor.getInteger("student_id");
        this.topicID = cursor.getInteger("topic_id");
    }
    
    public long getInterestID () {
        return this.interestID;
    }
    
    public void setInterestID (long interestID) {
        this.interestID = interestID;
    }
    
    public long getStudentID()
    {
        return studentID;
    }
    
    public void setStudentID(long studentID)
    {
        this.studentID = studentID;
    }
    
    public long getTopicID()
    {
        return topicID;
    }
    
    public void setTopicID(long topicID)
    {
        this.topicID = topicID;
    }
}
