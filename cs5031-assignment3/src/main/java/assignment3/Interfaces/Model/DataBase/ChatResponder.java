package assignment3.Interfaces.Model.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

import assignment3.Interfaces.Model.ChatInformation;

public class ChatResponder {

	private void printReturnToStudent(StringBuffer buffer) {
		buffer.append("<hr><a href='/'>Return to student table</a>");
	}
	
	/**
	 * process add request from the server
	 * @param buffer
	 * @param vector
	 * @throws SqlJetException
	 */
	public void processAddRequest(ChatInformation chatInfor) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		try {

			ChatInformation newInfor = new ChatInformation();
			
			Vector<ChatInformation> allChat = getAllChatInfor();
			newInfor = allChat.get((allChat.size() - 1));
			newInfor.setInforID(newInfor.getInforID() + 1);
	
			newInfor.setTopicID(chatInfor.getTopicID());
			newInfor.setChatInfor(chatInfor.getChatInfor());
			
			DBCreator db = new DBCreator();
			try {
				db.addChatInfor(newInfor);
			} finally {
				db.close();
			}
			buffer.append("comments was added.");
		} catch (IllegalArgumentException e) {
			buffer.append("Invalid input! " + e.getMessage());
		}
		printReturnToStudent(buffer);
	}
	
	/**
	 * update a comment information with a specific ID.
	 * @param buffer
	 * @param artefact
	 * @param vector
	 * @throws SqlJetException
	 */
	public void processEditRequest(ChatInformation charInfor) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		try {
			
			Map<String, Object> values = new HashMap<String, Object>();
			
			values.put("topic_id", charInfor.getTopicID());
			values.put("chat_infor", charInfor.getChatInfor());
			
			DBCreator db = new DBCreator();
			try {
				db.updateChatInfor(charInfor.getInforID(), values);
			} finally {
				db.close();
			}
			buffer.append("comment was updated.");
		} catch (IllegalArgumentException e) {
			buffer.append("Invalid input! " + e.getMessage());
		}
	}

	/**
	 * get all the comments information
	 * @return
	 * @throws SqlJetException
	 */
	public Vector<ChatInformation> getAllChatInfor () throws SqlJetException {
		
		Vector<ChatInformation> inforVector = new Vector<ChatInformation>();
		
		DBCreator db = new DBCreator();
		
		try {
			db.beginTransaction(false);
			try {
				ISqlJetCursor cursor;
				cursor = db.getAllChatInfor();
				try {
					while (!cursor.eof()) {
						ChatInformation chatInfor = new ChatInformation();
						chatInfor.read(cursor);
						inforVector.add(chatInfor);
			            cursor.next();
					}
					
				} finally {
					cursor.close();
				}
				//printShowUsers(db, buffer);
			} finally {
				db.commit();
			}
		} finally {
			db.close();
		}
		
		return inforVector;
	}
	
	/**
	 * remove a chat information with a specifics ID
	 * @param chatId
	 * @throws SqlJetException
	 */
	public void removeChatInfor(final long chatId) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		if (chatId != 0) {
			DBCreator db = new DBCreator();
			try {
				db.removeChatInfor(chatId);
			} finally {
				db.close();
			}
			buffer.append("comment was removed.");
			printReturnToStudent(buffer);
		}
	}
}
