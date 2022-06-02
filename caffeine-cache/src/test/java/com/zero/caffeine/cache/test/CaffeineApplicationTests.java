package com.zero.caffeine.cache.test;

import com.zero.caffeine.cache.CaffeineApplication;
import com.zero.caffeine.cache.dto.AddressDTO;
import com.zero.caffeine.cache.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaffeineApplication.class)
@Slf4j
public class CaffeineApplicationTests {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testCache() {
        AddressDTO newInsert = addressService.create("南山大道");
        // 要走缓存
        AddressDTO address = addressService.getAddress(newInsert.getCustomerId());

        // 第一次未命中缓存，打印 customerId：{} 没有走缓存，开始从数据库查询
        AddressDTO address2 = addressService.getAddress(2);
        // 命中缓存
        AddressDTO cacheAddress2 = addressService.getAddress(2);

        addressService.update(2L, "地址 2 被修改");

        // 更新后查询，依然命中缓存
        AddressDTO hitCache2 = addressService.getAddress(2);
        Assert.assertEquals(hitCache2.getAddress(), "地址 2 被修改");

        // 删除缓存
        addressService.delete(2);

        // 未命中缓存, 从数据库读取
        AddressDTO hit = addressService.getAddress(2);
        System.out.println(hit.getCustomerId());
    }

}
