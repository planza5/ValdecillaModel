package com.plm.valdecilla.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {
    public String id;
    public float x;
    public float y;
    public boolean selected;
    public boolean visible=true;
    public String name="New";
    public String piso = "";
    public List<SubNode> subnodes=new ArrayList();
    public String subnames;
    public float sx;
    public float sy;

    public Node(){

    }

    public Node(float x, float y){
        this.x=x;
        this.y=y;
        this.visible=true;
        this.id=Long.toString(new Random().nextLong());
    }

    public void setX(float value) {
        x = value;
    }

    public void setY(float value) {
        y = value;
    }

    public boolean isSelected(){
        return selected;
    }

    @Override
    public String toString(){
        if(subnodes==null || subnames.length()==0 || subnames.equals(name)){
            return name;
        }

        StringBuilder builder=new StringBuilder();
        builder.append(name).append(" (");

        for(SubNode one:subnodes) {
            builder.append(one.subname).append(", ");
        }

        builder.setLength(builder.length()-2);

        builder.append(")");

        return builder.toString();
    }

    public Node cloneNode(){
        Node node=new Node();

        node.id=this.id;
        node.x=this.x;
        node.y=this.y;
        node.visible=this.visible;
        node.selected=this.selected;
        node.name=this.name;
        node.piso=this.piso;
        node.subnodes=this.subnodes;
        node.subnames=this.subnames;
        node.sx=this.sx;
        node.sy=this.sy;

        return node;
    }
}
