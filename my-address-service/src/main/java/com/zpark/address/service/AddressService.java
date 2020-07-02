package com.zpark.address.service;

import com.zpark.address.domain.Address;

import java.util.List;

public interface AddressService {

    Integer addAddress(Address address);

    List<Address> findAddressByUserId(Integer userId);

    Integer updateAddressById(Address address);

    Integer deleteAddressById(Integer addressId);

    Address findAddressById(Integer addressId);
}
