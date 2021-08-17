package kr.msleague.msgui.gui.button;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemFlag;

import java.util.List;
import java.util.Set;

public class MSGuiButtonBuilder {

    @Getter private MSGuiButtonType type = MSGuiButtonType.ITEM_STACK;
    @Getter private Material material = Material.AIR;
    @Getter private int durability = 0;
    @Getter private String display = null;
    @Getter private List<String> lore = null;
    @Getter private Set<ItemFlag> itemFlags = null;
    @Getter private boolean glow = false;
    @Getter private MSGuiButtonAction action = null;
    @Getter private boolean cancel = false;
    @Getter private boolean cleanable = false;

    public MSGuiButtonBuilder(Material material, int durability) {
        this.material = material;
        this.durability = durability;
    }
    public MSGuiButtonBuilder(Material material) { this(material, 0); }
    public MSGuiButtonBuilder(String url) {}
    public MSGuiButtonBuilder(OfflinePlayer offlinePlayer) {}

    /**
     * 버튼에 로어 한 줄 추가.
     * @param line 라인
     * @return
     */
    public MSGuiButtonBuilder addLore(String line) { return this; }

    /**
     * 버튼의 디스플레이 네임 설정.
     * @param displayName 디스플레이 네임
     * @return
     */
    public MSGuiButtonBuilder setDisplayName(String displayName) { return this; }

    /**
     * 버튼의 로어를 설정함.
     * @param lore 로어
     * @return
     */
    public MSGuiButtonBuilder setLore(List<String> lore) { return this; }

    /**
     * 버튼 로어 중 index 번 째 로어를 지웁니다.
     * @param index 지울 라인 번호
     * @return
     */
    public MSGuiButtonBuilder removeLore(int index) { return this; }

    /**
     * 버튼에 아이템 플래그를 추가합니다.
     * @param itemFlags 아이템 플래그 (가변)
     * @return
     */
    public MSGuiButtonBuilder addItemFlags(ItemFlag... itemFlags) { return this; }

    /**
     * 버튼을 빛나게 할 지를 결정합니다.
     * @param glow boolean
     * @return
     */
    public MSGuiButtonBuilder setGlow(boolean glow) { return this; }

    /**
     * 버튼을 클릭 했을 때, 실행 될 액션을 설정합니다.
     * @param action MSButtonAction 의 event 는 InventoryClickEvent 입니다.
     * @return
     */
    public MSGuiButtonBuilder setAction(MSGuiButtonAction action) { return this; }

    /**
     * 해당 버튼에 대한 이벤트 캔슬 여부를 설정합니다.
     * @param cancel
     * @return
     */
    public MSGuiButtonBuilder setCancellable(boolean cancel){ return this; }

    /**
     * MSGui#refresh() 메서드 호출 시, 아이템이 지워지는것에 대한 설정을 합니다.
     * 기본 값은 false 입니다.
     * @param clean
     * @return
     */
    public MSGuiButtonBuilder setCleanable(boolean clean) { return this; }
    public MSGuiButton build() { return new MSGuiButton(); }

}
