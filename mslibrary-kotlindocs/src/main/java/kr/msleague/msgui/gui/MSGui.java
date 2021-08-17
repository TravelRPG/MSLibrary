package kr.msleague.msgui.gui;

import kr.msleague.bootstrap.MSPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class MSGui<V> {

    //private Map<Integer>
    private Player who;
    @Nullable
    public Player getPlayer() { return viewerUniqueId==null? null: MSPlugin.getServer().getPlayer(viewerUniqueId); }
    @Getter
    private int size = 0;
    @Getter
    private String title = null;
    @Getter
    @Setter
    private boolean cancelGUI = false;
    private int currentPage = 0;
    private List<V> objs = null;
    private List<Method> pages = null;
    private Inventory baseInventory = null;
    private Inventory inventory = null;
    private UUID viewerUniqueId = null;

    public Inventory getInventory() {
        if(baseInventory != null) inventory = baseInventory;
        else if(inventory == null) inventory = title != null? MSPlugin.getServer().createInventory(null, size, title): MSPlugin.getServer().createInventory(null, size);
        return inventory;
    }

    public int getPage() { return currentPage - 1; }
    public int getMaxPage() { return pages == null? 0 : pages.size(); }
    public boolean getHasPrevPage() { return getPage() > 0; }
    public boolean getHasNextPage() {
        int page = getPage();
        return page >= 0 && page < getMaxPage() -1;
    }

    public MSGui(Player who, int size, String title) {
        this.who = who;
        this.size = size;
        this.title = title;
        initializer();
    }
    @SafeVarargs
    public MSGui(Player who, int size, String title, V... args) {
        this(who,size,title);
        int length = args.length;
        if(length == 0) objs = Collections.emptyList();
        else if(length == 1) objs = Collections.singletonList(args[0]);
        else objs = Arrays.asList(args);
    }
    public MSGui(Player who, int size, String title, boolean cancel) {
        this(who,size,title);
        cancelGUI = cancel;
    }
    @SafeVarargs
    public MSGui(Player who, int size, String title, boolean cancel, V... args) {
        this(who, size, title, args);
        cancelGUI = cancel;
    }
    public MSGui(Player who, Inventory inventory) {
        this(who, inventory.getSize(), inventory.getTitle());
        baseInventory = inventory;
    }
    public MSGui(Player who, boolean cancel, Inventory inventory) {
        this(who, inventory);
        cancelGUI = cancel;
    }
    @SafeVarargs
    public MSGui(Player who, boolean cancel, Inventory inventory, V... args) { this(who, inventory.getSize(), inventory.getTitle(), cancel, args); }

    private void initializer() throws IllegalArgumentException {
        if(size % 9 != 0 || (size < 0 || size > 54)) throw new IllegalArgumentException("inventory invalid size error : "+size);
        else {

        }
    }

    abstract void init();
    public void refresh() {

    }
    public void refresh(boolean clear) {

    }
    public void asyncRefresh() {

    }
    public void asyncRefresh(boolean clear) {

    }
    public boolean openNextPage(boolean clear, boolean async) {
        if(!getHasNextPage()) return false;
        currentPage++;
        if(async) asyncRefresh(clear);
        else refresh(clear);
        return true;
    }

    public boolean openPrevPage(boolean clear, boolean async) {
        if(!getHasPrevPage()) return false;
        currentPage--;
        if(async) asyncRefresh(clear);
        else refresh(clear);
        return true;
    }

    private void onClick(InventoryClickEvent e) {

    }

    public void onClose(InventoryCloseEvent e) {

    }
    public void onDrag(InventoryDragEvent e) { if(cancelGUI) e.setCancelled(true); }

}
