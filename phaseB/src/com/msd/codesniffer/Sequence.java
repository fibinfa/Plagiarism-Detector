package com.msd.codesniffer;

/**
 * The type Sequence.
 *
 * @author sidharththapar
 * @since 2 /5/18.
 */
public class Sequence implements Statement {

    private Statement declaration;
    private Statement assignment;

    /**
     * Gets declaration.
     *
     * @return the declaration
     */
    public Statement getDeclaration() {
        return declaration;
    }

    /**
     * Sets declaration.
     *
     * @param declaration the declaration
     */
    public void setDeclaration(Statement declaration) {
        this.declaration = declaration;
    }

    /**
     * Gets assignment.
     *
     * @return the assignment
     */
    public Statement getAssignment() {
        return assignment;
    }

    /**
     * Sets assignment.
     *
     * @param assignment the assignment
     */
    public void setAssignment(Statement assignment) {
        this.assignment = assignment;
    }

    /**
     * Instantiates a new Sequence.
     *
     * @param dec    the dec
     * @param assign the assign
     */
    public Sequence(Statement dec, Statement assign) {
        this.declaration = dec;
        this.assignment = assign;
    }

    @Override
    public String textRepresentation() {
        return this.declaration.textRepresentation()
                + " " + this.assignment.textRepresentation();
    }
}
