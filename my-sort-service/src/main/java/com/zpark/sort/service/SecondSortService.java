package com.zpark.sort.service;

import com.zpark.sort.domain.SecondSort;

import java.util.List;

public interface SecondSortService {

    List<SecondSort> findSecondSortByTopSortId(Integer topSortId);

    List<SecondSort> findAllSecondSort();
}
