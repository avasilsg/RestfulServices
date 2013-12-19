package assignment3.server.Interfaces;

import java.util.Vector;

import assignment3.Interfaces.Model.Artefacts;
import assignment3.Interfaces.Model.Topic;
import assignment3.Interfaces.Model.User;

public interface IDBManipulator
{
    public User selectUserProfile(String usersID);
    //authenticate
    //select all
    public Vector<Topic> selectAllFromStaffProposed();
    //Pending
    public Vector<Topic> selectAllPendingProposed();
    
//    public Vector<String> getInterestedProjects(String userID);
    public Vector<String> getInterestedProjects(String userID);

    //Project Proposition
    public void proposedProject(Topic topic, Artefacts artefact);
    public Vector<Topic> getProposedProject(String userID);
    public String getReferences(String topicID);
    
  
    //Assign Project to Student
    public void assignProjectToStudent(String topicId, String userID);
    public void acceptProject(String topicId, String userID);
}
