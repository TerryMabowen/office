package com.mbw.office;

import cn.hutool.core.io.file.FileReader;
import org.junit.Test;

import java.util.*;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-01 14:03
 */
public class CommonTest {
    @Test
    public void f1() {
        FileReader fileReader = new FileReader("id.md");
        String readString = fileReader.readString();
        List<String> strings = fileReader.readLines();
    }

    @Test
    public void f2() {
        String arr = "2," +
                "3," +
                "4," +
                "5," +
                "6," +
                "7," +
                "8," +
                "9," +
                "12," +
                "13," +
                "14," +
                "15," +
                "16," +
                "19," +
                "21," +
                "22," +
                "23," +
                "24," +
                "25," +
                "26," +
                "28," +
                "29," +
                "30," +
                "31," +
                "33," +
                "34," +
                "35," +
                "37," +
                "38," +
                "39," +
                "40," +
                "51," +
                "52," +
                "53," +
                "57," +
                "58," +
                "62," +
                "63," +
                "65," +
                "66," +
                "67," +
                "68," +
                "70," +
                "71," +
                "72," +
                "73," +
                "79," +
                "80," +
                "81," +
                "82," +
                "83," +
                "85," +
                "86," +
                "89," +
                "91," +
                "92," +
                "93," +
                "94," +
                "100," +
                "101," +
                "104," +
                "105," +
                "106," +
                "107," +
                "108," +
                "119," +
                "124," +
                "127," +
                "128," +
                "129," +
                "130," +
                "131," +
                "132," +
                "133," +
                "134," +
                "135," +
                "136," +
                "137," +
                "142," +
                "143," +
                "144," +
                "145," +
                "146," +
                "147," +
                "148," +
                "149," +
                "150," +
                "151," +
                "153," +
                "154," +
                "155," +
                "156," +
                "157," +
                "158," +
                "159," +
                "160," +
                "161," +
                "162," +
                "164," +
                "165," +
                "166," +
                "167," +
                "171," +
                "172," +
                "173," +
                "174," +
                "175," +
                "176," +
                "177," +
                "178," +
                "179," +
                "180," +
                "181," +
                "183," +
                "184," +
                "187," +
                "188," +
                "189," +
                "195," +
                "196," +
                "197," +
                "198," +
                "199," +
                "200," +
                "201," +
                "202," +
                "203," +
                "204," +
                "206," +
                "207," +
                "213," +
                "214," +
                "215," +
                "217," +
                "218," +
                "221," +
                "222," +
                "225," +
                "227," +
                "228," +
                "229," +
                "230," +
                "231," +
                "232," +
                "233," +
                "234," +
                "242," +
                "249," +
                "252," +
                "255," +
                "256," +
                "268," +
                "269," +
                "270," +
                "271," +
                "273," +
                "274," +
                "275," +
                "276," +
                "277," +
                "278," +
                "279," +
                "280," +
                "281," +
                "282," +
                "283," +
                "314," +
                "315," +
                "316," +
                "317," +
                "318," +
                "319," +
                "320," +
                "321," +
                "322," +
                "323," +
                "324," +
                "326," +
                "327," +
                "328," +
                "329," +
                "337," +
                "338," +
                "339," +
                "340," +
                "341," +
                "348," +
                "349," +
                "350," +
                "354," +
                "355," +
                "356," +
                "357," +
                "358," +
                "359," +
                "368," +
                "377,378,379,380,381,382,383,384,387,388,452,454,465";
        String[] split = arr.split(",");
        List<Integer> list = new ArrayList<>();
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }

        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        System.out.println(list);
        System.out.println(list.size());

        System.out.println("*******************************************************");
        String sr = "2," +
                "3," +
                "4," +
                "5," +
                "6," +
                "7," +
                "8," +
                "9," +
                "12," +
                "13," +
                "14," +
                "15," +
                "16," +
                "19," +
                "21," +
                "22," +
                "23," +
                "24," +
                "25," +
                "26," +
                "28," +
                "29," +
                "30," +
                "31," +
                "33," +
                "34," +
                "35," +
                "37," +
                "38," +
                "39," +
                "40," +
                "51," +
                "52," +
                "53," +
                "57," +
                "58," +
                "62," +
                "63," +
                "65," +
                "66," +
                "67," +
                "68," +
                "70," +
                "71," +
                "72," +
                "73," +
                "79," +
                "80," +
                "81," +
                "82," +
                "83," +
                "85," +
                "86," +
                "89," +
                "91," +
                "92," +
                "93," +
                "94," +
                "100," +
                "101," +
                "104," +
                "105," +
                "106," +
                "107," +
                "108," +
                "119," +
                "124," +
                "127," +
                "128," +
                "129," +
                "130," +
                "131," +
                "132," +
                "133," +
                "134," +
                "135," +
                "136," +
                "137," +
                "142," +
                "143," +
                "144," +
                "145," +
                "146," +
                "147," +
                "148," +
                "149," +
                "150," +
                "151," +
                "153," +
                "154," +
                "155," +
                "156," +
                "157," +
                "158," +
                "159," +
                "160," +
                "161," +
                "162," +
                "164," +
                "165," +
                "166," +
                "167," +
                "171," +
                "172," +
                "173," +
                "174," +
                "175," +
                "176," +
                "177," +
                "178," +
                "179," +
                "180," +
                "181," +
                "183," +
                "184," +
                "187," +
                "188," +
                "189," +
                "195," +
                "196," +
                "197," +
                "198," +
                "199," +
                "200," +
                "201," +
                "202," +
                "203," +
                "204," +
                "206," +
                "207," +
                "213," +
                "214," +
                "215," +
                "217," +
                "218," +
                "221," +
                "222," +
                "225," +
                "227," +
                "228," +
                "229," +
                "230," +
                "231," +
                "232," +
                "233," +
                "234," +
                "242," +
                "249," +
                "252," +
                "255," +
                "256," +
                "268," +
                "269," +
                "270," +
                "271," +
                "273," +
                "274," +
                "275," +
                "276," +
                "277," +
                "278," +
                "279," +
                "280," +
                "281," +
                "282," +
                "283," +
                "314," +
                "315," +
                "316," +
                "317," +
                "318," +
                "319," +
                "320," +
                "321," +
                "322," +
                "323," +
                "324," +
                "326," +
                "327," +
                "328," +
                "329," +
                "337," +
                "338," +
                "339," +
                "340," +
                "341," +
                "347," +
                "348," +
                "349," +
                "350," +
                "354," +
                "355," +
                "356," +
                "357," +
                "358," +
                "359," +
                "368," +
                "377," +
                "378," +
                "379," +
                "380," +
                "381," +
                "382," +
                "383," +
                "384," +
                "387," +
                "388," +
                "452," +
                "454," +
                "465";

        String[] split1 = sr.split(",");
        List<Integer> list2 = new ArrayList<>();
        for (String s : split1) {
            list2.add(Integer.parseInt(s));
        }

        list2.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        System.out.println(list2);
        System.out.println(list2.size());

        Set<Integer> set = new HashSet<>();
        set.addAll(list);
        set.removeAll(list2);
        System.out.println(Arrays.toString(set.toArray()));

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Set<Integer> set1 = new HashSet<>();
        set1.addAll(list2);
        set1.removeAll(list);
        System.out.println(Arrays.toString(set1.toArray()));
    }

    @Test
    public void f3() {
        String fzIdStr = "2," +
                "3," +
                "4," +
                "5," +
                "6," +
                "7," +
                "8," +
                "9," +
                "12," +
                "13," +
                "14," +
                "15," +
                "16," +
                "19," +
                "21," +
                "22," +
                "23," +
                "24," +
                "25," +
                "26," +
                "28," +
                "29," +
                "30," +
                "31," +
                "33," +
                "34," +
                "35," +
                "37," +
                "38," +
                "39," +
                "40," +
                "51," +
                "52," +
                "53," +
                "57," +
                "58," +
                "62," +
                "63," +
                "65," +
                "66," +
                "67," +
                "68," +
                "70," +
                "71," +
                "72," +
                "73," +
                "79," +
                "80," +
                "81," +
                "82," +
                "83," +
                "85," +
                "86," +
                "89," +
                "91," +
                "92," +
                "93," +
                "94," +
                "100," +
                "101," +
                "104," +
                "105," +
                "106," +
                "107," +
                "108," +
                "119," +
                "124," +
                "127," +
                "128," +
                "129," +
                "130," +
                "131," +
                "132," +
                "133," +
                "134," +
                "135," +
                "136," +
                "137," +
                "142," +
                "143," +
                "144," +
                "145," +
                "146," +
                "147," +
                "148," +
                "149," +
                "150," +
                "151," +
                "153," +
                "154," +
                "155," +
                "156," +
                "157," +
                "158," +
                "159," +
                "160," +
                "161," +
                "162," +
                "164," +
                "165," +
                "166," +
                "167," +
                "171," +
                "172," +
                "173," +
                "174," +
                "175," +
                "176," +
                "177," +
                "178," +
                "179," +
                "180," +
                "181," +
                "183," +
                "184," +
                "187," +
                "188," +
                "189," +
                "195," +
                "196," +
                "197," +
                "198," +
                "199," +
                "200," +
                "201," +
                "202," +
                "203," +
                "204," +
                "206," +
                "207," +
                "213," +
                "214," +
                "215," +
                "217," +
                "218," +
                "221," +
                "222," +
                "225," +
                "227," +
                "228," +
                "229," +
                "230," +
                "231," +
                "232," +
                "233," +
                "234," +
                "242," +
                "249," +
                "252," +
                "255," +
                "256," +
                "268," +
                "269," +
                "270," +
                "271," +
                "273," +
                "274," +
                "275," +
                "276," +
                "277," +
                "278," +
                "279," +
                "280," +
                "281," +
                "282," +
                "283," +
                "314," +
                "315," +
                "316," +
                "317," +
                "318," +
                "319," +
                "320," +
                "321," +
                "322," +
                "323," +
                "324," +
                "326," +
                "327," +
                "328," +
                "329," +
                "337," +
                "338," +
                "339," +
                "340," +
                "341," +
                "348," +
                "349," +
                "350," +
                "354," +
                "355," +
                "356," +
                "357," +
                "358," +
                "359," +
                "368," +
                "377," +
                "378," +
                "379," +
                "380," +
                "381," +
                "382," +
                "383," +
                "384," +
                "387," +
                "388," +
                "452," +
                "454," +
                "465";
        String[] split = fzIdStr.split(",");
        List<Integer> list = new ArrayList<>();
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }

        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        System.out.println(list);
        System.out.println(list.size());
    }
}
