package com.zpark.sort.controller;

import com.zpark.sort.domain.SecondSort;
import com.zpark.sort.domain.TopSort;
import com.zpark.sort.service.TopSortService;
import com.zpark.sort.util.CommonUtil;
import com.zpark.sort.util.ResultObject;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topSort")
public class TopSortController {

    @Autowired
    private TopSortService topSortService;

    /**
     * 查询所有一级分类
     */
    @GetMapping("/findAllTopSort")
    public ResultObject findAllTopSort(){
        ResultObject result = new ResultObject();
        try {
            List<TopSort> topSortList=topSortService.findAllTopSort();
            if(topSortList!=null && topSortList.size()>0){
                result.setCode(200);
                result.setMessage("查询成功");
                result.setResultData(topSortList);
                return result;
            }
            result.setCode(400);
            result.setMessage("没有查询结果");
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
