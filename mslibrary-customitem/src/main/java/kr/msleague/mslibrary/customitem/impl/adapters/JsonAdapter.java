package kr.msleague.mslibrary.customitem.impl.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import kr.msleague.mslibrary.customitem.api.*;
import kr.msleague.mslibrary.customitem.impl.node.ListItemNodeArray;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import kr.msleague.mslibrary.customitem.impl.node.ObjectItemNodeValue;

import javax.annotation.Nonnull;
import java.util.Map;

public class JsonAdapter implements ItemAdapter<JsonObject> {

    @Override
    public MSItem deserialize(@Nonnull JsonObject serialized) throws IllegalArgumentException {
        HashItemNode node = new HashItemNode(null, "");
        read(node, serialized);
        return null;
    }

    private void read(ItemNode parent, JsonObject element){
        for (Map.Entry<String, JsonElement> entry : element.entrySet()) {
            JsonElement value = entry.getValue();
            if(value.isJsonPrimitive()){
                parent.set(entry.getKey(), new ObjectItemNodeValue(parent, entry.getKey(), readPrimitive(value.getAsJsonPrimitive())));
            }else if(value.isJsonArray()){
                ItemNodeArray nodeArray = new ListItemNodeArray(parent, entry.getKey());
                int i = 0;
                for (JsonElement ele : value.getAsJsonArray()) {
                    read(nodeArray, i, ele);
                    i++;
                }
                parent.set(entry.getKey(), nodeArray);
            }else if(value.isJsonObject()){
                ItemNode newNode = new HashItemNode(parent, entry.getKey());
                read(newNode, value.getAsJsonObject());
                parent.set(entry.getKey(), newNode);
            }
        }
    }

    private void read(ItemNodeArray parent, int index, JsonElement value){
        String name = index+"";
        if(value.isJsonPrimitive()){
            parent.add(new ObjectItemNodeValue(parent, name, readPrimitive(value.getAsJsonPrimitive())));
        }else if(value.isJsonArray()){
            ItemNodeArray nodeArray = new ListItemNodeArray(parent, name);
            int i = 0;
            for (JsonElement ele : value.getAsJsonArray()) {
                read(nodeArray, i, ele);
                i++;
            }
            parent.add(nodeArray);
        }else if(value.isJsonObject()){
            ItemNode node = new HashItemNode(parent, name);
            read(node, value.getAsJsonObject());
            parent.add(node);
        }
    }


    private Object readPrimitive(JsonPrimitive primitive){
        if(primitive.isBoolean()){
            return primitive.getAsBoolean();
        }else if(primitive.isNumber()){
            return primitive.getAsNumber();
        }else if(primitive.isString()){
            return primitive.getAsString();
        }
        return null;
    }

    @Override
    public JsonObject serialize(MSItem item) throws IllegalArgumentException {
        JsonObject root = new JsonObject();
        root.addProperty("id", item.getID());
        root.addProperty("version", item.getVersion());
        for (String key : item.getNodes().getKeys()) {
            write(root, item.getNodes().get(key, false));
        }
        return null;
    }

    private void write(JsonObject parent, ItemElement node){
        if(node instanceof ItemNode){
            JsonObject obj = new JsonObject();
            for (String key : node.asNode().getKeys()) {
                write(obj, node.asNode().get(key, false));
            }
            parent.add(node.getName(), obj);
        }else if(node instanceof ItemNodeArray){
            JsonArray array = new JsonArray();
            write(array, node.asArray());
            parent.add(node.getName(), array);
        }else if(node instanceof ItemNodeValue){
            //todo: 원시타입 가져오기
            JsonPrimitive primitive = new JsonPrimitive();
        }
    }



    private void write(JsonArray array, ItemElement element){

    }

    private void writePrimitive(){

    }
}
