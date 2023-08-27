package zero.springboot.study.redission.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.GeoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zero.springboot.study.redission.RedissionApplication;
import zero.springboot.study.redission.service.GeoService;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedissionApplication.class)
public class GeoServiceTest {

    @Autowired
    private GeoService geoService;

    private static final String KEY = "girls:location";

    @Test
    public void testAddGeo() {
        geoService.add(KEY, "苍无井空", 13.361389D, 38.115556D);
        geoService.add(KEY, "波节野衣", 15.087269D, 37.502669D);
        geoService.add(KEY, "码哥", 15.087269D, 37.502669D);

    }

    @Test
    public void testSearchFromMember() {
        List<String> list = geoService.searchFromMember(KEY, "码哥", 100, 20, GeoUnit.KILOMETERS);
        log.info(JSON.toJSONString(list));
    }


    @Test
    public void testSearchFromLonLat() {
        List<String> list = geoService.searchFromLonLat(KEY, 15.087269D, 37.502669D, 100, 20, GeoUnit.KILOMETERS);
        log.info(JSON.toJSONString(list));
    }

    @Test
    public void testDelete() {
        boolean flag = geoService.delete(KEY, "码哥");
        Assert.assertTrue(flag);
    }
}
