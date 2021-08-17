package kr.msleague.mslibrary.customitem.impl.adapters;

import kr.msleague.mslibrary.customitem.api.ItemAdapter;
import kr.msleague.mslibrary.customitem.api.MSItem;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;

public class YamlAdapter implements ItemAdapter<ConfigurationSection> {

    @Nonnull
    @Override
    public MSItem deserialize(@Nonnull ConfigurationSection serialized) throws IllegalArgumentException {
        return null;
    }

    @Nonnull
    @Override
    public ConfigurationSection serialize(@Nonnull MSItem item) throws IllegalArgumentException {
        return null;
    }

}
