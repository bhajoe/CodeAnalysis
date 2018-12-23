/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joebe.codeAnalysis.data;

/**
 *
 * @author bayup
 */
public class operandOperator {
    private String operandOperator;
    private int line;
    private int column;

    public operandOperator(String operand, int line, int column) {
        this.operandOperator = operand;
        this.line = line;
        this.column = column;
    }

    public String getOperandOperator() {
        return operandOperator;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
