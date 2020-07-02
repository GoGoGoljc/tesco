package com.zpark.sort.service;

import com.zpark.sort.domain.SecondSort;
import com.zpark.sort.domain.TopSort;

import java.util.List;

public interface TopSortService {

    List<TopSort> findAllTopSort();

}
