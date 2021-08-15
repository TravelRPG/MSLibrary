package kr.msleague.inventory;

import com.github.luben.zstd.Zstd;

import java.util.Arrays;

public class CompressAPI {
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
