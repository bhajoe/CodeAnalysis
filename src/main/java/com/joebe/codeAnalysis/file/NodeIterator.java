/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joebe.codeAnalysis.file;

import com.github.javaparser.ast.Node;

/**
 *
 * @author bayup
 */
public class NodeIterator {
    public interface NodeHandler {
        boolean handle(Node node);
    }
 
    private NodeHandler nodeHandler;
 
    public NodeIterator(NodeHandler nodeHandler) {
        this.nodeHandler = nodeHandler;
    }
 
    public void explore(Node node) {
        if (nodeHandler.handle(node)) {
            for (Node child : node.getChildNodes()) {
                explore(child);
            }
        }
    }
}