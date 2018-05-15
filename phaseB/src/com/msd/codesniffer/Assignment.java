package com.msd.codesniffer;

/**
 * assigns an expression to a variable
 *
 * @author sidharththapar
 * @since 2 /5/18.
 */
public class Assignment implements Statement {
    private Variable assign;
    private Expression exp;

    /**
     * Gets assign.
     *
     * @return the assign
     */
    public Variable getAssign() {
        return assign;
    }

    /**
     * Sets assign.
     *
     * @param assign the assign
     */
    public void setAssign(Variable assign) {
        this.assign = assign;
    }

    /**
     * Gets exp.
     *
     * @return the exp
     */
    public Expression getExp() {
        return exp;
    }

    /**
     * Sets exp.
     *
     * @param exp the exp
     */
    public void setExp(Expression exp) {
        this.exp = exp;
    }

    /**
     * Instantiates a new Assignment.
     *
     * @param state the variable
     * @param e     the expression
     */
    public Assignment(Variable state, Expression e) {
        this.assign = state;
        this.exp = e;
    }

    @Override
    public String textRepresentation() {
        return this.assign.textRepresentation() + " = "
                + this.exp.textRepresentation() + ";";
    }
}
