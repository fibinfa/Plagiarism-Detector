package com.msd.codesniffer;

/**
 * The type Number expression.
 *
 * @author sidharththapar
 * @since 2 /5/18.
 */
public class NumberExpression implements Expression {
    private Integer num;

    /**
     * Gets num.
     *
     * @return the num
     */
    public Integer getNum() {
        return num;
    }

    /**
     * Sets num.
     *
     * @param num the num
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * Instantiates a new Number expression.
     *
     * @param i the number
     */
    public NumberExpression(Integer i) {
        this.num = i;
    }

    @Override
    public String textRepresentation() {
        return this.num.toString();
    }
}
