package kr.msleague.mslibrary.customitem;

import com.google.gson.JsonObject;
import kr.msleague.mslibrary.customitem.api.ItemNode;
import kr.msleague.mslibrary.customitem.api.ItemNodeArray;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NodeTreeTest {

    @Test
    public void test() {
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
    public void arrayTest() {
        ItemNode node = new HashItemNode(null, "root");
        ItemNodeArray array = node.createArray("hello.world.array");
        array.addPrimitive(1);
        array.addPrimitive(2);
        array.addPrimitive(3);
        Assert.assertTrue(node.get("hello.world.array") instanceof ItemNodeArray);
        Assert.assertEquals(3, node.get("hello.world.array").asArray().size());
    }

    @Test
    public void setTest() {
        ItemNode node = new HashItemNode(null, "root");
        node.setPrimitive("hello.world.set", "hello");
        Assert.assertEquals("hello", node.get("hello.world.set").asValue().getAsString());
        node.set("hello.world.set", null);
        Assert.assertFalse(node.has("hello.world.set"));
        Assert.assertNull(node.get("hello.world.set"));
    }

    @Test
    public void createNodeTest() {
        ItemNode node = new HashItemNode(null, "root");
        ItemNode subNode = node.createNode("hello.world.set");
        subNode.setPrimitive("asdf", "hello");
        Assert.assertTrue(node.has("hello.world.set.asdf"));
        Assert.assertEquals("hello", node.get("hello.world.set.asdf").asValue().getAsString());
    }

    @Test
    public void shortTest() {
        ItemNode node = new HashItemNode(null, "root");
        ItemNode subNode = node.createNode("hello.world");
        subNode.setPrimitive("short", 3);
        Assert.assertEquals((short) 3, node.get("hello.world.short").asValue().getAsShort());
    }

    @Test
    public void asdf() {
        List<String> list = Arrays.asList("1", "3", "2", "4", "6", "5");
        for (String s : list.stream().sorted().collect(Collectors.toList())) {
            System.out.println(s);
        }

    }

}
