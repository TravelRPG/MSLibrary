package kr.msleague.inventory;

import com.github.luben.zstd.Zstd;

import java.util.Arrays;

public class CompressAPI {
    /**
     * 바이트 배열을 압축합니다.
     * 압축하지 않는 경우도 바이트 배열 맨앞에 압축/미압축 여부 플래그를 등록합니다.
     * 바이트 배열 길이가 40 미만일 경우 압축의 효율이 오히려 길이를 늘리는것으로 확인되어
     * 압축하도록 명령하더라도 압축하지 않도록 설계했습니다.
     * @param target 압축할 대상 바이트 배열입니다
     * @param compress 압축할 유무를 선택합니다. (단, 배열 길이가 40 미만일 경우 무시합니다)
     * @return 압축한/하지않은 플래그가 달린 바이트 배열을 반환합니다.
     */
    public static byte[] compress(byte[] target, boolean compress){
        if(compress && target.length > 40){
            byte[] compressed = Zstd.compress(target, 0);
            byte[] nextArray = new byte[compressed.length+1];
            nextArray[0] = 1;
            System.arraycopy(compressed, 0, nextArray, 1, compressed.length);
            return nextArray;
        }else{
            byte[] nonCompressed = new byte[target.length+1];
            nonCompressed[0] = 0;
            System.arraycopy(target, 0, nonCompressed, 1, target.length);
            return nonCompressed;
        }
    }

    /**
     * 바이트 배열을 압축풀기합니다.
     * 위 {@link CompressAPI#compress(byte[], boolean)} 를 사용하여 압축한 배열만을
     * 압축풀기 할 수 있으며, 반환하는 배열은 압축유무 판별용 태그를 제외한 배열을 반환합니다.
     * @param array 압축된 바이트 배열입니다
     * @return 압축풀기된 바이트 배열입니다.
     */
    public static byte[] decompress(byte[] array){
        boolean compressed = array[0] == 1;
        array = Arrays.copyOfRange(array, 1, array.length);
        if(compressed){
            long size = Zstd.decompressedSize(array);
            array = Zstd.decompress(array, (int) size);
        }
        return array;
    }
}
