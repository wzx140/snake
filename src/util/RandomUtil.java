package util;

import java.util.Arrays;

/**
 * the random utils
 */
public class RandomUtil {

    /**
     * produce a random int from begin to end,
     * included begin and end.
     * end should bigger than begin
     */
    public static int restrictedRandom(int begin, int end) {
        int count=end-begin+1;
        int off=begin;
        java.util.Random random = new java.util.Random();
        int result = random.nextInt(count)+off;
        return result;
    }

    /**
     * @see #restrictedRandom(int, int)
     * except this will overlook some int
     */
    public static int restrictedRandom(int begin, int end,Integer[] overlooked) {
        int count=end-begin+1;
        int off=begin;
        java.util.Random random = new java.util.Random();
        int result=random.nextInt(count)+off;
        while (Arrays.asList(overlooked).contains(result)){
            result=random.nextInt(count)+off;
        }
        return result;
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            System.out.println(RandomUtil.restrictedRandom(1, 4, new Integer[]{3}));
//        }
//
//
//    }

}
