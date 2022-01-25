package com.plm.valdecilla.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Path  {
    public Node a;
    public Node b;
    public List<Integer> colors = new ArrayList();
    public boolean visible=true;
    public String verb_ab = "";
    public String verb_ba = "";
    public String name = "";
    public int weight=1;
    public float angle;
    public boolean reverse;


    public void cloneColors(Path pa) {
        for(Integer color:colors){
            pa.colors.add(color);
        }
    }

    public void reverse(){
        Node temp1=a;
        a=b;
        b=temp1;

        String temp2=verb_ba;
        verb_ab=verb_ba;
        verb_ba=temp2;
    }

    @Override
    public String toString(){
        return reverse?b.name+"->"+a.name:a.name+"->"+b.name;
    }


}
