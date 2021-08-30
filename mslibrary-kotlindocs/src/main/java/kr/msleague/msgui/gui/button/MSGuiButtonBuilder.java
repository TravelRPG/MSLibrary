package kr.msleague.msgui.gui.button;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemFlag;

import java.util.List;
import java.util.Set;

/**
 * MSGuiButton 을 생성하기 위한 Builder
 */
public class MSGuiButtonBuilder {

    @Getter
    private MSGuiButtonType type = MSGuiButtonType.ITEM_STACK;
    @Getter
    private Material material = Material.AIR;
    @Getter
    private int durability = 0;
    @Getter
    private String display = null;
    @Getter
    private List<String> lore = null;
    @Getter
    private Set<ItemFlag> itemFlags = null;
    @Getter
    private boolean glow = false;
    @Getter
    private MSGuiButtonAction action = null;
    @Getter
    private boolean cancel = false;
    @Getter
    private boolean cleanable = false;

    /**
     * ITEM_STACK 타입으로 MSGuiButtonBuilder 를 생성합니다.
     *
     * @param material   Material
     * @param durability durability
     */
    public MSGuiButtonBuilder(Material material, int durability) {
        this.material = material;
        this.durability = durability;
    }

    /**
     * ITEM_STACK 타입으로 MSGuiButtonBuilder 를 생성합니다.
     *
     * @param material Material
     */
    public MSGuiButtonBuilder(Material material) {
        this(material, 0);
    }

    /**
     * CUSTOM_HEAD 타입으로 MSGuiButtonBuilder 를 생성합니다.
     *
     * @param url MCHeads 사이트의 Minecraft-URL 를 복사하여 넣을 수 있습니다.
     */
    public MSGuiButtonBuilder(String url) {
    }

    /**
     * PLAYER_HEAD 타입으로 MSGuiButtonBuilder 를 생성합니다.
     *
     * @param offlinePlayer 오프라인 플레이어
     */
    public MSGuiButtonBuilder(OfflinePlayer offlinePlayer) {
    }

    /**
     * 버튼에 로어 한 줄 추가.
     *
     * @param line 라인
     * @return MSGuiButtonBuilder
     */
    public MSGuiButtonBuilder addLore(String line) {
        return this;
    }

    /**
     * 버튼의 디스플레이 네임 설정.
     *
     * @param displayName 디스플레이 네임
     * @return MSGuiButtonBuilder
     */
    public MSGuiButtonBuilder setDisplayName(String displayName) {
        return this;
    }

    /**
     * 버튼의 로어를 설정함.
     *
     * @param lore 로어
     * @return MSGuiButtonBuilder
     */
    public MSGuiButtonBuilder setLore(List<String> lore) {
        return this;
    }

    /**
     * 버튼 로어 중 index 번 째 로어를 지웁니다.
     *
     * @param index 지울 라인 번호
     * @return MSGuiButtonBuilder
     */
    public MSGuiButtonBuilder removeLore(int index) {
        return this;
    }

    /**
     * 버튼에 아이템 플래그를 추가합니다.
     *
     * @param itemFlags 아이템 플래그 (가변)
     * @return MSGuiButtonBuilder
     */
    public MSGuiButtonBuilder addItemFlags(ItemFlag... itemFlags) {
        return this;
    }

    /**
     * 버튼을 빛나게 할 지를 결정합니다.
     *
     * @param glow boolean
     * @return MSGuiButtonBuilder
     */
    public MSGuiButtonBuilder setGlow(boolean glow) {
        return this;
    }

    /**
     * 버튼을 클릭 했을 때, 실행 될 액션을 설정합니다.
     *
     * @param action MSButtonAction 의 event 는 InventoryClickEvent 입니다.
     * @return MSGuiButtonBuilder
     */
    public MSGuiButtonBuilder setAction(MSGuiButtonAction action) {
        return this;
    }

    /**
     * 해당 버튼에 대한 이벤트 캔슬 여부를 설정합니다.
     *
     * @param cancel 캔슬 여부
     * @return MSGuiButtonBuilder
     */
    public MSGuiButtonBuilder setCancellable(boolean cancel) {
        return this;
    }

    /**
     * MSGui#refresh() 메서드 호출 시, 아이템이 지워지는것에 대한 설정을 합니다.
     * 기본 값은 false 입니다.
     *
     * @param clean 지우기 여부
     * @return MSGuiButtonBuilder
     */
    public MSGuiButtonBuilder setCleanable(boolean clean) {
        return this;
    }

    /**
     * MSGuiButton 객체를 생성합니다.
     *
     * @return MSGuiButton
     */
    public MSGuiButton build() {
        return new MSGuiButton();
    }

}
