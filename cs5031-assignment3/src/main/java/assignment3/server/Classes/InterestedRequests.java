package assignment3.server.Classes;

import java.util.Vector;

import javax.ws.rs.Path;

import org.tmatesoft.sqljet.core.SqlJetException;

import assignment3.Controller.Interfaces.GSonParsing;
import assignment3.Interfaces.Model.ChatInformation;
import assignment3.Interfaces.Model.Interest;
import assignment3.Interfaces.Model.Topic;
import assignment3.Interfaces.Model.DataBase.ChatResponder;
import assignment3.Interfaces.Model.DataBase.DBManipulator;
import assignment3.Interfaces.Model.DataBase.InterestResponder;
import assignment3.server.Interfaces.IInterestRequests;

@Path("/interestedTopics")
public class InterestedRequests implements IInterestRequests
{
    private InterestResponder intResp;
    private GSonParsing    gsonParser;
    private DBManipulator  dbHandle;

    public InterestedRequests() throws SqlJetException
    {
        gsonParser = new GSonParsing();
        intResp = new InterestResponder();
        dbHandle = new DBManipulator();
    }

    @Override
    public String getAllInterestedObject(String id) 
    {
        if (null == id)
        {
            throw new NullPointerException("Wrong parameter.");
        }
        Vector<Vector<String>> result = new Vector<Vector<String>>(); 
        Vector<String>   container = new Vector<String>();

        id = id.replaceAll("\"", "");
        Vector<Topic> topics = new Vector<Topic>();

        topics .addAll(dbHandle.selectAllFromStaffProposed());           
        for(Topic topic: topics)
        {
            if (Long.parseLong(id) == topic.getTopicID())
            {
                container.add(topic.getTitle());
            }
        }
        Vector<Interest> vectInterested;
        try
        {
            vectInterested = intResp.getAllInterestInfo();
            for(Interest interest: vectInterested)
            {
                if (Long.parseLong(id) == interest.getTopicID())
                {
                    container.add(String.valueOf(interest.getStudentID()));
                }
            }
        }
        catch (SqlJetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result.add(container);
        String ret_strString = gsonParser.parseToJSon(result);
        return ret_strString;
    }

    @Override
    public void addComment(String id, String content)
    {
        if (null == id)
        {
            throw new NullPointerException("Wrong parameter");
        }
        
        ChatResponder chatResponder = new ChatResponder();
        try
        {
            Vector<ChatInformation> chatInformations = chatResponder.getAllChatInfor();
            for(ChatInformation chat : chatInformations)
            {
                if (Long.parseLong(id) == chat.getTopicID())
                {
                    chat.setChatInfor(content);
                    chatResponder.processEditRequest(chat);
                    break;
                }
            }
        }
        catch (SqlJetException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getComments(String id)
    {
        id = id.replaceAll("\"", "");
        ChatResponder chatResponder = new ChatResponder();
        try
        {
            Vector<ChatInformation> chatInformations = chatResponder.getAllChatInfor();
            for(ChatInformation chat : chatInformations)
            {
                if (Long.parseLong(id) == chat.getTopicID())
                {
                    return chat.getChatInfor();
                }
            }
        }
        catch (SqlJetException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    
}
