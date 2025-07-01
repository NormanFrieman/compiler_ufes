package checker.utils;

import java.util.LinkedList;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.Token;

import checker.Variable;

public class Scope {
    public int Init;
    public int End;

    // Variables
    //// HashMap
    private LinkedList<Variable> vt = new LinkedList<Variable>();

    public int getInit() {
        return Init;
    }

    public int getEnd() {
        return End;
    }

    public void setInit(int init) {
        Init = init;
    }

    public void setEnd(int end) {
        End = end;
    }

    public boolean IsDeclared(Token var) {
        String varName = var.getText();
        boolean isDeclared = vt.stream().anyMatch(x -> x.getName().equals(varName));
        if (!isDeclared)
            return false;
        return true;
    }

    public void AddVar(Variable var) {
        vt.add(var);
    }

    public Variable GetVar(String name) {
        return vt.stream()
            .filter(x -> x.getName().equals(name))
            .collect(Collectors.toList()).get(0);
    }

    public void PrintVars() {
        vt.forEach(v -> System.err.println(v.getName()));
    }
}
