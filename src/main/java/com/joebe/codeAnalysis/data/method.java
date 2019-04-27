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
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sound.midi.Soundbank;

/**
 *
 * @author bayup
 */
public class method{
    private MethodDeclaration md;
    private ArrayList<loopSegment> loop;
    private ArrayList<operandOperator> operator;
    private ArrayList<operandOperator> operand;
    private Set<operandOperator> DistinctOperator;
    private Set<operandOperator> DistictOperand;
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
        this.loop = new ArrayList<>();
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
            // Get All Operators
            if (node instanceof UnaryExpr) {
                UnaryExpr be = (UnaryExpr) node;
                operandOperator opr = new operandOperator(be.getOperator().asString(), be.getBegin().get().line, be.getBegin().get().column);
                this.operator.add(opr);
            } else if (node instanceof AssignExpr) {
                AssignExpr be = (AssignExpr) node;
                operandOperator opr = new operandOperator(be.getOperator().asString(), be.getBegin().get().line, be.getBegin().get().column);
                this.operator.add(opr);
            } else if (node instanceof BinaryExpr) {
                BinaryExpr be = (BinaryExpr) node;
                operandOperator opr = new operandOperator(be.getOperator().asString(), be.getBegin().get().line, be.getBegin().get().column);
                this.operator.add(opr);
            // Get All Operands
            } else if (node instanceof PrimitiveType) {
                PrimitiveType nm = (PrimitiveType) node;
                operandOperator opn = new operandOperator(nm.getType().asString(), nm.getBegin().get().line, nm.getBegin().get().column);
                this.operand.add(opn);
            } else if (node instanceof NameExpr) {
                NameExpr nm = (NameExpr) node;
                operandOperator opn = new operandOperator(nm.getNameAsString(), nm.getBegin().get().line, nm.getBegin().get().column);
                this.operand.add(opn);
            } else if (node instanceof LiteralExpr) {
                LiteralExpr nm = (LiteralExpr) node;
                operandOperator opn = new operandOperator(nm.toString(), nm.getBegin().get().line, nm.getBegin().get().column);
                this.operand.add(opn);
            } else if (node instanceof VariableDeclarationExpr) {
                VariableDeclarationExpr nm = (VariableDeclarationExpr) node;
                nm.getVariables().forEach(var -> {
                    operandOperator opn = new operandOperator(var.getNameAsString(), nm.getBegin().get().line, nm.getBegin().get().column);
                    this.operand.add(opn);    
                });
            // Locate the predicate node
            } else if (node instanceof IfStmt) {
                IfStmt ce = (IfStmt) node;
                List<String> ConditionList = extractCondition(ce.getCondition().toString(), false);
                if (ConditionList.size() > 0)
                {
                    for (String condition : ConditionList)
                    {
                        predicateNode nodeP = new predicateNode();
                        nodeP.setType("IF");
                        nodeP.addCondition(condition);
                        addNodePredicate(nodeP);
                    }
                } else
                {
                    predicateNode nodeP = new predicateNode();
                    nodeP.setType("IF");
                    nodeP.addCondition(ce.getCondition().toString());
                    addNodePredicate(nodeP);
                }
            } else if (node instanceof WhileStmt) {
                WhileStmt ce = (WhileStmt) node;
                List<String> ConditionList = extractCondition(ce.getCondition().toString(), false);
                if (ConditionList.size() > 0)
                {
                    for (String condition : ConditionList)
                    {
                        predicateNode nodeP = new predicateNode();
                        nodeP.setType("WHILE");
                        nodeP.addCondition(condition);
                        addNodePredicate(nodeP);
                    }
                } else
                {
                    predicateNode nodeP = new predicateNode();
                    nodeP.setType("WHILE");
                    nodeP.addCondition(ce.getCondition().toString());
                    addNodePredicate(nodeP);
                }
            } else if (node instanceof DoStmt) {
                DoStmt ce = (DoStmt) node;
                List<String> ConditionList = extractCondition(ce.getCondition().toString(), false);
                if (ConditionList.size() > 0)
                {
                    for (String condition : ConditionList)
                    {
                        predicateNode nodeP = new predicateNode();
                        nodeP.setType("DO");
                        nodeP.addCondition(condition);
                        addNodePredicate(nodeP);
                    }
                } else
                {
                    predicateNode nodeP = new predicateNode();
                    nodeP.setType("DO");
                    nodeP.addCondition(ce.getCondition().toString());
                    addNodePredicate(nodeP);
                }
            } else if (node instanceof ForStmt) {
                ForStmt ce = (ForStmt) node;
                List<String> ConditionList = extractCondition(ce.getCompare().toString(), true);
                if (ConditionList.size() > 0)
                {
                    for (String condition : ConditionList)
                    {
                        predicateNode nodeP = new predicateNode();
                        nodeP.setType("FOR");
                        nodeP.addCondition(condition);
                        addNodePredicate(nodeP);
                    }
                } else
                {
                    predicateNode nodeP = new predicateNode();
                    nodeP.setType("FOR");
                    nodeP.addCondition(ce.getCompare().toString());
                    addNodePredicate(nodeP);
                }
            }
        });
        this.DistinctOperator.addAll(removeDuplicateOperandOperator(this.operator));
        this.DistictOperand.addAll(removeDuplicateOperandOperator(this.operand));
    }
    
    private ArrayList<operandOperator> removeDuplicateOperandOperator(ArrayList<operandOperator> op)
    {
        ArrayList<operandOperator> tempOp = new ArrayList<>();
        
        for (operandOperator opObj : op)
        {
            if (!isOperandOperatorDuplicate(opObj, tempOp))
            {
                tempOp.add(opObj);
            }
        }
        return tempOp;
        
    }
    
    private boolean isOperandOperatorDuplicate(operandOperator op, ArrayList<operandOperator> list)
    {
        boolean result = false;
        for (operandOperator tempObj : list)
        {
            if (op.getOperandOperator().equals(tempObj.getOperandOperator()))
            {
               result = true;
            } 
        }
        return result;
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
    
    private List<String> extractCondition(String str,boolean For)
    {
        List<String> matchList = new ArrayList<String>();
        Pattern regex;
        if (For)
        {
            regex = Pattern.compile("\\[(.*?)\\]");
        } else
        {
            regex = Pattern.compile("\\((.*?)\\)");
        }
        Matcher regexMatcher = regex.matcher(str);

        while (regexMatcher.find()) 
        {//Finds Matching Pattern in String
            matchList.add(regexMatcher.group(1));//Fetching Group from String
        }

        return matchList;
    }
    
    public ArrayList<operandOperator> getOperands()
    {
        return this.operand;
    }
    
    public ArrayList<operandOperator> getOperators()
    {
        return this.operator;
    }

    public ArrayList<loopSegment> getLoop() {
        return loop;
    }
    
    public void addLoopSegment(loopSegment ls)
    {
        this.loop.add(ls);
    }
    
}
