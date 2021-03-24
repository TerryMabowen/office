package com.mbw.office;

import cn.hutool.core.io.file.FileReader;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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
        String arr =
                "62b068d1-7803-43a0-83d0-340a4817ca92," +
                "aad6b510-8dd7-4f24-94b1-2a1e66b8c497," +
                "fb50b7c6-2a7d-477c-801f-fccfcf9f914f," +
                "245e16e3-8797-4a6e-ad4e-809aa29d38db," +
                "36e0c992-4d39-4931-8162-8de6f4a99ab4," +
                "86e31a96-3a1a-451d-b5b9-d5383e8895a7," +
                "03c54554-e1c4-4bc9-9d1e-8992e51977d5," +
                "54239f8b-ea36-463b-ba12-754a71674ec2," +
                "11df6f87-b89f-41af-b544-4d78ac1b44af," +
                "fafa240e-0b74-49e7-95d1-e0a116abc2fc," +
                "c9323cae-b274-477e-bb6b-4ed3dd84442b," +
                "bee25669-07bc-4301-8a6f-39951fb12f53," +
                "9f405b5a-a30b-4c9f-a9d2-d0bfe9c6d493," +
                "dbca52e6-f2ce-4834-b40a-3a6a2354a708," +
                "5a3b61fd-6f39-4fd3-9402-7eef9d2dda57," +
                "4434c8cf-4e27-4872-96bc-7e0593b956f1,"
                ;
        String[] split = arr.split(",");
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(split));
        System.out.println(list.size());

        System.out.println("*******************************************************");
        String sr =
                "002b1111-db5a-4876-8651-b0fe039181b4," +
                "00474956-508c-4866-b03b-b4afbb272a41," +
                "0096f294-c26b-40ab-9a0f-24d8c704836f," +
                "00c28130-79fa-430b-b3fe-70f981ff26ed," +
                "00ca225f-0fa4-4363-9715-4b2445e0219d," +
                "00cb29df-cef6-4916-9d8f-6b6bc98a490c," +
                "00e6c65b-e3fe-4466-81f9-1fc2ddf6ecfe," +
                "00f49aa1-b57e-4160-ad7e-c0562b56b465," +
                "00fc6d83-2e27-4525-92d7-bd8f7cd15fea," +
                "01240ee7-5b42-449f-b0cb-0520fceb05df," +
                "012dea76-d905-49b2-bf23-ea84b3d747ee," +
                "01323bb9-5219-478c-abb3-6afd01a423d1," +
                "014c141f-5529-41b3-b6ed-0c084d4dbc35," +
                "01dd1739-1176-4891-9388-6f821456902b," +
                "0210daee-7d8d-4e48-8feb-7b24e01fa7e2," +
                "024f3ffe-cfe4-4cf6-ac96-87deeba59cf5," +
                "0280c96b-389a-44da-8902-c23cc9543ef6," +
                "02ec0a8d-6645-4a29-8a41-a284d5d9a73b," +
                "fff44bbd-7a33-4818-9438-8b7db660aa04,";

        String[] split1 = sr.split(",");
        List<String> list2 = new ArrayList<>();
        list2.addAll(Arrays.asList(split1));
        System.out.println(list2.size());

        Set<String> set = new HashSet<>();
        set.addAll(list);
        Set<String> collect = set.stream().filter(s -> !list2.contains(s)).collect(Collectors.toSet());
//        set.containsAll(list2);
        System.out.println(Arrays.toString(collect.toArray()));
//
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//        Set<String> set1 = new HashSet<>();
//        set1.addAll(list2);
//        set1.containsAll(list);
//        System.out.println(Arrays.toString(set1.toArray()));
    }

    @Test
    public void f3() {
        String fzIdStr = "21," +
                "101," +
                "140," +
                "150," +
                "162," +
                "163," +
                "164," +
                "165," +
                "166," +
                "167," +
                "168," +
                "169," +
                "170," +
                "171," +
                "172," +
                "173," +
                "174," +
                "175," +
                "176," +
                "190," +
                "191," +
                "199";
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

    @Test
    public void f4() {
        String ids = "" +
                "2," +
                "21," +
                "22," +
                "23," +
                "24," +
                "25," +
                "26," +
                "27," +
                "28," +
                "29," +
                "38," +
                "43," +
                "51," +
                "57," +
                "60," +
                "68," +
                "69," +
                "77," +
                "85," +
                "93," +
                "101," +
                "103," +
                "104," +
                "111," +
                "112," +
                "114," +
                "128," +
                "129," +
                "131," +
                "136," +
                "139," +
                "141," +
                "142," +
                "143," +
                "144," +
                "145," +
                "146," +
                "148," +
                "149," +
                "154," +
                "155," +
                "164," +
                "171," +
                "174," +
                "175," +
                "176," +
                "177," +
                "183," +
                "184," +
                "186," +
                "187," +
                "188," +
                "189," +
                "191," +
                "192," +
                "194," +
                "200," +
                "207," +
                "208," +
                "210," +
                "232," +
                "234," +
                "235," +
                "238," +
                "239," +
                "240," +
                "246," +
                "248," +
                "254," +
                "262," +
                "269," +
                "271," +
                "279," +
                "287," +
                "288," +
                "296," +
                "297," +
                "299," +
                "311," +
                "315," +
                "316," +
                "317," +
                "324," +
                "332," +
                "333," +
                "339," +
                "340," +
                "341," +
                "342," +
                "345," +
                "346," +
                "349," +
                "350," +
                "351," +
                "3," +
                "5," +
                "6," +
                "7,";
        String[] split = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String s : split) {
            list.add(Long.parseLong(s));
        }

        list.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        });

        System.out.println(list);
        System.out.println(list.size());
    }

    @Test
    public void f5() {
        String ids = "427," +
                "432," +
                "437," +
                "442," +
                "447," +
                "452," +
                "457," +
                "462," +
                "469," +
                "475," +
                "479," +
                "483," +
                "488," +
                "496," +
                "503," +
                "510," +
                "517," +
                "522," +
                "527";
        String[] split = ids.split(",");
        System.out.println(Arrays.toString(split));
    }
}
