/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joebe.codeAnalysis.data;

import com.github.javaparser.ast.expr.BinaryExpr;
import java.util.ArrayList;

/**
 *
 * @author bayup
 */
public class predicateNode {
    private String type;
    private ArrayList<BinaryExpr> condition;

    public predicateNode() {
        this.condition = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<BinaryExpr> getCondition() {
        return condition;
    }

    public void addCondition(BinaryExpr condition) {
        this.condition.add(condition);
    }
    
    
}
