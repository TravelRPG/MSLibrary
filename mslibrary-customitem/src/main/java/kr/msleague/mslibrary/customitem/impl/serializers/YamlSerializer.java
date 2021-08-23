package kr.msleague.mslibrary.customitem.impl.serializers;

import kr.msleague.mslibrary.customitem.api.*;
import kr.msleague.mslibrary.customitem.impl.node.HashItemNode;
import kr.msleague.mslibrary.customitem.impl.node.MSLItemData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class YamlSerializer implements ItemSerializer<YamlConfiguration> {

    @Nonnull
    @Override
    public MSItemData deserialize(@Nonnull YamlConfiguration section) throws IllegalArgumentException {
        HashItemNode root = new HashItemNode(null, section.getName());
        for (String path : section.getKeys(true)) {
            if (!section.isConfigurationSection(path)) {
                if (section.isList(path)) {
                    ItemNodeArray array = root.createArray(path);
                    List<String> list = section.getStringList(path);
                    for (String s : list) {
                        array.addPrimitive(s);
                    }
                } else {
                    root.setPrimitive(path, section.get(path));
                }
            }
        }
        return new MSLItemData(root);
    }

    @Nonnull
    @Override
    public YamlConfiguration serialize(@Nonnull MSItemData item) throws IllegalArgumentException {
        ItemNode root = item.getNodes();
        YamlConfiguration section = new YamlConfiguration();
        write(section, root);
        return section;
    }

    private void write(ConfigurationSection section, ItemElement element) {
        if (element instanceof ItemNode) {
            write(section, element.asNode());
        } else if (element instanceof ItemNodeArray) {
            List<Object> list = new ArrayList<>();
            for (ItemElement content : element.asArray().contents()) {
                if (content instanceof ItemNodeValue) {
                    ItemNodeValue value = content.asValue();
                    list.add(value.getAsString());
                }
            }
            section.set(element.getPath(), list);
        } else if (element instanceof ItemNodeValue) {
            write(section, element.asValue());
        }
    }

    private void write(ConfigurationSection section, ItemNode node) {
        for (String key : node.getKeys()) {
            write(section, node.get(key));
        }
    }

    private void write(ConfigurationSection section, ItemNodeValue value) {
        if (value.isBoolean()) {
            section.set(value.getPath(), value.getAsBoolean());
        } else if (value.isNumber()) {
            section.set(value.getPath(), value.getAsNumber());
        } else if (value.isString()) {
            section.set(value.getPath(), value.getAsString());
        }
    }

}
