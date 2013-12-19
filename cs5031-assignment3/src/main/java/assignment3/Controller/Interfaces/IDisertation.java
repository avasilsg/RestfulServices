package assignment3.Controller.Interfaces;
/**
 * @author Stephan Vasilev
 * 
 *
 */
public interface IDisertation
{
    /**
     * 
     * @return
     */
    String getTitle();
    /**
     * 
     * @return
     */
    String getDescription();
    /**
     * 
     * @return
     */
    boolean getPreviousExperince();
    /**
     * 
     * @param title
     */
    void setTitle(String title);
    /**
     * 
     * @param description
     */
    void setDescr(String description);
    /**
     * 
     * @param flag
     */
    void setPreviousExperince(boolean flag);
}
