package com.joebe.codeAnalysis.data;


import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bayup
 */
public class classObject {
    
    private String path;
    private SimpleName name;
    private ArrayList<method> methods;

    public classObject() {
        this.methods = new ArrayList<>();
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SimpleName getName() {
        return name;
    }

    public void setName(SimpleName name) {
        this.name = name;
    }

    public List<method> getMethods() {
        return methods;
    }
    
    public void addMethods(method mtd)
    {
        this.methods.add(mtd);
    }
    
}
