package kr.msleague.mslibrary.customitem.impl.serializers;

import kr.msleague.mslibrary.customitem.api.*;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import kr.msleague.mslibrary.customitem.impl.node.ListItemNodeArray;
import kr.msleague.mslibrary.customitem.impl.node.MSLItemData;

import javax.annotation.Nonnull;

public class MappingSerializer implements ItemSerializer<MSItemData> {

    @Nonnull
    @Override
    public MSItemData serialize(@Nonnull MSItemData serialized) throws IllegalArgumentException {
        ItemNode node = new HashItemNode(null, "");
        map(node, serialized.getNodes());
        return new MSLItemData(node);
    }


    private void map(ItemNode parent, ItemNode node) {
        for (String key : node.getKeys()) {
            ItemElement element = node.get(key);
            map(parent, key, element);
        }
    }

    private void map(ItemNode parent, String key, ItemElement element) {
        if (element instanceof ItemNodeValue) {
            Object primitive = readPrimitive(element.asValue());
            if (primitive != null)
                parent.setPrimitive(key, primitive);
        } else if (element instanceof ItemNodeArray) {
            ItemNode newNode = parent.createNode(key);
            int i = 0;
            for (ItemElement ele : element.asArray().contents()) {
                map(newNode, i, ele);
                i++;
            }
            parent.set(key, newNode);
        } else if (element instanceof ItemNode) {
            ItemNode newNode = parent.createNode(key);
            map(newNode, element.asNode());
        }
    }

    private void map(ItemNode parent, int index, ItemElement value) {
        String name = "$" + index;
        map(parent, name, value);
    }

    private Object readPrimitive(ItemNodeValue primitive) {
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
    public MSItemData deserialize(@Nonnull MSItemData mapped) throws IllegalArgumentException {
        ItemNode node = new HashItemNode(null, "");
        for (String key : mapped.getNodes().getKeys()) {
            ItemElement element = mapped.getNodes().get(key);
            demap(node, element);
        }
        return new MSLItemData(node);
    }

    private void demap(ItemNode parent, ItemElement node) {
        if (node instanceof ItemNode) {
            boolean isArray = true;
            for (String key : node.asNode().getKeys()) {
                isArray = isArray && key.startsWith("$");
            }
            if (isArray) {
                ItemNodeArray newArray = parent.createArray(node.getName());
                for (String key : node.asNode().getKeys()) {
                    demap(newArray, node.asNode().get(key));
                }
                parent.set(node.getName(), newArray);
            } else {
                ItemNode newNode = parent.createNode(node.getName());
                for (String key : node.asNode().getKeys()) {
                    demap(newNode, node.asNode().get(key, false));
                }
                parent.set(node.getName(), newNode);
            }
        } else if (node instanceof ItemNodeValue) {
            Object primitive = readPrimitive(node.asValue());
            if (primitive != null)
                parent.setPrimitive(node.getName(), primitive);
        }
    }


    private void demap(ItemNodeArray parent, ItemElement node) {
        if (node instanceof ItemNode) {
            boolean isArray = true;
            for (String key : node.asNode().getKeys()) {
                isArray = isArray && key.startsWith("$");
            }
            if (isArray) {
                ItemNodeArray newArray = new ListItemNodeArray(parent, node.getName());
                for (String key : node.asNode().getKeys()) {
                    demap(newArray, node.asNode().get(key));
                }
            } else {
                ItemNode newNode = new HashItemNode(parent, node.getName());
                for (String key : node.asNode().getKeys()) {
                    demap(newNode, node.asNode().get(key, false));
                }
            }
        } else if (node instanceof ItemNodeValue) {
            Object primitive = readPrimitive(node.asValue());
            if (primitive != null)
                parent.addPrimitive(primitive);
        }
    }
}
