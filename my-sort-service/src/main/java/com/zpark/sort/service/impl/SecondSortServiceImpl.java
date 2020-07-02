package com.zpark.sort.service.impl;

import com.zpark.sort.domain.SecondSort;
import com.zpark.sort.mapper.SecondSortMapper;
import com.zpark.sort.service.SecondSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SecondSortServiceImpl implements SecondSortService {

    @Resource
    private SecondSortMapper secondSortMapper;

    @Override
    public List<SecondSort> findSecondSortByTopSortId(Integer topSortId) {
        try {
            return secondSortMapper.findSecondSortByTopSortId(topSortId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<SecondSort> findAllSecondSort() {
        try {
            return secondSortMapper.findAllSecondSort();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
