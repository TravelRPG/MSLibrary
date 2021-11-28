package kr.msleague.mslibrary.node.impl.serializers;

import kr.msleague.mslibrary.node.api.*;
import kr.msleague.mslibrary.node.impl.node.HashMSNode;
import kr.msleague.mslibrary.node.impl.node.ListMSNodeArray;

import javax.annotation.Nonnull;

public class MappingSerializer implements MSNodeSerializer<MSNode> {

    @Nonnull
    @Override
    public MSNode serialize(@Nonnull MSNode serialized) throws IllegalArgumentException {
        MSNode node = new HashMSNode(null, "");
        map(node, serialized);
        return node;
    }


    private void map(MSNode parent, MSNode node) {
        for (String key : node.getKeys()) {
            MSNodeElement element = node.get(key);
            map(parent, key, element);
        }
    }

    private void map(MSNode parent, String key, MSNodeElement element) {
        if (element instanceof MSNodeValue) {
            Object primitive = readPrimitive(element.asValue());
            if (primitive != null)
                parent.setPrimitive(key, primitive);
        } else if (element instanceof MSNodeArray) {
            MSNode newNode = parent.createNode(key);
            int i = 0;
            for (MSNodeElement ele : element.asArray().contents()) {
                map(newNode, i, ele);
                i++;
            }
            parent.set(key, newNode);
        } else if (element instanceof MSNode) {
            MSNode newNode = parent.createNode(key);
            map(newNode, element.asNode());
        }
    }

    private void map(MSNode parent, int index, MSNodeElement value) {
        String name = "$" + index;
        map(parent, name, value);
    }

    private Object readPrimitive(MSNodeValue primitive) {
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
    public MSNode deserialize(@Nonnull MSNode mapped) throws IllegalArgumentException {
        MSNode node = new HashMSNode(null, "");
        for (String key : mapped.getKeys()) {
            MSNodeElement element = mapped.get(key);
            demap(node, element);
        }
        return node;
    }

    private void demap(MSNode parent, MSNodeElement node) {
        if (node instanceof MSNode) {
            boolean isArray = true;
            for (String key : node.asNode().getKeys()) {
                isArray = isArray && key.startsWith("$");
            }
            if (isArray) {
                MSNodeArray newArray = parent.createArray(node.getName());
                for (String key : node.asNode().getKeys()) {
                    demap(newArray, node.asNode().get(key));
                }
                parent.set(node.getName(), newArray);
            } else {
                MSNode newNode = parent.createNode(node.getName());
                for (String key : node.asNode().getKeys()) {
                    demap(newNode, node.asNode().get(key, false));
                }
                parent.set(node.getName(), newNode);
            }
        } else if (node instanceof MSNodeValue) {
            Object primitive = readPrimitive(node.asValue());
            if (primitive != null)
                parent.setPrimitive(node.getName(), primitive);
        }
    }


    private void demap(MSNodeArray parent, MSNodeElement node) {
        if (node instanceof MSNode) {
            boolean isArray = true;
            for (String key : node.asNode().getKeys()) {
                isArray = isArray && key.startsWith("$");
            }
            if (isArray) {
                MSNodeArray newArray = new ListMSNodeArray(parent, node.getName());
                for (String key : node.asNode().getKeys()) {
                    demap(newArray, node.asNode().get(key));
                }
            } else {
                MSNode newNode = new HashMSNode(parent, node.getName());
                for (String key : node.asNode().getKeys()) {
                    demap(newNode, node.asNode().get(key, false));
                }
            }
        } else if (node instanceof MSNodeValue) {
            Object primitive = readPrimitive(node.asValue());
            if (primitive != null)
                parent.addPrimitive(primitive);
        }
    }
}
