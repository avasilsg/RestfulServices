package assignment3.Controller.Interfaces;
/**
 * 
 * @author Stephan Vasilev
 *
 */
public interface IPerson
{
    /**
     * 
     * @return
     */
    String getName();
    /**
     * 
     * @return
     */
    String getAddress();
    /**
     * 
     * @param name
     */
    void setName(String name);
    /**
     * 
     * @param address
     */
    void setAddress(String address);
}
