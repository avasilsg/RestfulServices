package assignment3.Interfaces.Model;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

public class Artefacts
{
    private long artefactsID;
    private long topicID;
    private String artefacts;
    
    public Artefacts () {
        
    }
    
    public Artefacts (long artefactsID, long topicID, String artefacts) {
        
        this.artefactsID = artefactsID;
        this.topicID = topicID;
        this.artefacts = artefacts;
    }
    
    public void read(ISqlJetCursor cursor) throws SqlJetException {
        
        this.artefactsID = cursor.getInteger("artefact_id");
        this.topicID = cursor.getInteger("topic_id");
        this.artefacts = cursor.getString("refs");
    }
    
    public String getArtefacts()
    {
        return artefacts;
    }
    
    public void setArtefacts(String artefacts)
    {
        this.artefacts = artefacts;
    }
    
    public long getArtefactsID()
    {
        return artefactsID;
    }
    
    public void setArtefactsID(long artefactsID)
    {
        this.artefactsID = artefactsID;
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

