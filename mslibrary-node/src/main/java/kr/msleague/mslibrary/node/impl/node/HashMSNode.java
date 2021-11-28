package kr.msleague.mslibrary.node.impl.node;

import com.google.gson.JsonObject;
import kr.msleague.mslibrary.node.api.MSNode;
import kr.msleague.mslibrary.node.api.MSNodeArray;
import kr.msleague.mslibrary.node.api.MSNodeElement;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMSNode extends MSNodeElementImpl implements MSNode {

    HashMap<String, MSNodeElement> members = new HashMap<>();

    public HashMSNode(MSNodeElement parents, String name) {
        super(parents, name);
    }

    @Nonnull
    @Override
    public MSNodeElement get(@Nonnull String path) {
        return get(path, true);
    }

    @Override
    public MSNodeElement get(@Nonnull String path, boolean deep) {
        String[] strs = path.split("\\.", 2);
        String name = strs[0];
        MSNodeElement element = members.get(name);
        if (deep && strs.length > 1) {
            if (element != null) {
                if (element instanceof MSNode) {
                    MSNode node = element.asNode();
                    return node.get(strs[1]);
                } else {
                    throw new IllegalStateException("the path " + name + " is " + element.getClass().getSimpleName() + " expected: " + MSNode.class.getSimpleName());
                }
            } else {
                return null;
            }
        } else {
            return element;
        }
    }

    @Override
    public void set(@Nonnull String path, MSNodeElement toSet) {
        String[] strs = path.split("\\.", 2);
        String name = strs[0];
        if (strs.length != 1) {
            if (!members.containsKey(name))
                members.put(name, new HashMSNode(this, name));
            MSNodeElement node = members.get(name);
            if (node instanceof MSNode) {
                ((MSNode) node).set(strs[1], toSet);
            }
        } else {
            if (toSet != null)
                members.put(name, toSet);
            else
                members.remove(name);
        }
    }

    @Override
    public void setValue(@Nonnull String path, @Nonnull Number number) {
        setPrimitive(path, number);
    }

    @Override
    public void setValue(@Nonnull String path, @Nonnull Boolean b) {
        setPrimitive(path, b);
    }

    @Override
    public void setValue(@Nonnull String path, @Nonnull String s) {
        setPrimitive(path, s);
    }

    @Override
    public void setPrimitive(@Nonnull String path, @Nonnull Object primitive) {
        String[] strs = path.split("\\.", 2);
        String name = strs[0];
        if (strs.length != 1) {
            if (!members.containsKey(name))
                members.put(name, new HashMSNode(this, name));
            MSNodeElement node = members.get(name);
            if (node instanceof MSNode) {
                node.asNode().setPrimitive(strs[1], primitive);
            } else if (node instanceof MSNodeArray) {
                node.asArray().addPrimitive(primitive);
            }
        } else {
            members.put(name, new ObjectMSNodeValue(this, name, primitive));
        }
    }


    @Override
    public boolean has(@Nonnull String key) {
        return get(key) != null;
    }

    @Nonnull
    @Override
    public List<String> getKeys() {
        return new ArrayList<>(members.keySet());
    }

    @Override
    public MSNode createNode(String path) {
        String[] strs = path.split("\\.", 2);
        String name = strs[0];
        if (!members.containsKey(name)) {
            members.put(name, new HashMSNode(this, name));
        }
        if (strs.length > 1) {
            return members.get(name).asNode().createNode(strs[1]);
        } else {
            return members.get(name).asNode();
        }
    }

    @Override
    public MSNodeArray createArray(String path) {
        String[] strs = path.split("\\.", 2);
        String name = strs[0];
        if (strs.length > 1) {
            MSNodeElement subNode = members.get(name);
            if (!members.containsKey(name)) {
                subNode = new HashMSNode(this, name);
                members.put(name, subNode);
            } else if (subNode instanceof MSNodeArray) {
                subNode = new HashMSNode(this, name);
                subNode.asArray().add(subNode);
            }
            if (subNode instanceof MSNode)
                return subNode.asNode().createArray(strs[1]);
            else
                throw new IllegalArgumentException("the path " + path + " of " + name + " already has primitive type");
        } else {
            if (!members.containsKey(name)) {
                members.put(name, new ListMSNodeArray(this, name));
            }
            return members.get(name).asArray();
        }
    }
}
