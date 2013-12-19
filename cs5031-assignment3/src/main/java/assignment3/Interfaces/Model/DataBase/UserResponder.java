package assignment3.Interfaces.Model.DataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

import assignment3.Interfaces.Model.Interest;
import assignment3.Interfaces.Model.User;

public class UserResponder {

	private void printReturnToStudent(StringBuffer buffer) {
		buffer.append("<hr><a href='/'>Return to student table</a>");
	}
	
	/**
	 * process add request from the server
	 * @param buffer
	 * @param vector
	 * @throws SqlJetException
	 */
	public void processAddRequest(User user) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer(); 
		
		try {

			User newUser = new User();
			
			newUser.setUserID(user.getUserID());
			if (newUser.getUserID() == 0) {
				throw new IllegalArgumentException("user id is not specified.");
			}
			
			newUser.setRole(user.getRole());
	
			newUser.setName(user.getName());
			if (newUser.getName().equals("")) {
				throw new IllegalArgumentException("user name is not specified.");
			}
			
			newUser.setPersonName(user.getPersonName());
			if (newUser.getPersonName().equals("")) {
				throw new IllegalArgumentException("user person_name is not specified.");
			}
			
			newUser.setAddress(user.getAddress());
			newUser.setEmail(user.getEmail());
			newUser.setPasswordString(user.getPasswordString());
			newUser.setResInterest(user.getResInterest());
			
			DBCreator db = new DBCreator();
			try {
				db.addUser(newUser);
			} finally {
				db.close();
			}
			buffer.append("User was added.");
		} catch (IllegalArgumentException e) {
			buffer.append("Invalid input! " + e.getMessage());
		}
		printReturnToStudent(buffer);
	}
	
	/**
	 * find an user with a specific ID
	 * @param buffer
	 * @param vector
	 * @return
	 * @throws SqlJetException
	 */
	
	public User findUser(final long userID) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		User user = new User();
		DBCreator db = new DBCreator();
		try {
			user = db.getUser(userID);
		} finally {
			db.close();
		}
		if (user == null) {
			buffer.append("User with ID '");
			buffer.append(userID);
			buffer.append("' is not found.");
			printReturnToStudent(buffer);
			return null;
		}
		return user;
	}
	
	/**
	 * @param buffer
	 * @param name
	 * @return
	 * @throws SqlJetException
	 */
	public Vector<User> getAllUserInfo () throws SqlJetException {
		
		Vector<User> userVector = new Vector<User>();
		
		DBCreator db = new DBCreator();
		
		try {
			db.beginTransaction(false);
			try {
				ISqlJetCursor cursor;
				cursor = db.getAllUser();
				try {
					while (!cursor.eof()) {
						User user = new User();
						user.read(cursor);
			            userVector.add(user);
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
		
		return userVector;
	}
	
	/**
	 * update a student's profile
	 * @param buffer
	 * @param student
	 * @param vector
	 * @throws SqlJetException
	 */
	public void processEditRequest(User user)
			throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		try {
			
			Map<String, Object> values = new HashMap<String, Object>();

			String role = user.getRole();
			values.put("role", role);
			
			String name = user.getName();
			if (name.equals("")) {
				throw new IllegalArgumentException("user's name is not specified.");
			} else {
				values.put("name", name);
			}
			
			String personName = user.getPersonName();
			if (personName.equals("")) {
				throw new IllegalArgumentException("user's person_name is not specified.");
			} else {
				values.put("personName", personName);
			}
			
			String address = user.getAddress();
			values.put("address", address);
			
			String email = user.getEmail();
			values.put("email", email);
			
			String password = user.getPasswordString();
			values.put("password", password);
			
			String researchInterests = user.getResInterest();
			values.put("researchInterests", researchInterests);
			
			DBCreator db = new DBCreator();
			try {
				db.updateUser(user.getUserID(), values);
			} finally {
				db.close();
			}
			buffer.append("user was updated.");
		} catch (IllegalArgumentException e) {
			buffer.append("Invalid input! " + e.getMessage());
		}
	}

	/**
	 * get all the students infor who are interested in a specific topic
	 * @param userID
	 * @param topicID
	 * @return
	 */
	public Vector<User> getAllStuInterestedInSpeTopic (long userID, long topicID) {
	    
//		InterestResponder intResponder = new InterestResponder();
		Vector<Interest> allInterest = new Vector<Interest>();
		Vector<User> interestUser = new Vector<User>();
		Interest curInterest = new Interest();
		User curUser = new User();
		
//		StringBuffer buffer = new StringBuffer();
		
		for (int index = 0; index < allInterest.size(); index ++) {
			
			curInterest = allInterest.get(index);
			
			if (curInterest.getTopicID() == topicID) {
				try {
					curUser = this.findUser(userID);
				} catch (SqlJetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				interestUser.add(curUser);
			}
		}
		
		return interestUser;
	}
	//Vector<Interest> getAllInterestInfo ()
	/*
	public void removeStudent(StringBuffer buffer, Vector<String> vector) throws SqlJetException {
		Student student = findStudent(buffer, vector);
		if (student != null) {
			ProjManageDB db = new ProjManageDB();
			try {
				db.removeStudent(student.stu_id);
			} finally {
				db.close();
			}
			buffer.append("Student was removed.");
			printReturnToStudent(buffer);
		}
	}
	*/
    //String searchForUserPassoword(String userName, String password)
}
