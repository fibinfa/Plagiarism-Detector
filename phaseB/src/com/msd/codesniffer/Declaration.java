package com.msd.codesniffer;

/**
 * The type Declaration.
 *
 * @author sidharththapar
 * @since 2 /5/18.
 */
public class Declaration implements Statement {
    private Variable dec;

    /**
     * Instantiates a new Declaration.
     *
     * @param x the variable to be declared
     */
    public Declaration(Variable x) { this.dec = x; }

    @Override
    public String textRepresentation() {
        return "var " + this.dec.textRepresentation() + ";";
    }
}
