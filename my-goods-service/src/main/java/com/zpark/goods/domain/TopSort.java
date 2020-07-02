package com.zpark.goods.domain;

public class TopSort {
    private Integer topSortId;

    private String topSortName;

    private Integer topSortNum;

    private String topSortRemark;

    public Integer getTopSortId() {
        return topSortId;
    }

    public void setTopSortId(Integer topSortId) {
        this.topSortId = topSortId;
    }

    public String getTopSortName() {
        return topSortName;
    }

    public void setTopSortName(String topSortName) {
        this.topSortName = topSortName == null ? null : topSortName.trim();
    }

    public Integer getTopSortNum() {
        return topSortNum;
    }

    public void setTopSortNum(Integer topSortNum) {
        this.topSortNum = topSortNum;
    }

    public String getTopSortRemark() {
        return topSortRemark;
    }

    public void setTopSortRemark(String topSortRemark) {
        this.topSortRemark = topSortRemark == null ? null : topSortRemark.trim();
    }
}