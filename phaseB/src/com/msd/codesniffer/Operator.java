package com.msd.codesniffer;

/**
 * The type Operator.
 *
 * @author sidharththapar
 * @since 2 /5/18.
 */
public class Operator {
    private String str;

    /**
     * Gets str.
     *
     * @return the str
     */
    public String getStr() {
        return str;
    }

    /**
     * Sets str.
     *
     * @param str the str
     */
    public void setStr(String str) {
        this.str = str;
    }

    /**
     * Instantiates a new Operator.
     *
     * @param s the operator
     */
    public Operator(String s) { this.str = s; }

    /**
     * Text representation string.
     *
     * @return the string
     */
    public String textRepresentation() {
        return this.str;
    }
}
