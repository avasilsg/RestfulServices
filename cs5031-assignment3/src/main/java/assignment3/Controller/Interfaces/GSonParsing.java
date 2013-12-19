package assignment3.Controller.Interfaces;

import java.io.InvalidObjectException;
import java.util.Vector;

import com.google.gson.Gson;

import assignment3.Interfaces.Model.ProposeobjectData;
import assignment3.server.Interfaces.IFactory;

public class GSonParsing implements IFactory
{
    public Gson gSonParser;

    public GSonParsing()
    {
        gSonParser = new Gson() ;
    }
    @Override
    public Object parseFromJSon(String json)
    {
        ProposeobjectData deserializeData = gSonParser.fromJson(json, ProposeobjectData.class);
        return deserializeData;
    }

    @Override
    public String parseToJSon(Vector<Vector<String>> object)
    {
        return gSonParser.toJson(object);
    }
    @SuppressWarnings("unchecked")
    @Override
    public Vector<Vector<String>> parserFromJsonVector(Object object)
            throws InvalidObjectException
    {
        return gSonParser.fromJson((String) object, Vector.class);
    }
    
    
}
