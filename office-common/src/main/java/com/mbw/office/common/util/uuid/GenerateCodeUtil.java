package com.mbw.office.common.util.uuid;



import com.mbw.office.common.util.date.DateUtil;

import java.util.*;

/**
 * 生成各种编号的工具类
 *
 * @author Mabowen
 * @date 2020-04-08 21:31
 */
public class GenerateCodeUtil {
    private final static String DATE_FORMAT = "yyyyMMddHHmmssSSS";
    private static int sed  = 0;
    private static String[] beforeShuffle = new String[]
            {"2", "3", "4", "5", "6", "7",
            "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J",
            "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "m", "n", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z"};
    private static Random rd = new Random();

    /**
     * 生成一个20位的唯一的字符串
     * @author Mabowen
     * @date 21:32 2020-04-08
     */
    public synchronized static String generateClientId() {
        String prefix = DateUtil.format(new Date(), DATE_FORMAT);

        synchronized (GenerateCodeUtil.class) {
            sed++;
            if (sed > 999) {
                sed = 1;
            }
        }

        String suffix = String.format("%03d", sed);

        return prefix + suffix;
    }

    /**
     * 生成一个四位的字符串
     * @author Mabowen
     * @date 10:05 2020-04-09
     */
    public synchronized static String generatePayId() {
        List<String> list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        String afterShuffle = sb.toString();

        Random random = new Random();
        int index = random.nextInt(beforeShuffle.length);
        if (index <= beforeShuffle.length - 4) {
            return afterShuffle.substring(index, index + 4);
        } else {
            return afterShuffle.substring(5, 9);
        }
    }

    public static String getUuid1() {
        return UUID.randomUUID().toString();
    }

    public static String getUuid2() {
        return getUuid1().replace("-", "");
    }
}
