package com.msd.codesniffer;

/**
 * The type Infix expression.
 *
 * @author sidharththapar
 * @since 2 /5/18.
 */
public class InfixExpression implements Expression {
    /**
     * Gets o.
     *
     * @return the o
     */
    public Operator getO() {
        return o;
    }

    /**
     * Sets o.
     *
     * @param o the o
     */
    public void setO(Operator o) {
        this.o = o;
    }

    /**
     * Gets e 1.
     *
     * @return the e 1
     */
    public Expression getE1() {
        return e1;
    }

    /**
     * Sets e 1.
     *
     * @param e1 the e 1
     */
    public void setE1(Expression e1) {
        this.e1 = e1;
    }

    /**
     * Gets e 2.
     *
     * @return the e 2
     */
    public Expression getE2() {
        return e2;
    }

    /**
     * Sets e 2.
     *
     * @param e2 the e 2
     */
    public void setE2(Expression e2) {
        this.e2 = e2;
    }

    private Operator o;
    private Expression e1;
    private Expression e2;

    /**
     * Instantiates a new Infix expression.
     *
     * @param plus the operator
     * @param one  the first expression
     * @param two  the second expression
     */
    public InfixExpression(Operator plus, Expression one, Expression two) {
        this.o = plus;
        this.e1 = one;
        this.e2 = two;
    }

    @Override
    public String textRepresentation() {
        return this.e1.textRepresentation() +" "+ this.o.textRepresentation()
                +" "+  this.e2.textRepresentation();
    }
}
