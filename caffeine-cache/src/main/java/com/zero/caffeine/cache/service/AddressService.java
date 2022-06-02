package com.zero.caffeine.cache.service;

import com.zero.caffeine.cache.dto.AddressDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AddressService {

    private static final AtomicLong ID_CREATOR = new AtomicLong(0);

    private Map<Long, AddressDTO> addressMap;

    public AddressService() {
        addressMap = new ConcurrentHashMap<>();

        addressMap.put(ID_CREATOR.incrementAndGet(), AddressDTO.builder().customerId(ID_CREATOR.get()).address("地址1").build());
        addressMap.put(ID_CREATOR.incrementAndGet(), AddressDTO.builder().customerId(ID_CREATOR.get()).address("地址2").build());
        addressMap.put(ID_CREATOR.incrementAndGet(), AddressDTO.builder().customerId(ID_CREATOR.get()).address("地址3").build());
    }

    @Cacheable(cacheNames = {"caffeine:address"}, key = "#customerId")
    public AddressDTO getAddress(long customerId) {
        return addressMap.get(customerId);
    }

    @CachePut(cacheNames = {"caffeine:address"}, key = "#result.customerId")
    public AddressDTO create(String address) {
        long customerId = ID_CREATOR.incrementAndGet();
        AddressDTO addressDTO = AddressDTO.builder().customerId(customerId).address(address).build();

        addressMap.put(customerId, addressDTO);
        return addressDTO;
    }

    @CachePut(cacheNames = {"caffeine:address"}, key = "#result.customerId")
    public AddressDTO update(Long customerId, String address) {

        AddressDTO addressDTO = addressMap.get(customerId);
        if (addressDTO == null) {
            throw new RuntimeException("没有 customerId = " + customerId + "的地址");
        }

        addressDTO.setAddress(address);
        return addressDTO;
    }

    @CacheEvict(cacheNames = {"caffeine:address"}, key = "#customerId")
    public boolean delete(long customerId) {
        AddressDTO remove = addressMap.remove(customerId);
        return remove != null;
    }
}
