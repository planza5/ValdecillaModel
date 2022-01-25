package com.plm.valdecilla.model;

public class SubNode {
    public Node parent;
    public String subname;

    public SubNode(Node parent, String subname){
        this.parent=parent;
        this.subname=subname;
    }

    public String toString(){
        if(subname==null || subname.length()==0){
            return parent.name;
        }

        return subname;
    }
}
