package assignment3.Interfaces.Model;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

public class Topic {

    private long topicID;
    private long proposedBy;
    private String title;
    private String description;
    private long supervisorID;
    private long assignedTo;
    private boolean accepted;
    private boolean prevExperience;

    
    public Topic () {
        
    }
    
    public Topic (long topicID, long proposer, String title, String description, 
            long assigned_to, long supervisor_id, boolean isAccepted, boolean pre_exp) {
        
        this.topicID = topicID;
        this.proposedBy = proposer;
        this.title = title;
        this.description = description;
        this.assignedTo = assigned_to;
        this.supervisorID = supervisor_id;
        this.accepted = isAccepted;
        this.prevExperience = pre_exp;
    }
    
    public void read(ISqlJetCursor cursor) throws SqlJetException {
        
        this.topicID = cursor.getInteger("topic_id");
        this.proposedBy = cursor.getInteger("proposer");
        this.title = cursor.getString("title");
        this.description = cursor.getString("description");
        this.assignedTo = cursor.getInteger("assigned_to");
        this.supervisorID = cursor.getInteger("supervisor_id");
        this.accepted = cursor.getBoolean("accepted");
        this.prevExperience = cursor.getBoolean("pre_exp");

    }
    
    public String getTitle()
    {
        return this.title;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
    public boolean getPreviousExperince()
    {
        return this.prevExperience;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public void setDescr(String description)
    {
        this.description = description;
    }
       
    public void setPreviousExperince(boolean flag)
    {
        this.prevExperience = flag;;
   
    }
    
    public long getProposedBy()
    {
        return proposedBy;
    }
    
    public void setProposedBy(long proposedBy)
    {
        this.proposedBy = proposedBy;
    }
    
    public long getTopicID()
    {
        return topicID;
    }
    
    public void setTopicID(long topicID)
    {
        this.topicID = topicID;
    }
    
    public long getSupervisorID()
    {
        return supervisorID;
    }
    
    public void setSupervisorID(long supervisorID)
    {
        this.supervisorID = supervisorID;
    }
    
    public long getAssignedTo()
    {
        return assignedTo;
    }
    
    public void setAssignedTo(long assignedTo)
    {
        this.assignedTo = assignedTo;
    }
    
    public boolean isAccepted()
    {
        return this.accepted;
    }
    
    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }
}
