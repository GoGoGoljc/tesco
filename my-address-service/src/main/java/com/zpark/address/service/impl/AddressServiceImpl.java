package com.zpark.address.service.impl;

import com.zpark.address.domain.Address;

import com.zpark.address.mapper.AddressMapper;
import com.zpark.address.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;

    /**
     * 添加地址信息
     * @param address
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Integer addAddress(Address address) {
        try {
            Integer i=addressMapper.addAddress(address);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 根据用户的id查询自己的地址表信息
     */
    @Override
    public List<Address> findAddressByUserId(Integer userId) {
        try {
            List<Address> addressList=addressMapper.findAddressByUserId(userId);
            return addressList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 根据地址id修改地址信息
     * @param address
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Integer updateAddressById(Address address) {
        try {
            return addressMapper.updateAddressById(address);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 根据地址id删除地址信息
     * @param addressId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Integer deleteAddressById(Integer addressId) {
        try {
            return addressMapper.deleteAddressById(addressId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }


    /**
     * 根据主键idc查询地址信息
     * @param addressId
     * @return
     */
    @Override
    public Address findAddressById(Integer addressId) {
        try {
            return   addressMapper.findAddressById(addressId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
