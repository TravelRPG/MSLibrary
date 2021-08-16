package kr.msleague.msgui.managers;

import kr.msleague.msgui.gui.MSGui;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class Test extends MSGui {

    public Test(@NotNull Player who, int size, @Nullable String title, boolean cancel) {
        super(who, size, title, cancel);
    }

    @Override
    public void init() {

    }
}
