package com.zero.caffeine.cache.service;

import com.zero.caffeine.cache.dto.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class AddressService {

    public static final String CACHE_NAME = "caffeine:address";

    private static final AtomicLong ID_CREATOR = new AtomicLong(0);

    private Map<Long, AddressDTO> addressMap;

    public AddressService() {
        addressMap = new ConcurrentHashMap<>();

        addressMap.put(ID_CREATOR.incrementAndGet(), AddressDTO.builder().customerId(ID_CREATOR.get()).address("地址1").build());
        addressMap.put(ID_CREATOR.incrementAndGet(), AddressDTO.builder().customerId(ID_CREATOR.get()).address("地址2").build());
        addressMap.put(ID_CREATOR.incrementAndGet(), AddressDTO.builder().customerId(ID_CREATOR.get()).address("地址3").build());
    }

    @Cacheable(cacheNames = {CACHE_NAME}, key = "#customerId")
    public AddressDTO getAddress(long customerId) {
        log.info("customerId：{} 没有走缓存，开始从数据库查询", customerId);
        return addressMap.get(customerId);
    }

    @CachePut(cacheNames = {CACHE_NAME}, key = "#result.customerId")
    public AddressDTO create(String address) {
        long customerId = ID_CREATOR.incrementAndGet();
        AddressDTO addressDTO = AddressDTO.builder().customerId(customerId).address(address).build();

        addressMap.put(customerId, addressDTO);
        return addressDTO;
    }

    @CachePut(cacheNames = {CACHE_NAME}, key = "#result.customerId")
    public AddressDTO update(Long customerId, String address) {

        AddressDTO addressDTO = addressMap.get(customerId);
        if (addressDTO == null) {
            throw new RuntimeException("没有 customerId = " + customerId + "的地址");
        }

        addressDTO.setAddress(address);
        return addressDTO;
    }

    @CacheEvict(cacheNames = {CACHE_NAME}, key = "#customerId")
    public boolean delete(long customerId) {
        log.info("缓存 {} 被删除", customerId);
        return true;
    }
}
