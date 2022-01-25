package com.plm.valdecilla.model.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.plm.valdecilla.model.App;
import com.plm.valdecilla.model.Node;
import com.plm.valdecilla.model.Path;
import com.plm.valdecilla.model.SubNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author planz
 */
public class GsonUtils {

    public static App fromJson(String json){
        JsonDeserializer<App> deserialization = new JsonDeserializer<App>() {
            HashMap<String, Node> map = new HashMap();

            @Override
            public App deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
                App app = new App();
                JsonObject object = je.getAsJsonObject();
                JsonArray nodes = object.getAsJsonArray("nodes");

                for (JsonElement e : nodes) {
                    JsonObject one=e.getAsJsonObject();
                    String id=one.get("id").getAsString();

                    Node node = new Node();
                    node.id=id;
                    node.piso = one.get("piso").getAsString();
                    node.name=one.get("name").getAsString();
                    node.subnames=one.get("subnames").getAsString();

                    if(node.subnames==null || node.subnames.length()==0){
                        SubNode subNode=new SubNode(node,node.name);
                        node.subnodes.add(subNode);
                        node.subnames=subNode.subname;
                    }else{
                        for(String subname:node.subnames.split(",")){
                            SubNode subnode=new SubNode(node,subname);
                            node.subnodes.add(subnode);
                        }
                    }

                    node.x=one.get("x").getAsFloat();
                    node.y=one.get("y").getAsFloat();

                    map.put(id,node);
                    app.nodes.add(node);
                }

                JsonArray paths = object.getAsJsonArray("paths");

                for (JsonElement e : paths) {
                    JsonObject one=e.getAsJsonObject();
                    Path path=new Path();
                    path.a=map.get(one.get("a").getAsString());
                    path.b=map.get(one.get("b").getAsString());
                    path.verb_ab = one.get("verb_ab").getAsString();
                    path.verb_ba = one.get("verb_ba").getAsString();
                    path.name = one.get("path_name").getAsString();

                    JsonArray array=one.getAsJsonArray("colors");

                    for(JsonElement other:array){
                        path.colors.add(other.getAsInt());
                    }

                    app.paths.add(path);
                }

                return app;
            }
        };

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(App.class, deserialization);
        builder.setPrettyPrinting();

        return builder.create().fromJson(json, App.class);
    }

    public static String toJson(App app) {

        JsonSerializer<App> serialization = new JsonSerializer<App>() {
            @Override
            public JsonElement serialize(App app, Type typeOfSrc, JsonSerializationContext context) {
                JsonObject object = new JsonObject();

                Map<String, Node> map = new HashMap();
                JsonArray array1 = new JsonArray();

                for (Node node : app.nodes) {
                    map.put(node.id, node);
                    JsonObject one = new JsonObject();
                    one.addProperty("piso", node.piso==null?"":node.piso);
                    one.addProperty("x", node.x);
                    one.addProperty("y", node.y);
                    one.addProperty("name", node.name);
                    one.addProperty("id", node.id);

                    if(node.subnames==null || node.subnames.length()==0){
                        one.addProperty("subnames",node.name);
                    }else{
                        //one.addProperty("subnames",node.subnames.replace('\n',','));
                        one.addProperty("subnames",node.subnames);
                    }



                    array1.add(one);
                }

                object.add("nodes", array1);

                JsonArray array2 = new JsonArray();

                for (Path path : app.paths) {
                    JsonObject one = new JsonObject();
                    one.addProperty("a", path.a.id);
                    one.addProperty("b", path.b.id);
                    one.addProperty("path_name", path.name);
                    one.addProperty("verb_ab", path.verb_ab);
                    one.addProperty("verb_ba", path.verb_ba);
                    JsonArray colors=new JsonArray();

                    for(Integer color:path.colors){
                        colors.add(color);
                    }

                    one.add("colors",colors);
                    //one.addProperty("color", path.color);
                    array2.add(one);
                }

                object.add("paths", array2);

                return object;
            }
        };

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(App.class, serialization);
        builder.setPrettyPrinting();

        return builder.create().toJson(app);


    }

    public static String loadText(Context context, Uri uri) throws IOException {
        StringBuilder builder=new StringBuilder();
        InputStream is = context.getContentResolver().openInputStream(uri);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String readLine = null;

            // While the BufferedReader readLine is not null
            while ((readLine = br.readLine()) != null) {
                builder.append(readLine).append("\n");
            }

            if(builder.length()>0){
                builder.setLength(builder.length()-1);
            }


            br.close();

        } catch(FileNotFoundException e1){
            throw e1;
        } catch (IOException e2) {
            throw e2;
        }

        return builder.toString();
    }

}
