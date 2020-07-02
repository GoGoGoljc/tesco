package com.zpark.browse.service.impl;

import com.zpark.browse.domian.Browse;
import com.zpark.browse.mapper.BrowseMapper;
import com.zpark.browse.service.BrowseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BrowseServiceImpl implements BrowseService {

    @Resource
    private BrowseMapper browseMapper;

    /**
     * 添加用户浏览商品的记录
     * @param browse
     * @return
     */

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public Integer addBrowseInfo(Browse browse) {
        try {
            return browseMapper.addBrowseInfo(browse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 根据用户id 查询用户的浏览记录
     * @param userId
     * @return
     */
    @Override
    public List<Browse> findBrowsebyUserId(Integer userId) {
        try {
            return browseMapper.findBrowsebyUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
