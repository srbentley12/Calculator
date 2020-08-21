/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.util.LinkedList;

/**
 *
 * @author Steve
 */
public class Operation {

    String operator;
    Double number;
    Double runningTotal;

    /**
     *
     * @param operator
     * @param number
     */
    public Operation(String operator, Double number) {
        this.operator = operator;
        this.number = number;
    }

    /**
     *
     * @return
     */
    public double addition() {
        return runningTotal + number;
    }

    /**
     *
     * @return
     */
    public double subtraction() {
        return runningTotal - number;
    }

    /**
     *
     * @return
     */
    public double multiplication() {
        return runningTotal * number;
    }

    /**
     *
     * @return
     */
    public double division() {
        return runningTotal / number;
    }

    /**
     *
     * @param runningTotal
     * @return
     */
    public double operate(Double runningTotal) {
        this.runningTotal = runningTotal;
        switch(operator){
            case "a":
                return addition();
            case "s":
                return subtraction();
            case "m":
                return multiplication();
             default: //case "d":
                return division();
        }
    }
}
