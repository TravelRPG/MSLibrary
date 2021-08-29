package kr.msleague.mslibrary.customitem.impl.serializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import kr.msleague.mslibrary.customitem.api.*;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import kr.msleague.mslibrary.customitem.impl.node.ListItemNodeArray;
import kr.msleague.mslibrary.customitem.impl.node.MSLItemData;
import kr.msleague.mslibrary.customitem.impl.node.ObjectItemNodeValue;

import javax.annotation.Nonnull;
import java.util.Map;

public class JsonSerializer implements ItemSerializer<JsonObject> {

    @Override
    public MSItemData deserialize(@Nonnull JsonObject serialized) throws IllegalArgumentException {
        HashItemNode node = new HashItemNode(null, "");
        read(node, serialized);
        return new MSLItemData(node);
    }

    private void read(ItemNode parent, JsonObject element) {
        for (Map.Entry<String, JsonElement> entry : element.entrySet()) {
            JsonElement value = entry.getValue();
            if (value.isJsonPrimitive()) {
                parent.set(entry.getKey(), new ObjectItemNodeValue(parent, entry.getKey(), readPrimitive(value.getAsJsonPrimitive())));
            } else if (value.isJsonArray()) {
                ItemNodeArray nodeArray = new ListItemNodeArray(parent, entry.getKey());
                int i = 0;
                for (JsonElement ele : value.getAsJsonArray()) {
                    read(nodeArray, i, ele);
                    i++;
                }
                parent.set(entry.getKey(), nodeArray);
            } else if (value.isJsonObject()) {
                ItemNode newNode = new HashItemNode(parent, entry.getKey());
                read(newNode, value.getAsJsonObject());
                parent.set(entry.getKey(), newNode);
            }
        }
    }

    private void read(ItemNodeArray parent, int index, JsonElement value) {
        String name = index + "";
        if (value.isJsonPrimitive()) {
            parent.add(new ObjectItemNodeValue(parent, name, readPrimitive(value.getAsJsonPrimitive())));
        } else if (value.isJsonArray()) {
            ItemNodeArray nodeArray = new ListItemNodeArray(parent, name);
            int i = 0;
            for (JsonElement ele : value.getAsJsonArray()) {
                read(nodeArray, i, ele);
                i++;
            }
            parent.add(nodeArray);
        } else if (value.isJsonObject()) {
            ItemNode node = new HashItemNode(parent, name);
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
    public JsonObject serialize(MSItemData item) throws IllegalArgumentException {
        if (!item.getNodes().has("id") || !item.getNodes().has("version"))
            throw new IllegalArgumentException("target item has not 'id' or 'version' key");
        JsonObject root = new JsonObject();
        for (String key : item.getNodes().getKeys()) {
            write(root, item.getNodes().get(key, false));
        }
        return root;
    }

    private void write(JsonObject parent, ItemElement node) {
        if (node instanceof ItemNode) {
            JsonObject obj = new JsonObject();
            for (String key : node.asNode().getKeys()) {
                write(obj, node.asNode().get(key, false));
            }
            parent.add(node.getName(), obj);
        } else if (node instanceof ItemNodeArray) {
            JsonArray array = new JsonArray();
            write(array, node.asArray());
            parent.add(node.getName(), array);
        } else if (node instanceof ItemNodeValue) {
            JsonPrimitive primitive = getPrimitive(node.asValue());
            parent.add(node.getName(), primitive);
        }
    }

    private JsonPrimitive getPrimitive(ItemNodeValue node) {
        JsonPrimitive primitive;
        ItemNodeValue nodeValue = node.asValue();
        if (nodeValue.isBoolean()) {
            primitive = new JsonPrimitive(nodeValue.getAsBoolean());
        } else if (nodeValue.isNumber()) {
            primitive = new JsonPrimitive(nodeValue.getAsNumber());
        } else {
            primitive = new JsonPrimitive(nodeValue.getAsString());
        }
        return primitive;
    }

    private void write(JsonArray array, ItemElement node) {
        if (node instanceof ItemNode) {
            JsonObject obj = new JsonObject();
            write(obj, node.asNode());
            array.add(obj);
        } else if (node instanceof ItemNodeArray) {
            for (ItemElement element : node.asArray().contents()) {
                write(array, element);
            }
        } else if (node instanceof ItemNodeValue) {
            JsonPrimitive primitive = getPrimitive(node.asValue());
            array.add(primitive);
        }
    }
}
