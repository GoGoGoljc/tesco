package com.zpark.sort.controller;

import com.zpark.sort.domain.SecondSort;
import com.zpark.sort.service.SecondSortService;
import com.zpark.sort.util.CommonUtil;
import com.zpark.sort.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/secondSort")
public class SecondSortConstroller {

    @Autowired
    private SecondSortService secondSortService;

    /**
     * 通过一级分类查询二级分类
     */

    @GetMapping("/findSecondSortByTopSortId")
    public ResultObject findSecondSortByTopSortId(@RequestParam("top_sort_id") Integer topSortId){
        try {
            if(StringUtils.isEmpty(topSortId)){
                return new ResultObject(400,"发生了未知错误",null);
            }
            List<SecondSort> secondSortList=secondSortService.findSecondSortByTopSortId(topSortId);
            ResultObject resultObject = CommonUtil.listResultTip(secondSortList);
            return  resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }

    }

    /**
     * 查询所有二级分类
     *
     */

    @GetMapping("/findAllSecondSort")
    public ResultObject findAllSecondSort(){
        try {
            List<SecondSort> secondSortList=secondSortService.findAllSecondSort();
            ResultObject resultObject = CommonUtil.listResultTip(secondSortList);
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }


}
