package assignment3.client.core.Interfaces;


/**
 * @author asif this is a singelton class responsible for storing username and
 *         password
 * 
 */
public class Singleton {
    private static final Singleton INSTANCE = new Singleton();

    /**
     * username for login
     */
    public String username;
    /**
     * password for login
     */
    public String password;

    /**
     * prevents he class for initiating
     */
    private Singleton() {

    }

    /**
     * @return INSTANCE of the class
     */
    public static Singleton getInstance() {
        return INSTANCE;
    }
}
