package uk.ac.standrews.cs.cs5031.practice03;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

import assignment3.Interfaces.Model.Interest;
import assignment3.Interfaces.Model.User;
import assignment3.Interfaces.Model.DataBase.DBCreator;

public class dbUserResponder 
{

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

			User new_user = new User();
			
			new_user.setUserID(user.getUserID());
			if (new_user.getUserID() == 0) {
				throw new IllegalArgumentException("user name is not specified.");
			}
			
			new_user.setRole(user.getRole());
	
			new_user.setName(user.getName());
			if (new_user.getName().equals("")) {
				throw new IllegalArgumentException("user name is not specified.");
			}
			
			new_user.setAddress(user.getAddress());
			new_user.setEmail(user.getEmail());
			new_user.setPasswordString(user.getPasswordString());
			
			DBCreator db = new DBCreator();
			try {
				db.addUser(new_user);
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
	
	public User findUser(final long user_id) throws SqlJetException {
		
		StringBuffer buffer = new StringBuffer();
		
		User user = new User();
		DBCreator db = new DBCreator();
		try {
			user = db.getUser(user_id);
		} finally {
			db.close();
		}
		if (user == null) {
			buffer.append("User with ID '");
			buffer.append(user_id);
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
		
		Vector<User> user_vector = new Vector<User>();
		
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
			            user_vector.add(user);
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
		
		return user_vector;
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
			
			String address = user.getAddress();
			values.put("address", address);
			
			String email = user.getEmail();
			values.put("email", email);
			
			String password = user.getPasswordString();
			values.put("password", password);
			
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
	    
		Vector<Interest> allInterest = new Vector<Interest>();
		Vector<User> interestUser = new Vector<User>();
		Interest cur_interest = new Interest();
		User cur_user = new User();
		
		for (int index = 0; index < allInterest.size(); index ++) {
			
			cur_interest = allInterest.get(index);
			
			if (cur_interest.getTopicID() == topicID) {
				try {
					cur_user = this.findUser(userID);
				} catch (SqlJetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				interestUser.add(cur_user);
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
