package assignment3.client.datahandling;

public class ProposeobjectData {

    /**
     * 
     */
    private String projectTitle;

    /**
     * userID
     */
    private String userID;
    /**
     * project description
     */
    private String txt_project_description;
    /**
     * project aims
     */
    private String txt_project_aims;
    /**
     * project references
     */
    private String txt_references;
    /**
     * name for contact
     */
    private String txt_name;
    /**
     * value of the comobox
     */
    private String proposedByString;

    /**
     * Concatenated value of the deliverables
     */
    private String deliverables;

    /**
     * flag for previous require requires or not
     */
    private String prev_expe_require;

    /**
     * @return String getting the flag value
     */
    public String getPrev_expe_require() {
        return prev_expe_require;
    }

    /**
     * @param prev_expe_require setting the flag value
     */
    public void setPrev_expe_require(String prev_expe_require) {
        this.prev_expe_require = prev_expe_require;
    }

    /**
     * @return string Concatenated deliverables value
     */
    public String getDeliverables() {
        return deliverables;
    }

    /**
     * @param deliverables setting the deliverables
     */
    public void setDeliverables(String deliverables) {
        this.deliverables = deliverables;
    }

    /**
     * @return string the current value of the comobox
     */
    public String getProposedBy() {
        return proposedByString;
    }

    /**
     * @param comobox_value setting the value of the comobox
     */
    public void setProposedBy(String comobox_value) {
        this.proposedByString = comobox_value;
    }

    /**
     * @return string returning id
     */
    public String getID() {
        return userID;
    }

    /**
     * @param setID setting the id
     */
    public void setID(String txt_student_id) {
        this.userID = txt_student_id;
    }

    /**
     * @return string project description
     */
    public String getTxt_project_description() {
        return txt_project_description;
    }

    /**
     * @param txt_project_description setting the project description
     */
    public void setTxt_project_description(String txt_project_description) {
        this.txt_project_description = txt_project_description;
    }

    /**
     * @return string getting project names
     */
    public String getTxt_project_aims() {
        return txt_project_aims;
    }

    /**
     * @param txt_project_aims setting project aims
     */
    public void setTxt_project_aims(String txt_project_aims) {
        this.txt_project_aims = txt_project_aims;
    }

    /**
     * @return string returning the references
     */
    public String getTxt_references() {
        return txt_references;
    }

    /**
     * @param txt_references setting the references
     */
    public void setTxt_references(String txt_references) {
        this.txt_references = txt_references;
    }

    /**
     * @return string getting the name
     */
    public String getTxt_name() {
        return txt_name;
    }

    /**
     * @param txt_name setting the name
     */
    public void setTxt_name(String txt_name) {
        this.txt_name = txt_name;
    }
    
    public String getProjectTitle()
    {
        return this.projectTitle;
    }

    public void setProjectTitle(String projectTitle)
    {
        this.projectTitle = projectTitle;
    }
}
