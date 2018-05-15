package com.msd.codesniffer;

/**
 * The type Variable.
 *
 * @author sidharththapar
 * @since 2 /5/18.
 */
public class Variable {
    private String var;

    /**
     * Gets var.
     *
     * @return the var
     */
    public String getVar() {
        return var;
    }

    /**
     * Sets var.
     *
     * @param var the var
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * Instantiates a new Variable.
     *
     * @param x the variable value
     */
    public Variable(String x) {
        this.var = x;
    }

    /**
     * Text representation string.
     *
     * @return the string
     */
    public String textRepresentation() {
        return this.var;
    }
}
