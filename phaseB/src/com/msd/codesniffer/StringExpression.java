package com.msd.codesniffer;

/**
 * The type String expression.
 *
 * @author sidharththapar
 * @since 2 /5/18.
 */
public class StringExpression implements Expression {
    /**
     * Gets s.
     *
     * @return the s
     */
    public String getS() {
        return s;
    }

    /**
     * Sets s.
     *
     * @param s the s
     */
    public void setS(String s) {
        this.s = s;
    }

    private String s;

    /**
     * Instantiates a new String expression.
     *
     * @param str the str
     */
    public StringExpression(String str){ this.s = str; }

    @Override
    public String textRepresentation() {
        return this.s;
    }
}
