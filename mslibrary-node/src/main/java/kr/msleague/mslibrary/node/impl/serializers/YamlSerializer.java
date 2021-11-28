package kr.msleague.mslibrary.node.impl.serializers;

import kr.msleague.mslibrary.node.api.*;
import kr.msleague.mslibrary.node.impl.node.HashMSNode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class YamlSerializer implements MSNodeSerializer<YamlConfiguration> {

    @Nonnull
    @Override
    public MSNode deserialize(@Nonnull YamlConfiguration section) throws IllegalArgumentException {
        HashMSNode root = new HashMSNode(null, section.getName());
        for (String path : section.getKeys(true)) {
            if (!section.isConfigurationSection(path)) {
                if (section.isList(path)) {
                    MSNodeArray array = root.createArray(path);
                    List<String> list = section.getStringList(path);
                    for (String s : list) {
                        array.addPrimitive(s);
                    }
                } else {
                    root.setPrimitive(path, section.get(path));
                }
            }
        }
        return root;
    }

    @Nonnull
    @Override
    public YamlConfiguration serialize(@Nonnull MSNode root) throws IllegalArgumentException {
        YamlConfiguration section = new YamlConfiguration();
        write(section, root);
        return section;
    }

    private void write(ConfigurationSection section, MSNodeElement element) {
        if (element instanceof MSNode) {
            write(section, element.asNode());
        } else if (element instanceof MSNodeArray) {
            List<Object> list = new ArrayList<>();
            for (MSNodeElement content : element.asArray().contents()) {
                if (content instanceof MSNodeValue) {
                    MSNodeValue value = content.asValue();
                    list.add(value.getAsString());
                }
            }
            section.set(element.getPath(), list);
        } else if (element instanceof MSNodeValue) {
            write(section, element.asValue());
        }
    }

    private void write(ConfigurationSection section, MSNode node) {
        for (String key : node.getKeys()) {
            write(section, node.get(key));
        }
    }

    private void write(ConfigurationSection section, MSNodeValue value) {
        if (value.isBoolean()) {
            section.set(value.getPath(), value.getAsBoolean());
        } else if (value.isNumber()) {
            section.set(value.getPath(), value.getAsNumber());
        } else if (value.isString()) {
            section.set(value.getPath(), value.getAsString());
        }
    }

}
