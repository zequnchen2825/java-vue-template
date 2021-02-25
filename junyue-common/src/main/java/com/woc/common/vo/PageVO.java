package com.woc.common.vo;






import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Objects;

public class PageVO<T> {

    private List<T> rows;

    private Long total;

    public PageVO(List<T> rows ,long total ) {
        this.rows = rows;
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
