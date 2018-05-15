package com.msd.codesniffer;

/**
 * The type Variable expression.
 *
 * @author sidharththapar sidharththapar
 * @since 2/5/18
 */
public class VariableExpression implements Expression {
    private Variable varExp;

    /**
     * Gets var exp.
     *
     * @return the var exp
     */
    public Variable getVarExp() {
        return varExp;
    }

    /**
     * Sets var exp.
     *
     * @param varExp the var exp
     */
    public void setVarExp(Variable varExp) {
        this.varExp = varExp;
    }

    /**
     * Instantiates a new Variable expression.
     *
     * @param exp the VariableExpression
     */
    public VariableExpression(Variable exp){ this.varExp = exp;}

    @Override
    public String textRepresentation() {
        return this.varExp.textRepresentation();
    }
}
