package kr.msleague.bgmlib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCode {
    static List<String> codeString = new ArrayList<>();
    static {
        for(char a = 'A'; a <= 'Z'; a++) {
            codeString.add(Character.toString(a));
        }
        for(char a = 'a'; a <= 'z'; a++) {
            codeString.add(Character.toString(a));
        }
        for(int i = 0; i<10 ; i++) {
            codeString.add(Integer.toString(i));
        }
    }
    public static String codeSize(Integer codeSize) {
        Random random = new Random();
        String code = "";
        int maxInt = codeString.size()-1;
        for(int i = 0 ; i < codeSize+1 ; i++) {
            int randomInt = random.nextInt(maxInt);
            code = code+codeString.get(randomInt);
        }
        return code;
    }
}
