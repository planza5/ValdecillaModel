package com.plm.valdecilla.model;

import com.plm.valdecilla.model.utils.CommonsCtes;

public class PathLine {

    public String line;
    public int color;

    public PathLine() {

    };

    public PathLine(String line,int color){
        this.line=line;
        this.color=color;
    }

    public String toString(){
        return line;
    }
}
