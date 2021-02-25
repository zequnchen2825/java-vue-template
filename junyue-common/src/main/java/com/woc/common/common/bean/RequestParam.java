package com.woc.common.common.bean;

import lombok.Data;

@Data
public class RequestParam<T> {

    private String ip;

    private Integer page;

    private Integer size;

    private T data;

    private Long userId;
}
