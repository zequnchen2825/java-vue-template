package com.woc.common.vo;

import java.util.List;

public class MenuVO {

    private Integer menuId;

    private Integer specificationId;

    private Integer count = 1;

    private List<SelectVO> selects;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public List<SelectVO> getSelects() {
        return selects;
    }

    public void setSelects(List<SelectVO> selects) {
        this.selects = selects;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
