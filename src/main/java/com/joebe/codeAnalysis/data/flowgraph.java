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
public class flowgraph {
    
    private ArrayList<node> nodes;

    public ArrayList<node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<node> nodes) {
        this.nodes = nodes;
    }
    
    public void addNode(node aNode)
    {
        this.nodes.add(aNode);
    }
    
    public void printGraph()
    {
        
    }
}
