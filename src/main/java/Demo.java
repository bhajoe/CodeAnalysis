
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.joebe.codeAnalysis.file.DirExplorer;
import com.joebe.codeAnalysis.data.classObject;
import com.joebe.codeAnalysis.data.method;
import com.joebe.codeAnalysis.data.operandOperator;
import com.joebe.codeAnalysis.data.predicateNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bayup
 */
public class Demo {
 
    public ArrayList<classObject> listClasses(File projectDir) {
        ArrayList<classObject> classes = new ArrayList<>();
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (int level, String path, File file) -> {
            classObject objClass = new classObject();
            objClass.setPath(path);
            try {
                new VoidVisitorAdapter<Object>() {
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                        objClass.setName(n.getName()); 
                        // Testing commit
                        //System.out.println("Jumlah method "+n.getMethods().size());
                        for (int i = 0; i < n.getMethods().size(); i++)
                        {
                            //System.out.println("index = "+i);
                            method objMethod = new method(n.getMethods().get(i));
                            
                            objClass.addMethods(objMethod);
                            //System.out.println("Masuk "+objMethod.getMd().getNameAsString());
                        }
                    }
                }.visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
            classes.add(objClass);
        }).explore(projectDir);
        return classes;
    }
    
    public static void main(String[] args) {
        File projectDir = new File("C:\\Users\\bayup\\Documents\\NetBeansProjects\\CodeAnalysis\\src\\main\\java\\testing");
        Demo lc = new Demo();
        for (classObject cls : lc.listClasses(projectDir))
        {
            System.out.println("Class name : "+cls.getName());
            for (method md : cls.getMethods())
            {
                System.out.println(" method: "+md.getMd().getName());
                System.out.println("      - Operator :"+md.getOperator());
                System.out.println("      - Operand : "+md.getOperand());
                if (md.getOperands().size() > 0)
                {
                    System.out.println("      - Operator : ");
                    for (operandOperator ops : md.getOperands())
                    {
                        System.out.println("           - : "+ops.getOperandOperator()+" "+ops.getLine());
                    }
                }
                System.out.println("      - Distinct Operator :"+md.getDistinctOperator());
                System.out.println("      - Distinct Operand : "+md.getDistinctOperand());
                System.out.println("      - Volume : "+md.getHVolume());
                System.out.println("      - Level : "+md.getHProgramLevel());
                System.out.println("      - Effort : "+md.getHEffort());
                System.out.println("      - Time to Implement : "+md.getHTime());
                if (md.getNodePredicate().size() > 0)
                {
                    System.out.println("      - Predicate Node : ");
                    for (predicateNode nd : md.getNodePredicate())
                    {
                        System.out.println("           - : "+nd.getType()+" "+nd.getCondition().toString());
                    }
                }
                System.out.println("      - Cyclomatic Complexity : "+md.getCyclomaticComplexity());
                
            }
        }
        
    }
}
