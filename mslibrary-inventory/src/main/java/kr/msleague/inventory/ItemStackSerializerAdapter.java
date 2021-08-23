package kr.msleague.inventory;

import org.bukkit.inventory.ItemStack;

public interface ItemStackSerializerAdapter {
    /**
     * 아이템스택을 직렬화하는 정책을 표준화한 어댑터입니다.
     * @param itemStack 직렬화 대상 아이템스택입니다
     * @param compress 압축 유무를 설정할 수 있습니다
     * @return 직렬화된 아이템스택을 반환합니다.
     */
    byte[] serialize(ItemStack itemStack, boolean compress);

    /**
     * 아이템스택을 역직렬화하는 정책을 표준화한 어댑터입니다.
     * @param array 역직렬화 대상 바이트 배열입니다.
     * @return 역직렬화 후의 아이템스택을 반환합니다.
     */
    ItemStack deserialize(byte[] array);
}
