/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joebe.codeAnalysis.data;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.LiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bayup
 */
public class method{
    private MethodDeclaration md;
    private ArrayList<String> operator;
    private ArrayList<String> operand;
    private Set<String> DistinctOperator;
    private Set<String> DistictOperand;
    private float HProgramLevel;
    private float HVolume;
    private float HDifficulty;
    private float HEffort;
    private float HVocabulary;
    private float HLength;
    private float HTime;

    public method(MethodDeclaration md) {
        this.md = md;
        this.operand = new ArrayList<>();
        this.operator =  new ArrayList<>();
        this.DistinctOperator = new HashSet<>();
        this.DistictOperand = new HashSet<>();
        ExtractBody();
        CalculateHalstead();
    }

    public float getHProgramLevel() {
        return HProgramLevel;
    }

    public float getHVolume() {
        return HVolume;
    }

    public float getHDifficulty() {
        return HDifficulty;
    }

    public float getHEffort() {
        return HEffort;
    }

    public float getHVocabulary() {
        return HVocabulary;
    }

    public float getHLength() {
        return HLength;
    }

    public float getHTime() {
        return HTime;
    }
    
    
    
    public MethodDeclaration getMd() {
        return md;
    }
    
    private void CalculateHalstead()
    {
        CalculateVocabulary();
        CalculateLength();
        CalculateDifficulty();
        CalculateProgramLevel();
        CalculateHVolume();
        CalculateHEffort();
        CalculateHTime();
    }
    
    public void ExtractBody()
    {
        md.walk(node -> {
            if (node instanceof UnaryExpr) {
                UnaryExpr be = (UnaryExpr) node;
                this.operator.add(be.getOperator().asString());
            } else if (node instanceof AssignExpr) {
                AssignExpr be = (AssignExpr) node;
                this.operator.add(be.getOperator().asString());
            } else if (node instanceof BinaryExpr) {
                BinaryExpr be = (BinaryExpr) node;
                this.operator.add(be.getOperator().asString());
            } else if (node instanceof PrimitiveType) {
                PrimitiveType nm = (PrimitiveType) node;
                this.operand.add(nm.getType().asString());
            } else if (node instanceof NameExpr) {
                NameExpr nm = (NameExpr) node;
                this.operand.add(nm.getNameAsString());
            } else if (node instanceof LiteralExpr) {
                LiteralExpr nm = (LiteralExpr) node;
                this.operand.add(nm.toString());
            } else if (node instanceof VariableDeclarationExpr) {
                VariableDeclarationExpr nm = (VariableDeclarationExpr) node;
                nm.getVariables().forEach(var -> {
                    this.operand.add(var.getNameAsString());    
                });
            } else if (node instanceof IfStmt) {
                IfStmt ce = (IfStmt) node;
                //System.out.println("Conditional"+ce.getElseStmt());
                //this.operand.add(nm.toString());
            }
        });
        this.DistinctOperator.addAll(this.operator);
        this.DistictOperand.addAll(this.operand);
    }
    
    public int getOperator()
    {
        return this.operator.size();
    }
    
    public int getOperand()
    {
        return this.operand.size();
    }
    
    public int getDistinctOperator()
    {
        return this.DistinctOperator.size();
    }
    
    public int getDistinctOperand()
    {
        return this.DistictOperand.size();
    }
    
    private void CalculateVocabulary()
    {
        this.HVocabulary = this.operator.size() + this.operand.size();
    }
    
    private void CalculateLength()
    {
        this.HLength = this.DistictOperand.size() + this.DistinctOperator.size();
    }

    private void CalculateDifficulty() {
        this.HDifficulty = (this.DistinctOperator.size()/2)*(this.operand.size()/this.DistictOperand.size());
    }

    private void CalculateProgramLevel() {
        
        this.HProgramLevel = 1/this.HDifficulty;
    }
    
    private void CalculateHTime()
    {
        this.HTime = this.HEffort/18;
    }

    private void CalculateHVolume() {
        this.HVolume = this.HLength * log2(this.HVocabulary);
    }


    private void CalculateHEffort() {
        this.HEffort = this.HVolume * this.HDifficulty;
    }
    
    private float log2(float n)
    {
        return (float) (Math.log(n) / Math.log(2));
    }
    
}
