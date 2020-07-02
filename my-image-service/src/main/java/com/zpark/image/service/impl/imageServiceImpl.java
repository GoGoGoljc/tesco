package com.zpark.image.service.impl;

import com.zpark.image.domain.Image;
import com.zpark.image.mapper.ImageMapper;
import com.zpark.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class imageServiceImpl implements ImageService {

    @Resource
    private ImageMapper imageMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Integer addImage(Image image) {
        try {
            return imageMapper.addImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public Integer deleteByGoodsId(Integer goodsId) {
        try {
            return imageMapper.deleteByGoodsId(goodsId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
