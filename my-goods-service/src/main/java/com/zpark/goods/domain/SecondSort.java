package com.zpark.goods.domain;

public class SecondSort {
    private Integer secondSortId;

    private String secondSortName;

    private Integer topSortId;

    private String secondSortRemark;

    public Integer getSecondSortId() {
        return secondSortId;
    }

    public void setSecondSortId(Integer secondSortId) {
        this.secondSortId = secondSortId;
    }

    public String getSecondSortName() {
        return secondSortName;
    }

    public void setSecondSortName(String secondSortName) {
        this.secondSortName = secondSortName == null ? null : secondSortName.trim();
    }

    public Integer getTopSortId() {
        return topSortId;
    }

    public void setTopSortId(Integer topSortId) {
        this.topSortId = topSortId;
    }

    public String getSecondSortRemark() {
        return secondSortRemark;
    }

    public void setSecondSortRemark(String secondSortRemark) {
        this.secondSortRemark = secondSortRemark == null ? null : secondSortRemark.trim();
    }
}