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

    private Player who;

    /**
     * 해당 gui 를 열고있는 Player 객체를 얻어옵니다.
     * @return Nullable Player
     */
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

    /**
     * Inventory 객체를 얻어옵니다.
     * @return 인벤토리
     */
    public Inventory getInventory() {
        if(baseInventory != null) inventory = baseInventory;
        else if(inventory == null) inventory = title != null? MSPlugin.getServer().createInventory(null, size, title): MSPlugin.getServer().createInventory(null, size);
        return inventory;
    }

    /**
     * 현재 페이지를 얻어올 수 있습니다.
     * 페이지가 없는 경우, 기본값은 -1 입니다.
     * 있는 경우, 기본값은 0 입니다.
     * @return 현재 페이지
     */
    public int getPage() { return currentPage - 1; }

    /**
     * 마지막 페이지를 얻어올 수 있습니다.
     * 페이지가 없는 경우, 기본값은 0 입니다.
     * @return 페이지의 수
     */
    public int getMaxPage() { return pages == null? 0 : pages.size(); }

    /**
     * 이전 페이지가 있는지에 대한 여부를 확인할 수 있습니다.
     * @return boolean
     */
    public boolean getHasPrevPage() { return getPage() > 0; }

    /**
     * 다음 페이지가 있는지에 대한 여부를 확인할 수 있습니다.
     * @return boolean
     */
    public boolean getHasNextPage() {
        int page = getPage();
        return page >= 0 && page < getMaxPage() -1;
    }

    /**
     * MSGui 를 생성하고 오픈합니다.
     * @param who 보여질 대상.
     * @param size 크기 ( 0 이상, 53 이하, 9의 배수 )
     * @param title gui 타이틀
     */
    public MSGui(Player who, int size, String title) {
        this.who = who;
        this.size = size;
        this.title = title;
        initializer();
    }

    /**
     * MSGui 에 객체를 전달하여 생성하고 오픈합니다.
     * @param who 보여질 대상.
     * @param size 크기 ( 0 이상, 53 이하, 9의 배수 )
     * @param title gui 타이틀
     * @param args 전달 할 객체
     */
    @SafeVarargs
    public MSGui(Player who, int size, String title, V... args) {
        this(who,size,title);
        int length = args.length;
        if(length == 0) objs = Collections.emptyList();
        else if(length == 1) objs = Collections.singletonList(args[0]);
        else objs = Arrays.asList(args);
    }

    /**
     * MSGui 를 생성하고 오픈합니다.
     * @param who 보여질 대상.
     * @param size 크기 ( 0 이상, 53 이하, 9의 배수 )
     * @param title gui 타이틀
     * @param cancel gui 전체 이벤트 캔슬 여부
     */
    public MSGui(Player who, int size, String title, boolean cancel) {
        this(who,size,title);
        cancelGUI = cancel;
    }

    /**
     * MSGui 에 객체를 전달하여 생성하고 오픈합니다.
     * @param who 보여질 대상.
     * @param size 크기 ( 0 이상, 53 이하, 9의 배수 )
     * @param title gui 타이틀
     * @param cancel gui 전체 이벤트 캔슬 여부
     * @param args 전달 할 객체
     */
    @SafeVarargs
    public MSGui(Player who, int size, String title, boolean cancel, V... args) {
        this(who, size, title, args);
        cancelGUI = cancel;
    }

    /**
     * MSGui 를 Inventory 기반으로 생성하고 오픈합니다.
     * @param who 보여질 대상.
     * @param inventory 인벤토리
     */
    public MSGui(Player who, Inventory inventory) {
        this(who, inventory.getSize(), inventory.getTitle());
        baseInventory = inventory;
    }

    /**
     * MSGui 를 Inventory 기반으로 생성하고 오픈합니다.
     * @param who 보여질 대상.
     * @param cancel gui 전체 이벤트 캔슬 여부
     * @param inventory 인벤토리
     */
    public MSGui(Player who, boolean cancel, Inventory inventory) {
        this(who, inventory);
        cancelGUI = cancel;
    }

    /**
     * MSGui 에 객체를 전달하고 Inventory 기반으로 생성하고 오픈합니다.
     * @param who 보여질 대상.
     * @param cancel gui 전체 이벤트 캔슬 여부
     * @param inventory 인벤토리
     * @param args 전달 할 객체
     */
    @SafeVarargs
    public MSGui(Player who, boolean cancel, Inventory inventory, V... args) { this(who, inventory.getSize(), inventory.getTitle(), cancel, args); }

    /**
     * MSGui 의 초기 설정 메서드입니다.
     * @throws IllegalArgumentException 사이즈를 잘못 기입 한 경우 Exception 이 발생합니다.
     */
    private void initializer() throws IllegalArgumentException {
        if(size % 9 != 0 || (size < 0 || size > 54)) throw new IllegalArgumentException("inventory invalid size error : "+size);
        else {

        }
    }

    /**
     * MSGui 의 초기 설정 메서드입니다.
     * 페이지가 있는 gui 의 refresh() 에서는 호출되지 않습니다.
     */
    abstract public void init();

    /**
     * MSGui 를 새로 고칩니다.
     * Inventory 를 clear 하지 않습니다.
     */
    public void refresh() {

    }

    /**
     * MSGui 를 새로 고칩니다.
     * @param clear Inventory clear 여부
     */
    public void refresh(boolean clear) {

    }

    /**
     * MSGui 를 비동기로 새로 고칩니다.
     */
    public void asyncRefresh() {

    }
    /**
     * MSGui 를 비동기로 새로 고칩니다.
     * @param clear Inventory clear 여부
     */
    public void asyncRefresh(boolean clear) {

    }

    /**
     * 다음 페이지를 엽니다.
     * 페이지가 없는 gui 거나, 다음 페이지가 없는 경우, 열지 않습니다.
     * @param clear Inventory clear 여부
     * @param async 비동기 여부
     * @return 열었는지에 대한 결과
     */
    public boolean openNextPage(boolean clear, boolean async) {
        if(!getHasNextPage()) return false;
        currentPage++;
        if(async) asyncRefresh(clear);
        else refresh(clear);
        return true;
    }

    /**
     * 이전 페이지를 엽니다.
     * 페이지가 없는 gui 거나, 이전 페이지가 없는 경우, 열지 않습니다.
     * @param clear Inventory clear 여부
     * @param async 비동기 여부
     * @return 열었는지에 대한 결과
     */
    public boolean openPrevPage(boolean clear, boolean async) {
        if(!getHasPrevPage()) return false;
        currentPage--;
        if(async) asyncRefresh(clear);
        else refresh(clear);
        return true;
    }

    /**
     * 인벤토리를 클릭했을 때, 호출 됩니다.
     * gui 의 버튼에 대한 기본적인 동작이 들어있기 때문에,
     * override 시, super(e) 로 호출을 하지 않으면 버튼에 대한 Action 이 실행되지 않습니다.
     * @param e InventoryClickEvent
     */
    public void onClick(InventoryClickEvent e) {

    }

    /**
     * 인벤토리를 닫았을 때, 호출 됩니다.
     * @param e InventoryCloseEvent
     */
    public void onClose(InventoryCloseEvent e) {

    }

    /**
     * 인벤토리를 드래그 했을 때, 호출 됩니다.
     * @param e InventoryDragEvent
     */
    public void onDrag(InventoryDragEvent e) { if(cancelGUI) e.setCancelled(true); }

}
