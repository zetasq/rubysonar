package org.yinwang.rubysonar.ast;

import org.jetbrains.annotations.NotNull;
import org.yinwang.rubysonar.Analyzer;
import org.yinwang.rubysonar.State;
import org.yinwang.rubysonar.types.Type;


public class Raise extends Node {

    public Node exceptionType;
    public Node inst;
    public Node traceback;


    public Raise(Node exceptionType, Node inst, Node traceback, int start, int end) {
        super(start, end);
        this.exceptionType = exceptionType;
        this.inst = inst;
        this.traceback = traceback;
        addChildren(exceptionType, inst, traceback);
    }


    @NotNull
    @Override
    public Type transform(State s) {
        if (exceptionType != null) {
            transformExpr(exceptionType, s);
        }
        if (inst != null) {
            transformExpr(inst, s);
        }
        if (traceback != null) {
            transformExpr(traceback, s);
        }
        return Type.CONT;
    }


    @NotNull
    @Override
    public String toString() {
        return "(raise:" + traceback + ":" + exceptionType + ")";
    }


    @Override
    public void visit(@NotNull NodeVisitor v) {
        if (v.visit(this)) {
            visitNode(exceptionType, v);
            visitNode(inst, v);
            visitNode(traceback, v);
        }
    }
}
