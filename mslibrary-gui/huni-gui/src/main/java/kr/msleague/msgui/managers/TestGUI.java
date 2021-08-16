package kr.msleague.msgui.managers;

import kr.msleague.msgui.api.annotations.MSGuiPage;
import kr.msleague.msgui.gui.MSGui;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestGUI extends MSGui {

    public TestGUI(@NotNull Player who, int size, @Nullable String title) {
        super(who, size, title);
    }

    @Override
    public void init() {
        // initializer

    }

    @MSGuiPage(pagePriority = 0)
    public void mainPage() {

    }

    @MSGuiPage(pagePriority = 1)
    public void subPage1() {

    }

    @MSGuiPage(pagePriority = 2)
    public void subPage2() {

    }


}
