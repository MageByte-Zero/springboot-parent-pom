package com.zero.caffeine.cache.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class AddressDTO implements Serializable {
    private Long customerId;

    private String address;
}
