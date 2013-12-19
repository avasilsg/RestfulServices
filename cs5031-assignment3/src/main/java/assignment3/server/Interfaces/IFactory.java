package assignment3.server.Interfaces;

import java.io.InvalidObjectException;
import java.util.Vector;

public interface IFactory
{

        /**
         * Creates an instance of {@link MyClient Side Object}.
         * 
         * @param jsonObject as a string
         * @return TODO
         * @throws InvalidObjectException 
         */
         Vector<Vector<String>> parserFromJsonVector(Object object) throws InvalidObjectException;

        /**
         * Creates an instance of {@link IFaculty or IStudent}.
         * 
         * @param IStudent or IFaculty
         * @return String (json) object
         */
         Object parseFromJSon(String json);

        String parseToJSon(Vector<Vector<String>> object);
}
