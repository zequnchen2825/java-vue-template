package com.woc.common.common.api.order.feieyun;

import java.util.Map;

public interface BaseRequest<T extends BaseResponse> {

    /**
     * 获取TOP的API名称。
     *
     * @return API名称
     */
    public String getApiMethodName();

    /**
     * 获取TOP的API名称。
     *
     * @return API名称
     */
    public String getApiDisplayName();

    /**
     * 描述：获取请求方式
     * @return
     */
    public String getHttpMethod();

    /**
     * 描述：获取请求参数
     * @return
     */
    public Map<String, Object> getTextParams() throws Exception;

    /**
     * 得到当前API的响应结果类型
     *
     * @return 响应类型
     */
    public Class<T> getResponseClass();

    /**
     * 描述：是否需要签名
     * @return
     */
    public boolean isNeedSign();
}
