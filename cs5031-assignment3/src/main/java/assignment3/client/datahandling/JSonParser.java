package assignment3.client.datahandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gson.Gson;

/**
 * @author asif this class is responsible for the parsing the string from gson
 *         format to Vector for rendering to the table
 * 
 */
public class JSonParser {

    /**
     * gson object for parsing
     */
    public Gson gson;
    
    public JSonParser()
    {
        gson = new Gson();
    }
    /**
     * @param  gsonToVector
     * @return the vector of vectors string to return the data back
     */
    @SuppressWarnings("unchecked")
    public Vector<Vector<String>> gsonToVector(String gson_string_to_parse) {

        Vector<Vector<String>> model = new Vector<Vector<String>>();

        /* deserializing the Gson string for rendering */
        Vector<Vector<String>> parsingVector = new Vector<Vector<String>>();

        parsingVector = gson.fromJson(gson_string_to_parse, Vector.class);
        List<String> li;
        Vector<String> new_tmp_vec;

        for (int i = 0; i < parsingVector.size(); i++) 
        {
            new_tmp_vec = new Vector<String>();
            li = new ArrayList<String>(parsingVector.get(i));
            for (int j = 0; j < li.size(); j++) 
            {
                new_tmp_vec.add(li.get(j));
            }

            model.add(new_tmp_vec);
        }

        return model;
    }

    /*
     * public Object gsonToObjects(String gson_obj_str) { Vector<Vector<Object>>
     * model = new Vector<Vector<Object>>(); deserializing the Gson string for
     * rendering Vector<Vector<Object>> parsing_gson_vector_str = new
     * Vector<Vector<Object>>(); parsing_gson_vector_str =
     * gson.fromJson(gson_obj_str, Vector.class); List<String> li;
     * Vector<Object> new_tmp_vec; for (int i = 0; i <
     * parsing_gson_vector_str.size(); i++) { new_tmp_vec = new
     * Vector<Object>(); li = new ArrayList<String>(); for (int j = 0; j <
     * li.size(); j++) { new_tmp_vec.add(li.get(j)); } model.add(new_tmp_vec); }
     * return model; }
     */

}
