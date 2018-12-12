/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joebe.codeAnalysis.data;

import java.util.ArrayList;

/**
 *
 * @author bayup
 */
public class node {
    private int number;
    private String code;
    private ArrayList<node>[] predNode;
    private ArrayList<node>[] succNode;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<node>[] getPredNode() {
        return predNode;
    }

    public void setPredNode(ArrayList<node>[] predNode) {
        this.predNode = predNode;
    }

    public ArrayList<node>[] getSuccNode() {
        return succNode;
    }

    public void setSuccNode(ArrayList<node>[] succNode) {
        this.succNode = succNode;
    }
    
    
    
}
