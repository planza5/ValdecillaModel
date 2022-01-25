package com.plm.valdecilla.model;

import java.util.ArrayList;
import java.util.List;

//HOLA
public class App {
    public List<Path> paths=new ArrayList();
    public List<Node> nodes=new ArrayList();
    public List<Box> boxes = new ArrayList();

    public Path getPath(Node a, Node b){
        for(Path path:paths){
            if(path.a==a && path.b==b){
                return path;
            }

            if(path.a==b && path.b==a){
                return path;
            }
        }

        return null;
    }

    public Node getNode(App app,String name) {
        Node node=null;

        for(Node one:app.nodes){
            if(one.name.equals(name)){
                return one;
            }
        }

        return null;
    }
}
