package com.zpark.address.mapper;

import com.zpark.address.domain.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AddressMapper {
     Integer addAddress(Address address);

     List<Address> findAddressByUserId(Integer userId);

     Integer updateAddressById(Address address);

     Integer deleteAddressById(Integer addressId);

     Address findAddressById(Integer addressId);
}
