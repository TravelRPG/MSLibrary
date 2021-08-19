package kr.msleague.mslibrary.customitem;

import kr.msleague.mslibrary.customitem.api.ItemNode;
import kr.msleague.mslibrary.customitem.api.ItemNodeArray;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import kr.msleague.mslibrary.customitem.impl.node.ListItemNodeArray;
import org.junit.Assert;
import org.junit.Test;

public class NodeTreeTest {

    @Test
    public void test(){
        ItemNode node = new HashItemNode(null, "root");
        node.setValue("hello.world", 32);
        node.setValue("hello.land", "string");
        Assert.assertNotNull(node.get("hello"));
        Assert.assertEquals("hello", node.get("hello").getName());
        Assert.assertEquals(2, node.get("hello").asNode().getKeys().size());
        Assert.assertEquals("world", node.get("hello.world").getName());
        Assert.assertEquals(32, node.get("hello.world").asValue().getAsInt());
        Assert.assertEquals("string", node.get("hello.land").asValue().getAsString());
    }

    @Test
    public void arrayTest(){
        ItemNode node = new HashItemNode(null, "root");
        ItemNodeArray array = new ListItemNodeArray(node, "array");
        array.addPrimitive(1);
        array.addPrimitive(2);
        array.addPrimitive(3);
        node.set("hello.world.array", array);
        Assert.assertTrue(node.get("hello.world.array") instanceof ItemNodeArray);
        Assert.assertEquals(3, node.get("hello.world.array").asArray().size());
    }
}
