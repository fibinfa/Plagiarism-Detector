package com.msd.codesniffer;

/**
 * The type Project.
 *
 * @author sidharththapar
 * @since 2/11/18.
 */
public class Project {
    /**
     * The Id.
     */
    private int id;

    /**
     * The Student id.
     */

    private int studentID;

    /**
     * The Git url.
     */
    private String gitURL;


    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getGitURL() {
        return gitURL;
    }

    public void setGitURL(String gitURL) {
        this.gitURL = gitURL;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }
}
