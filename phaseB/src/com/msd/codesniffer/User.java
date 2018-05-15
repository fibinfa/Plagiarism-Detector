package com.msd.codesniffer;

/**
 * The type User.
 *
 * @author sidharththapar
 * @since 2 /11/18.
 */
public class User {
	
	private int id;
    private String fName;
    private String lName;
    private String email;
    private Role userType;
    
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

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getfName() {
        return fName;
    }

    /**
     * Sets name.
     *
     * @param fName the f name
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getlName() {
        return lName;
    }

    /**
     * Sets name.
     *
     * @param lName the l name
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets user type.
     *
     * @return the user type
     */
    public Role getUserType() {
        return userType;
    }

    /**
     * Sets user type.
     *
     * @param userType the user type
     */
    public void setUserType(Role userType) {
        this.userType = userType;
    }

    
}


