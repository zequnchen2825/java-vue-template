package com.woc.common.entity.extend;

public class PageParams {

    private Integer page = 1;

    private Integer pageSize = 10;

    private Integer skip;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getSkip() {
        return (page-1)*pageSize;
    }

}
