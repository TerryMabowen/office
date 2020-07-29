package com.mbw.office.test.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.mbw.office.common.util.json.FastJsonUtil;
import com.mbw.office.demo.fastjson.domain.Almanac;
import com.mbw.office.demo.fastjson.domain.Holiday;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-24 19:33
 */
public class FastJsonTest {
    private static String json = "{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"StdStg\":6018,\"StdStl\":8,\"_update_time\":\"1580102250\",\"cambrian_appid\":\"0\",\"almanac\":[{\"avoid\":\"开市.安葬.\",\"date\":\"2019-7-1\",\"suit\":\"订盟.纳采.出行.祭祀.祈福.修造.动土.移徙.入宅.\"},{\"avoid\":\"诸事不宜.\",\"date\":\"2019-7-2\",\"suit\":\"诸事不宜.\"},{\"avoid\":\"开光.开市.\",\"date\":\"2019-7-3\",\"suit\":\"嫁娶.订盟.纳采.祭祀.祈福.入殓.破土.安葬.\"},{\"avoid\":\"嫁娶.开光.\",\"date\":\"2019-7-4\",\"suit\":\"开光.求医.治病.动土.上梁.入殓.破土.安葬.\"},{\"avoid\":\"诸事不宜.\",\"date\":\"2019-7-5\",\"suit\":\"祭祀.栽种.余事勿取.\"},{\"avoid\":\"安葬.开市.交易.立券.\",\"date\":\"2019-7-6\",\"suit\":\"嫁娶.开光.祭祀.祈福.求嗣.出行.解除.伐木.入宅.移徙.安床.出火.拆卸.修造.上梁.栽种.移柩.\"},{\"avoid\":\"出行.安葬.造桥.\",\"date\":\"2019-7-7\",\"suit\":\"纳采.订盟.嫁娶.祭祀.沐浴.塑绘.开光.出火.治病.习艺.伐木.造屋.竖柱.上梁.安床.作灶.安碓硙.挂匾.掘井.纳畜.\"},{\"avoid\":\"开市.入宅.嫁娶.开光.造屋.\",\"date\":\"2019-7-8\",\"suit\":\"祭祀.入殓.除服.成服.移柩.破土.启攒.安葬.塞穴.断蚁.结网.\"},{\"avoid\":\"动土.破土.掘井.安葬.\",\"date\":\"2019-7-9\",\"suit\":\"祭祀.修造.出行.造屋.竖柱.造车器.教牛马.造畜椆栖.割蜜.\"},{\"avoid\":\"嫁娶.出行.纳采.入宅.作灶.\",\"date\":\"2019-7-10\",\"suit\":\"祭祀.沐浴.塑绘.开光.入学.解除.扫舍.治病.开池.牧养.\"},{\"avoid\":\"斋醮.入宅.安门.安葬.破土.行丧.\",\"date\":\"2019-7-11\",\"suit\":\"纳财.开市.交易.立券.出行.祭祀.祈福.求嗣.开光.解除.扫舍.起基.竖柱.安床.移徙.开仓.出货财.补垣.塞穴.栽种.纳畜.牧养.\"},{\"avoid\":\"开市.动土.破土.嫁娶.修造.安葬.\",\"date\":\"2019-7-12\",\"suit\":\"祭祀.修饰垣墙.平治道涂.\"},{\"avoid\":\"嫁娶.安葬.行丧.破土.修坟.\",\"date\":\"2019-7-13\",\"suit\":\"订盟.纳采.祭祀.祈福.开光.安香.出火.立券.安机械.移徙.入宅.竖柱.上梁.会亲友.安床.拆卸.挂匾.牧养.教牛马.\"},{\"avoid\":\"出火.嫁娶.入宅.作灶.破土.上梁.动土.\",\"date\":\"2019-7-14\",\"suit\":\"沐浴.理发.捕捉.入殓.移柩.破土.启攒.安葬.\"},{\"avoid\":\"嫁娶.出行.\",\"date\":\"2019-7-15\",\"suit\":\"求医.治病.破屋.坏垣.余事勿取.\"},{\"avoid\":\"祈福.上梁.开仓.掘井.牧养.\",\"date\":\"2019-7-16\",\"suit\":\"纳采.订盟.嫁娶.移徙.入宅.出行.开市.交易.立券.纳财.会亲友.安香.出火.拆卸.造屋.起基.安床.作灶.挂匾.安葬.破土.启攒.立碑.入殓.移柩.\"},{\"avoid\":\"嫁娶.开市.栽种.合寿木.\",\"date\":\"2019-7-17\",\"suit\":\"祭祀.祈福.斋醮.出行.纳采.订盟.安机械.出火.拆卸.修造.动土.起基.移徙.入宅.造庙.入殓.除服.成服.移柩.破土.安葬.谢土.\"},{\"avoid\":\"开市.入宅.安床.动土.安葬.\",\"date\":\"2019-7-18\",\"suit\":\"祭祀.进人口.纳财.纳畜.牧养.捕捉.余事勿取.\"},{\"avoid\":\"造屋.入宅.作灶.入学.安葬.行丧.\",\"date\":\"2019-7-19\",\"suit\":\"祭祀.塑绘.开光.求医.治病.嫁娶.会亲友.放水.掘井.牧养.纳畜.开渠.安碓硙.\"},{\"avoid\":\"移徙.开市.入宅.嫁娶.开光.安门.\",\"date\":\"2019-7-20\",\"suit\":\"祭祀.塞穴.结网.畋猎.余事勿取.\"},{\"avoid\":\"动土.破土.安葬.治病.\",\"date\":\"2019-7-21\",\"suit\":\"开市.纳财.祭祀.塑绘.安机械.冠笄.会亲友.裁衣.开仓.经络.纳畜.造畜椆栖.教牛马.牧养.\"},{\"avoid\":\"开市.斋醮.安床.出行.经络.\",\"date\":\"2019-7-22\",\"suit\":\"移徙.入宅.治病.会亲友.祭祀.祈福.斋醮.安香.移徙.嫁娶.造屋.起基.\"},{\"avoid\":\"\",\"date\":\"2019-7-23\",\"suit\":\"塑绘.出行.冠笄.嫁娶.进人口.裁衣.纳婿.造畜椆栖.交易.立券.牧养.开生坟.入殓.除服.成服.移柩.安葬.启攒.\"},{\"avoid\":\"余事勿取.\",\"date\":\"2019-7-24\",\"suit\":\"祭祀.冠笄.嫁娶.捕捉.结网.畋猎.取渔.余事勿取.\"},{\"avoid\":\"诸事不宜.\",\"date\":\"2019-7-25\",\"suit\":\"沐浴.扫舍.余事勿取.\"},{\"avoid\":\"嫁娶.移徙.入宅.\",\"date\":\"2019-7-26\",\"suit\":\"纳采.祭祀.祈福.解除.动土.破土.安葬.\"},{\"avoid\":\"诸事不宜.\",\"date\":\"2019-7-27\",\"suit\":\"祭祀.破屋.坏垣.余事勿取.\"},{\"avoid\":\"祭祀.祈福.\",\"date\":\"2019-7-28\",\"suit\":\"嫁娶.纳采.开市.出行.动土.上梁.移徙.入宅.破土.安葬.\"},{\"avoid\":\"赴任.\",\"date\":\"2019-7-29\",\"suit\":\"嫁娶.纳采.开市.出行.动土.上梁.移徙.入宅.破土.安葬.\"},{\"avoid\":\"开市.破土.\",\"date\":\"2019-7-30\",\"suit\":\"祭祀.作灶.纳财.捕捉.\"},{\"avoid\":\"造庙.安葬.\",\"date\":\"2019-7-31\",\"suit\":\"嫁娶.开市.立券.祭祀.祈福.动土.移徙.入宅.\"}],\"holiday\":{\"desc\":\"6月7日放假，与周末连休。\",\"festival\":\"2019-6-7\",\"list\":[{\"date\":\"2019-6-7\",\"status\":\"1\"},{\"date\":\"2019-6-8\",\"status\":\"1\"},{\"date\":\"2019-6-9\",\"status\":\"1\"}],\"list#num#baidu\":3,\"name\":\"端午节\",\"rest\":\"拼假建议：2019年6月3日（周一）~2019年6月6日（周四）请假4天，可拼9天端午节小长假。\"},\"holidaylist\":[{\"name\":\"元旦\",\"startday\":\"2019-1-1\"},{\"name\":\"除夕\",\"startday\":\"2019-2-4\"},{\"name\":\"春节\",\"startday\":\"2019-2-4\"},{\"name\":\"清明节\",\"startday\":\"2019-4-5\"},{\"name\":\"劳动节\",\"startday\":\"2019-5-1\"},{\"name\":\"端午节\",\"startday\":\"2019-6-7\"},{\"name\":\"中秋节\",\"startday\":\"2019-9-13\"},{\"name\":\"国庆节\",\"startday\":\"2019-10-1\"}],\"key\":\"2019年7月\",\"selectday\":\"2019-7-1\",\"url\":\"http:\\/\\/nourl.baidu.com\\/6018\",\"loc\":\"http:\\/\\/open.baidu.com\\/q?r=2002753&k=2019%E5%B9%B47%E6%9C%88\",\"SiteId\":2002753,\"_version\":2767,\"_select_time\":1580102079,\"clicklimit\":\"1-3\",\"ExtendedLocation\":\"\",\"OriginQuery\":\"2019年07月\",\"tplt\":\"calendar_new\",\"resourceid\":\"6018\",\"fetchkey\":\"6018_2019年7月\",\"role_id\":10,\"disp_type\":0,\"appinfo\":\"\"}]}\n";

    @Test
    public void f1() {
        JSONObject jsonObject = FastJsonUtil.getJsonObject(json);
        String data = FastJsonUtil.getJsonByKey(jsonObject, "data");
        System.out.println(data);
    }

    @Test
    public void f2() {
        JSONObject jsonObject = FastJsonUtil.getJsonObject(json);
        String data = FastJsonUtil.getJsonByKey(jsonObject, "data");
        Map<String, Object> jsonToMap = FastJsonUtil.jsonToMap(data, Object.class);
        List<Almanac> almanacs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : jsonToMap.entrySet()) {
            if ("almanac".equals(entry.getKey()) && entry.getValue() != null) {
                String value = String.valueOf(entry.getValue());
                if (value.startsWith("[")) {
                    almanacs = FastJsonUtil.jsonToList(value, Almanac.class);
                } else if (value.startsWith("{")) {
                    almanacs.add(FastJsonUtil.jsonToBean(value, Almanac.class));
                }
            }
        }

        System.out.println(almanacs);
    }

    @Test
    public void f3() {
        JSONObject jsonObject = FastJsonUtil.getJsonObject(json);
        String data = FastJsonUtil.getJsonByKey(jsonObject, "data");
        Map<String, Object> jsonToMap = FastJsonUtil.jsonToMap(data, Object.class);
        List<Holiday> holidays = new ArrayList<>();
        for (Map.Entry<String, Object> entry : jsonToMap.entrySet()) {
            if ("holiday".equals(entry.getKey()) && entry.getValue() != null) {
                String value = String.valueOf(entry.getValue());
                if (value.startsWith("[")) {
                    holidays = FastJsonUtil.jsonToList(value, Holiday.class);
                } else if (value.startsWith("{")) {
                    holidays.add(FastJsonUtil.jsonToBean(value, Holiday.class));
                }
            }
        }

        System.out.println(holidays);
    }
}
