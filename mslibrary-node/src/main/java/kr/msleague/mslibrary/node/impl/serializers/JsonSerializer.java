package kr.msleague.mslibrary.node.impl.serializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import kr.msleague.mslibrary.node.api.*;
import kr.msleague.mslibrary.node.impl.node.HashMSNode;
import kr.msleague.mslibrary.node.impl.node.ListMSNodeArray;
import kr.msleague.mslibrary.node.impl.node.ObjectMSNodeValue;

import javax.annotation.Nonnull;
import java.util.Map;

public class JsonSerializer implements MSNodeSerializer<JsonObject> {

    @Nonnull
    @Override
    public MSNode deserialize(@Nonnull JsonObject serialized) throws IllegalArgumentException {
        HashMSNode node = new HashMSNode(null, "");
        read(node, serialized);
        return node;
    }

    private void read(MSNode parent, JsonObject element) {
        for (Map.Entry<String, JsonElement> entry : element.entrySet()) {
            JsonElement value = entry.getValue();
            if (value.isJsonPrimitive()) {
                parent.set(entry.getKey(), new ObjectMSNodeValue(parent, entry.getKey(), readPrimitive(value.getAsJsonPrimitive())));
            } else if (value.isJsonArray()) {
                MSNodeArray nodeArray = new ListMSNodeArray(parent, entry.getKey());
                int i = 0;
                for (JsonElement ele : value.getAsJsonArray()) {
                    read(nodeArray, i, ele);
                    i++;
                }
                parent.set(entry.getKey(), nodeArray);
            } else if (value.isJsonObject()) {
                MSNode newNode = new HashMSNode(parent, entry.getKey());
                read(newNode, value.getAsJsonObject());
                parent.set(entry.getKey(), newNode);
            }
        }
    }

    private void read(MSNodeArray parent, int index, JsonElement value) {
        String name = index + "";
        if (value.isJsonPrimitive()) {
            parent.add(new ObjectMSNodeValue(parent, name, readPrimitive(value.getAsJsonPrimitive())));
        } else if (value.isJsonArray()) {
            MSNodeArray nodeArray = new ListMSNodeArray(parent, name);
            int i = 0;
            for (JsonElement ele : value.getAsJsonArray()) {
                read(nodeArray, i, ele);
                i++;
            }
            parent.add(nodeArray);
        } else if (value.isJsonObject()) {
            MSNode node = new HashMSNode(parent, name);
            read(node, value.getAsJsonObject());
            parent.add(node);
        }
    }


    private Object readPrimitive(JsonPrimitive primitive) {
        if (primitive.isBoolean()) {
            return primitive.getAsBoolean();
        } else if (primitive.isNumber()) {
            return primitive.getAsNumber();
        } else if (primitive.isString()) {
            return primitive.getAsString();
        }
        return null;
    }

    @Nonnull
    @Override
    public JsonObject serialize(MSNode item) throws IllegalArgumentException {
        JsonObject root = new JsonObject();
        for (String key : item.getKeys()) {
            write(root, item.get(key, false));
        }
        return root;
    }

    private void write(JsonObject parent, MSNodeElement node) {
        if (node instanceof MSNode) {
            JsonObject obj = new JsonObject();
            for (String key : node.asNode().getKeys()) {
                write(obj, node.asNode().get(key, false));
            }
            parent.add(node.getName(), obj);
        } else if (node instanceof MSNodeArray) {
            JsonArray array = new JsonArray();
            write(array, node.asArray());
            parent.add(node.getName(), array);
        } else if (node instanceof MSNodeValue) {
            JsonPrimitive primitive = getPrimitive(node.asValue());
            parent.add(node.getName(), primitive);
        }
    }

    private JsonPrimitive getPrimitive(MSNodeValue node) {
        JsonPrimitive primitive;
        MSNodeValue nodeValue = node.asValue();
        if (nodeValue.isBoolean()) {
            primitive = new JsonPrimitive(nodeValue.getAsBoolean());
        } else if (nodeValue.isNumber()) {
            primitive = new JsonPrimitive(nodeValue.getAsNumber());
        } else {
            primitive = new JsonPrimitive(nodeValue.getAsString());
        }
        return primitive;
    }

    private void write(JsonArray array, MSNodeElement node) {
        if (node instanceof MSNode) {
            JsonObject obj = new JsonObject();
            write(obj, node.asNode());
            array.add(obj);
        } else if (node instanceof MSNodeArray) {
            for (MSNodeElement element : node.asArray().contents()) {
                write(array, element);
            }
        } else if (node instanceof MSNodeValue) {
            JsonPrimitive primitive = getPrimitive(node.asValue());
            array.add(primitive);
        }
    }
}
