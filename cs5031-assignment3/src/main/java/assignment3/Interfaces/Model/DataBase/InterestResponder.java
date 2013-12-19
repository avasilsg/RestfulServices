package assignment3.Interfaces.Model.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

import assignment3.Interfaces.Model.Interest;

/**
 * interface for operating on interest table.
 * @author Jiang
 *
 */
public class InterestResponder {

	private void printReturnToStudent(StringBuffer buffer) {
		buffer.append("<hr><a href='/'>Return to student table</a>");
	}
	
	/**
	 * process add request from the server
	 * @param buffer
	 * @param vector
	 * @throws SqlJetException
	 */
	public void processAddRequest(Interest interest) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		
		try {

			Interest newInterest = new Interest();
			
			Vector<Interest> allInterest = getAllInterestInfo();
			newInterest = allInterest.get(allInterest.size() - 1);
			
			newInterest.setInterestID(newInterest.getInterestID() + 1);
			
			if (newInterest.getInterestID() == 0) {
				throw new IllegalArgumentException("interest id is not specified.");
			}
			
			newInterest.setTopicID(interest.getTopicID());
			newInterest.setStudentID(interest.getStudentID());
	
			DBCreator db = new DBCreator();
			try {
				db.addInterest(newInterest);
			} finally {
				db.close();
			}
			buffer.append("Interest was added.");
		} catch (IllegalArgumentException e) {
			buffer.append("Invalid input! " + e.getMessage());
		}
		printReturnToStudent(buffer);
	}
	
	/**
	 * update an interest's information with a specific ID.
	 * @param buffer
	 * @param artefact
	 * @param vector
	 * @throws SqlJetException
	 */
	public void processEditRequest(Interest interest) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		try {
			
			Map<String, Object> values = new HashMap<String, Object>();
			
			values.put("topic_id", interest.getTopicID());
			values.put("student_id", interest.getStudentID());
			
			DBCreator db = new DBCreator();
			try {
				db.updateInterest(interest.getInterestID(), values);
			} finally {
				db.close();
			}
			buffer.append("interest was updated.");
		} catch (IllegalArgumentException e) {
			buffer.append("Invalid input! " + e.getMessage());
		}
	}

	/**
	 * get all the interests' information
	 * @return
	 * @throws SqlJetException
	 */
	public Vector<Interest> getAllInterestInfo () throws SqlJetException {
		
		Vector<Interest> artf_vector = new Vector<Interest>();
		
		DBCreator db = new DBCreator();
		
		try {
			db.beginTransaction(false);
			try {
				ISqlJetCursor cursor;
				cursor = db.getAllInterest();
				try {
					while (!cursor.eof()) {
						Interest interest = new Interest();
						interest.read(cursor);
						artf_vector.add(interest);
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
		
		return artf_vector;
	}
	
	/**
	 * remove an interest with a specific id.
	 *
	 * @param interestID
	 *
	 */
	public void removeInterest(final long interestID) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		if (interestID != 0) {
			DBCreator db = new DBCreator();
			try {
				db.removeInterest(interestID);
			} finally {
				db.close();
			}
			buffer.append("interest was removed.");
			printReturnToStudent(buffer);
		}
	}
}
