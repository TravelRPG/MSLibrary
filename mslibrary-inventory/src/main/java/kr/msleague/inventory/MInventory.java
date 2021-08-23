package kr.msleague.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * 버킷의 인벤토리 생성 메커니즘 실행 전에
 * 내부적으로 아이템을 처리하기 위해 사용되는
 * 인벤토리의 인터페이스 형태입니다.
 */
public interface MInventory {
    /**
     * @return 인벤토리의 크기를 반환합니다
     */
    int getSize();

    /**
     * 인벤토리의 크기를 설정할 수 있습니다
     * @param size 인벤토리 크기
     */
    void setSize(int size);

    /**
     * 슬롯에 해당하는 아이템을 가져올 수 있습니다
     * @param slot 대상 슬롯
     * @return 슬롯내 아이템
     */
    ItemStack getItem(int slot);

    /**
     * 아이템을 슬롯에 설정할 수 있습니다
     * @param slot 대상 슬롯
     * @param stack 슬롯에 넣을 아이템
     */
    void setItem(int slot, ItemStack stack);

    /**
     * 위 인벤토리를 버킷 인벤토리를 생성하여 반환합니다
     * @param owner 버킷 인벤토리 생성에 필요한 owner입니다.
     * @param name 버킷 인벤토리 생성에 필요한 이름입니다.
     * @return 생성된 버킷 인벤토리를 반환합니다.
     */
    Inventory asBukkitInventory(InventoryHolder owner, String name);

    /**
     * 생성하지 않고 대상 인벤토리에 모든 아이템을 붙여넣기 합니다.
     * @param to 대상 인벤토리입니다.
     */
    void copyContents(Inventory to);
}
