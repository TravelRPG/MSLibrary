package kr.msleague.mslibrary.customitem.impl.node;

import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemNode;
import kr.msleague.mslibrary.customitem.api.ItemNodeArray;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashItemNode extends ItemElementImpl implements ItemNode{

    HashMap<String, ItemElement> members = new HashMap<>();

    public HashItemNode(ItemElement parents, String name) {
        super(parents, name);
    }

    @Override
    public ItemElement get(@Nonnull String path) {
        return get(path, true);
    }

    @Override
    public ItemElement get(@Nonnull String path, boolean deep) {
        String[] strs = path.split("\\.", 2);
        String name = strs[0];
        ItemElement element = members.get(name);
        if(deep && strs.length > 1){
            if(element != null){
                if(element instanceof ItemNode) {
                    ItemNode node = element.asNode();
                    return node.get(strs[1]);
                }else{
                    throw new IllegalStateException("the path "+name+" is "+element.getClass().getSimpleName()+" expected: "+ItemNode.class.getSimpleName());
                }
            }else
                return null;
        }else{
            return element;
        }
    }

    @Override
    public void set(@Nonnull String path, ItemElement toSet) {
        String[] strs = path.split("\\.", 2);
        String name = strs[0];
        if(strs.length != 1){
            if(!members.containsKey(name))
                members.put(name, new HashItemNode(this, name));
            ItemElement node = members.get(name);
            if(node instanceof ItemNode){
                ((ItemNode)node).set(strs[1], toSet);
            }
        }else{
            if(toSet != null)
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
        if(strs.length != 1){
            if(!members.containsKey(name))
                members.put(name, new HashItemNode(this, name));
            ItemElement node = members.get(name);
            if(node instanceof ItemNode){
                node.asNode().setPrimitive(strs[1], primitive);
            }else if(node instanceof ItemNodeArray){
                node.asArray().addPrimitive(primitive);
            }
        }else{
            members.put(name, new ObjectItemNodeValue(this, name, primitive));
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
    public ItemNode createNode(String path) {
        String[] strs = path.split("\\.", 2);
        String name = strs[0];
        if(!members.containsKey(name)){
            members.put(name, new HashItemNode(this, name));
        }
        if(strs.length > 1)
            return createNode(strs[1]);
        else
            return members.get(name).asNode();
    }

    @Override
    public ItemNodeArray createArray(String path) {
        String[] strs = path.split("\\.", 2);
        String name = strs[0];
        if(strs.length > 1) {
            ItemElement subNode = members.get(name);
            if (!members.containsKey(name)) {
                subNode = new HashItemNode(this, name);
                members.put(name, subNode);
            }else if(subNode instanceof ItemNodeArray){
                subNode = new HashItemNode(this, name);
                subNode.asArray().add(subNode);
            }
            if(subNode instanceof ItemNode)
                return subNode.asNode().createArray(strs[1]);
            else
                throw new IllegalArgumentException("the path "+path+" of "+name+" already has primitive type");
        }else {
            if(!members.containsKey(name)){
                members.put(name, new ListItemNodeArray(this, name));
            }
            return members.get(name).asArray();
        }
    }
}
