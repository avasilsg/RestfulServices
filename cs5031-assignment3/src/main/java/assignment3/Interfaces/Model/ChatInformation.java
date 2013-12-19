package assignment3.Interfaces.Model;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

public class ChatInformation {

	private long infor_id;
	private long topic_id;
	private String chat_infor;
	
	public ChatInformation () 
	{
		
	}
	
	public ChatInformation (long infor_id, long topic_id, String chat_infor) {
		
		this.infor_id = infor_id;
		this.topic_id = topic_id;
		this.chat_infor = chat_infor;
	}
	
	public void read(ISqlJetCursor cursor) throws SqlJetException {
		
        this.infor_id = cursor.getInteger("infor_id");
        this.topic_id = cursor.getInteger("topic_id");
		this.chat_infor = cursor.getString("chat_infor");
	}
	
	public void setInforID (long infor_id) {
	    this.infor_id = infor_id;
	}
	
	public long getInforID () {
	    return this.infor_id;
	}
	
	public long getTopicID () {
	    return this.topic_id;
	}
	
	public void setTopicID (long topic_id) {
	    this.topic_id = topic_id;
	}
	public void setChatInfor (String chat_infor) {
	    this.chat_infor = chat_infor;
	}
	
	public String getChatInfor() {
	    return this.chat_infor;
	}
	/*
projManageDb.createTable("create table chats(infor_id integer primary key, " +
                			"chat_infor text)"); 
	 */
}
