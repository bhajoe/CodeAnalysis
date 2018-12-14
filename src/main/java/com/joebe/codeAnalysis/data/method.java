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
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
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
    private Set<predicateNode> NodePredicate;
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
        this.NodePredicate = new HashSet<>();
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
            // Locate the predicate node
            } else if (node instanceof IfStmt) {
                IfStmt ce = (IfStmt) node;
                ce.walk(nd ->{
                    if (nd instanceof BinaryExpr)
                    {
                        BinaryExpr ndun = (BinaryExpr) nd;
                        if (ndun.toString().indexOf("(") < 0)
                        {
                            predicateNode nodeP = new predicateNode();
                            nodeP.setType("IF");
                            nodeP.addCondition(ndun.asBinaryExpr());
                            addNodePredicate(nodeP);
                        }
                    } 
                });
            } else if (node instanceof WhileStmt) {
                WhileStmt ce = (WhileStmt) node;
                ce.walk(nd ->{
                    if (nd instanceof UnaryExpr)
                    {
                        //UnaryExpr ndun = (UnaryExpr) nd;
                        //System.out.println("Unary "+ndun.toString());
                    } else if (nd instanceof BinaryExpr)
                    {
                        BinaryExpr ndun = (BinaryExpr) nd;
                        predicateNode nodeP = new predicateNode();
                        nodeP.setType("While");
                        nodeP.addCondition(ndun.asBinaryExpr());
                        addNodePredicate(nodeP);
                        //System.out.println("Binary "+ndun.toString());
                    } 
                });
            } else if (node instanceof DoStmt) {
                DoStmt ce = (DoStmt) node;
                ce.walk(nd ->{
                    if (nd instanceof UnaryExpr)
                    {
                        //UnaryExpr ndun = (UnaryExpr) nd;
                        //System.out.println("Unary "+ndun.toString());
                    } else if (nd instanceof BinaryExpr)
                    {
                        BinaryExpr ndun = (BinaryExpr) nd;
                        predicateNode nodeP = new predicateNode();
                        nodeP.setType("Do");
                        nodeP.addCondition(ndun.asBinaryExpr());
                        addNodePredicate(nodeP);
                        //System.out.println("Binary "+ndun.toString());
                    } 
                });
            } else if (node instanceof ForStmt) {
                ForStmt ce = (ForStmt) node;
                ce.walk(nd ->{
                    if (nd instanceof UnaryExpr)
                    {
                        //UnaryExpr ndun = (UnaryExpr) nd;
                        //System.out.println("Unary "+ndun.toString());
                    } else if (nd instanceof BinaryExpr)
                    {
                        BinaryExpr ndun = (BinaryExpr) nd;
                        predicateNode nodeP = new predicateNode();
                        nodeP.setType("For");
                        nodeP.addCondition(ndun.asBinaryExpr());
                        addNodePredicate(nodeP);
                        //System.out.println("Binary "+ndun.toString());
                    } 
                });
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

    public Set<predicateNode> getNodePredicate() {
        return NodePredicate;
    }

    public void addNodePredicate(predicateNode NodePredicate) {
        if (!isObjectInSet(NodePredicate, (HashSet<predicateNode>) this.NodePredicate))
        {
            this.NodePredicate.add(NodePredicate);
        }
    }
    
    private boolean isObjectInSet(predicateNode object, HashSet<predicateNode> set) 
    {
        boolean result = false;

        for(predicateNode o : set) {
          if((o.getType().equals(object.getType())) && (o.getCondition().equals(object.getCondition()))) {
            result = true;
            break;
          }
        }

        return result;
     }
    
    public int getCyclomaticComplexity()
    {
        // McCabe CC => V(g) = P + 1;
        return this.NodePredicate.size() + 1;
    }
    
    
}
