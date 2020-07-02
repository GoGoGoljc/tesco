package com.zpark.sort.service.impl;

import com.zpark.sort.domain.SecondSort;
import com.zpark.sort.domain.TopSort;
import com.zpark.sort.mapper.TopSortMapper;
import com.zpark.sort.service.TopSortService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TopSortServiceImpl implements TopSortService {

    @Resource
    private TopSortMapper topSortMapper;

    @Override
    public List<TopSort> findAllTopSort() {
        try {
            List<TopSort> topSortList=topSortMapper.findAll();
            return topSortList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
