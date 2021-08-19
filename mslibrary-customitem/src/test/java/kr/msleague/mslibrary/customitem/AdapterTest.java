package kr.msleague.mslibrary.customitem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kr.msleague.mslibrary.customitem.api.ItemNode;
import kr.msleague.mslibrary.customitem.api.MSItemData;
import kr.msleague.mslibrary.customitem.impl.serializers.JsonSerializer;
import kr.msleague.mslibrary.customitem.impl.serializers.MappingSerializer;
import kr.msleague.mslibrary.customitem.impl.serializers.YamlSerializer;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import kr.msleague.mslibrary.customitem.impl.node.MSLItemData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class AdapterTest {

    @Test
    public void deserializeYamlTest(){
        YamlSerializer adapter = new YamlSerializer();
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("id", 1);
        yaml.set("version", 2);
        yaml.set("hello.world.int", 3);
        yaml.set("hello.world.double", 4d);
        String[] asdf = {"1", "2", "3"};
        yaml.set("hello.world.array", Arrays.asList(asdf));
        MSItemData item = adapter.deserialize(yaml);
        ItemNode node = item.getNodes();
        Assert.assertEquals(1, node.get("id").asValue().getAsInt());
        Assert.assertEquals(2, node.get("version").asValue().getAsInt());
        Assert.assertEquals(3, node.get("hello.world.int").asValue().getAsInt());
        Assert.assertEquals(4d, node.get("hello.world.double").asValue().getAsDouble(), 0d);
        Assert.assertEquals(3, node.get("hello.world.array").asArray().size());
    }
    
    @Test
    public void serializeYamlTest(){
        YamlSerializer adapter = new YamlSerializer();
        ItemNode node = new HashItemNode(null, "");
        node.setValue("id", 1);
        node.setValue("version", 2);
        node.setValue("hello.world.int", 3);
        node.setValue("hello.world.double", 4d);
        node.createArray("hello.world.array").addPrimitive("1");
        node.createArray("hello.world.array").addPrimitive("2");
        node.createArray("hello.world.array").addPrimitive("3");
        YamlConfiguration config = adapter.serialize(new MSLItemData(node));
        Assert.assertEquals(1, config.getInt("id"));
        Assert.assertEquals(2, config.getInt("version"));
        Assert.assertEquals(3, config.getInt("hello.world.int"));
        Assert.assertEquals(4d, config.getDouble("hello.world.double"), 0d);
        Assert.assertEquals(3, config.getList("hello.world.array").size());
    }

    @Test
    public void deserializeJsonTest(){
        JsonSerializer adapter = new JsonSerializer();
        JsonObject obj = new JsonObject();
        obj.addProperty("id", 1);
        obj.addProperty("version", 2);
        obj.add("hello", new JsonObject());
        JsonObject world = new JsonObject();
        world.addProperty("int", 3);
        world.addProperty("double", 4d);
        JsonArray array = new JsonArray();
        array.add("1");
        array.add("2");
        array.add("3");
        world.add("array", array);
        obj.getAsJsonObject("hello").add("world", world);
        MSItemData item = adapter.deserialize(obj);
        ItemNode node = item.getNodes();
        Assert.assertEquals(1, node.get("id").asValue().getAsInt());
        Assert.assertEquals(2, node.get("version").asValue().getAsInt());
        Assert.assertEquals(3, node.get("hello.world.int").asValue().getAsInt());
        Assert.assertEquals(4d, node.get("hello.world.double").asValue().getAsDouble(), 0d);
        Assert.assertEquals(3, node.get("hello.world.array").asArray().size());
    }

    @Test
    public void serializeJsonTest(){
        JsonSerializer adapter = new JsonSerializer();
        ItemNode node = new HashItemNode(null, "");
        node.setValue("id", 1);
        node.setValue("version", 2);
        node.setValue("hello.world.int", 3);
        node.setValue("hello.world.double", 4d);
        node.createArray("hello.world.array").addPrimitive(1);
        node.createArray("hello.world.array").addPrimitive(2.5d);
        node.createArray("hello.world.array").addPrimitive("3");
        JsonObject obj = adapter.serialize(new MSLItemData(node));
        Assert.assertEquals(1, obj.get("id").getAsInt());
        Assert.assertEquals(2, obj.get("version").getAsInt());
        Assert.assertEquals(3, obj.get("hello").getAsJsonObject().get("world").getAsJsonObject().get("int").getAsInt());
        Assert.assertEquals( 4d, obj.get("hello").getAsJsonObject().get("world").getAsJsonObject().get("double").getAsDouble(), 0d);
        JsonArray array = obj.get("hello").getAsJsonObject().get("world").getAsJsonObject().get("array").getAsJsonArray();
        Assert.assertEquals(3, array.size());
        System.out.println(obj);
    }

    @Test
    public void remapTest(){
        JsonSerializer adapter = new JsonSerializer();
        MappingSerializer mapping = new MappingSerializer();
        ItemNode node = new HashItemNode(null, "");
        node.setValue("id", 1);
        node.setValue("version", 2);
        node.setValue("hello.world.int", 3);
        node.setValue("hello.world.double", 4d);
        node.createArray("hello.world.array").addPrimitive(1);
        node.createArray("hello.world.array").addPrimitive(2.5d);
        node.createArray("hello.world.array").addPrimitive("3");

        MSItemData mapped = mapping.serialize(new MSLItemData(node));
        ItemNode mappedNode = mapped.getNodes();
        Assert.assertEquals(1, mappedNode.get("id").asValue().getAsInt());
        Assert.assertEquals(2, mappedNode.get("version").asValue().getAsInt());
        Assert.assertEquals(3, mappedNode.get("hello.world.int").asValue().getAsInt());
        Assert.assertEquals(4d, mappedNode.get("hello.world.double").asValue().getAsDouble(), 0d);
        Assert.assertEquals(1, mappedNode.get("hello.world.array.$0").asValue().getAsInt());
        Assert.assertEquals(2.5d, mappedNode.get("hello.world.array.$1").asValue().getAsDouble(), 0d);
        Assert.assertEquals("3", mappedNode.get("hello.world.array.$2").asValue().getAsString());

        MSItemData demapped = mapping.deserialize(mapped);
        Assert.assertEquals(1, demapped.getNodes().get("id").asValue().getAsInt());
        Assert.assertEquals(2, demapped.getNodes().get("version").asValue().getAsInt());
        Assert.assertEquals(3, demapped.getNodes().get("hello.world.int").asValue().getAsInt());
        Assert.assertEquals(4d, demapped.getNodes().get("hello.world.double").asValue().getAsDouble(), 0d);
        Assert.assertEquals(3, demapped.getNodes().get("hello.world.array").asArray().size());
    }

    @Test
    public void demapTest(){

    }
}
