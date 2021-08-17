package kr.msleague.mslibrary.customitem.impl.node;

import kr.msleague.mslibrary.customitem.api.ItemElement;
import kr.msleague.mslibrary.customitem.api.ItemNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashItemNode extends ItemElementImpl implements ItemNode{

    HashMap<String, ItemElement> members = new HashMap<>();

    public HashItemNode(ItemElement parents, String name) {
        super(parents, name);
    }

    @Override
    public ItemElement get(String path) {
        return get(path, true);
    }

    @Override
    public ItemElement get(String path, boolean deep) {
        String[] strs = path.split("\\.");
        String name = strs[0];
        ItemElement element = members.get(name);
        if(deep && strs.length != 1){
            ItemNode node = element.asNode();
            if(node != null){
                StringBuilder leftPath = new StringBuilder();
                for(int i = 1; i < strs.length ; i++){
                    leftPath.append(strs[i]).append('.');
                }
                return node.get(leftPath.toString());
            }else
                throw new IllegalArgumentException("path "+path+" of " +strs[1] +" not exists");
        }else{
            return element;
        }
    }

    @Override
    public boolean set(String path, ItemElement toSet) {
        String[] strs = path.split("\\.");
        if(strs.length != 1){
            String name = strs[0];
            if(!members.containsKey(name))
                members.put(name, new HashItemNode(this, name));
            ItemElement node = members.get(name);
            if(node instanceof ItemNode){
                StringBuilder leftPath = new StringBuilder();
                for(int i = 1; i < strs.length ; i++){
                    leftPath.append(strs[i]).append('.');
                }
                ((ItemNode)node).set(leftPath.toString(), toSet);
            }else
                if(toSet != null)
                    members.put(name, toSet);
                else
                    members.remove(name);
        }
        return true;
    }



    @Override
    public List<String> getKeys() {
        return new ArrayList<>(members.keySet());
    }
}
