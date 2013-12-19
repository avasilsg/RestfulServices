package assignment3.Interfaces.Model;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

public class User {
	
	public long userID;
	public String role;
	public String name;
	public String personName;
	public String address;
	public String email;
	public String password;
	public String researchInterests;
	
	public User () {
		
	}
	
	public User(long userID, String role, String name, String personName, String address,
			String email, String password, String researchInterests) {
		
		this.userID = userID;
		this.role = role;
		this.name = name;
		this.personName = personName;
		this.address = address;
		this.email = email;
		this.password = password;
		this.researchInterests = researchInterests;
	}
	
	public void read(ISqlJetCursor cursor) throws SqlJetException {
		
        this.userID = cursor.getInteger("user_id");
		this.role = cursor.getString("role");
		this.name = cursor.getString("name");
		this.personName = cursor.getString("personName");
		this.address = cursor.getString("address");
		this.email = cursor.getString("email");
		this.password = cursor.getString("password");
		this.researchInterests = cursor.getString("researchInterests");
	}
	
	 public String getPasswordString()
	    {
	        return this.password;
	    }

	    public void setPasswordString(String passwordString)
	    {
	        this.password = passwordString;
	    }

	    public String getName()
	    {
	        return name;
	    }
   
	    public String getAddress()
	    {
	        return address;
	    }
	    
	    public void setName(String name)
	    {
	        this.name = name;
	    }
	    
	    public void setPersonName(String personName) {
	    	this.personName = personName;
	    }
	    
	    public String getPersonName() {
	    	return this.personName;
	    }
	    
	    public void setAddress(String address)
	    {
	        this.address = address;
	    }

	    public long getUserID()
	    {
	        return userID;
	    }

	    public void setUserID(long userID)
	    {
	        this.userID = userID;
	    }

	    public String getRole()
	    {
	        return role;
	    }

	    public void setRole(String role)
	    {
	        this.role = role;
	    }

	    public String getEmail()
	    {
	        return email;
	    }

	    public void setEmail(String email)
	    {
	        this.email = email;
	    }
	    
	    public void setResInterest(String res_interest) {
	    	this.researchInterests = res_interest;
	    }
	    
	    public String getResInterest() {
	        return this.researchInterests;
	    }
}
