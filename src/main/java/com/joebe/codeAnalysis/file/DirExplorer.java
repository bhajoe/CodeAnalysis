/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joebe.codeAnalysis.file;

import java.io.File;

/**
 *
 * @author bayup
 */
public class DirExplorer {
    public interface FileHandler {
        void handle(int level, String path, File file);
    }
 
    public interface Filter {
        boolean interested(int level, String path, File file);
    }
 
    private FileHandler fileHandler;
    private Filter filter;
 
    public DirExplorer(Filter filter, FileHandler fileHandler) {
        this.filter = filter;
        this.fileHandler = fileHandler;
    }
 
    public void explore(File root) {
        explore(0, "", root);
    }
 
    private void explore(int level, String path, File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                explore(level + 1, path + "/" + child.getName(), child);
            }
        } else {
            if (filter.interested(level, path, file)) {
                fileHandler.handle(level, path, file);
            }
        }
    }
 
}
