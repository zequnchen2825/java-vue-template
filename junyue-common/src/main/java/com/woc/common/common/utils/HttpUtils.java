package com.woc.common.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.Contants;
import com.woc.common.common.RespContants;
import com.woc.common.common.exception.BusinessException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 *
 * @author zhangyong
 */

public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

//    /**
//     * 执行一个HTTP GET请求，返回请求响应的HTML
//     *
//     * @param url 请求的URL地址
//     * @return 返回请求响应的HTML
//     */
//    public static String doGet(String url) throws IOException {
//        HttpClient client = new HttpClient();
//        HttpMethod method = new GetMethod(url);
//
//        try {
//            client.executeMethod(method);
//        } catch (URIException e) {
//            logger.error("执行HTTP Get请求时，发生异常！", e);
//
//        } catch (IOException e) {
//            logger.error("执行HTTP Get请求" + url + "时，发生异常！", e);
//        } finally {
//            method.releaseConnection();
//        }
//        logger.info("执行HTTP GET请求，返回码: {}", method.getStatusCode());
//        if (method.getStatusCode() == HttpStatus.SC_OK) {
//            return StreamUtils.copyToString(method.getResponseBodyAsStream(), Charset.forName("utf-8"));
//        }
//        return url;
//    }

    /**
     * 执行一个带参数的HTTP GET请求，返回请求响应的JSON字符串
     *
     * @param url 请求的URL地址
     * @return 返回请求响应的JSON字符串
     */
    public static String doGet(String url) {
        // 构造HttpClient的实例
        HttpClient client = new HttpClient();
        //设置参数
        // 创建GET方法的实例
        GetMethod method = new GetMethod(url );
        // 使用系统提供的默认的恢复策略
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        try {
            // 执行getMethod
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                return StreamUtils.copyToString(method.getResponseBodyAsStream(), Charset.forName("utf-8"));
            }
        } catch (IOException e) {
            logger.error("执行HTTP Get请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url         请求的URL地址
     * @param queryString 请求的查询参数,可以为null
     * @param charset     字符集
     * @param pretty      是否美化
     * @return 返回请求响应的HTML
     */
    public static String doGet(String url, String queryString, String charset, boolean pretty) {
        //logger.info("http的请求地址为:"+url);
        logger.info("http的请求参数为：" + queryString);

        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);

        try {
            if (StringUtils.isNotBlank(queryString)) {
                method.setQueryString(URIUtil.encodeQuery(queryString));
            }

            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();

            // 设置连接的超时时间
            managerParams.setConnectionTimeout(10* 1000);

            // 设置读取数据的超时时间
            managerParams.setSoTimeout(10 * 1000);

            client.executeMethod(method);
            logger.info("http的请求地址为:" + url + ",返回的状态码为" + method.getStatusCode());

            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
            String line;
            while ((line = reader.readLine()) != null) {
                if (pretty) {
                    response.append(line).append(System.getProperty("line.separator"));
                } else {
                    response.append(line);
                }
            }

            reader.close();

        } catch (Exception e) {
            logger.error("执行HTTP Get请求" + url + "时，发生异常！" + e);
            return response.toString();
        } finally {
            method.releaseConnection();
        }
        return response.toString();

    }

    /**
     * 执行一个带参数的HTTP POST请求，返回请求响应的JSON字符串
     *
     * @param url 请求的URL地址
     * @param map 请求的map参数
     * @return 返回请求响应的JSON字符串
     */
    public static String doPostKV(String url, Map<String, Object> map,Map<String, Object> headmap) throws Exception {
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        // 创建POST方法的实例
        PostMethod method = new PostMethod(url);

        // 这个basicNameValue**放在List中
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        // 创建basicNameValue***对象参数
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(entry != null || entry.getValue() != null){

                    nameValuePairs.add(new NameValuePair(entry.getKey(), entry.getValue().toString()));
                }
            }
        }
        HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();

        // 设置连接的超时时间
        managerParams.setConnectionTimeout(1000*10);

        // 设置读取数据的超时时间
        managerParams.setSoTimeout(10 * 1000);

        // 填入各个表单域的值
        NameValuePair[] param = nameValuePairs.toArray(new NameValuePair[nameValuePairs.size()]);

        if(headmap == null){
            headmap = new HashMap<>();
        }
        headmap.put("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        for(String key : headmap.keySet()){
            method.setRequestHeader(key,headmap.get(key).toString());
        }
        method.addParameters(param);
        // 将表单的值放入postMethod中
        try {
            // 执行postMethod
            int statusCode = httpClient.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                return StreamUtils.copyToString(method.getResponseBodyAsStream(), Charset.forName("utf-8"));
            }
        } catch (UnsupportedEncodingException e1) {
            logger.error(e1.getMessage());
        } catch (ConnectException e) {
            throw new BusinessException(RespContants.SERVICE_ERROR);
        }catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！" + e.toString());
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    /**
     * K-V post 返回JSONObject
     * @param url
     * @param map
     * @param headmap
     * @return JSONObject
     */
    public static JSONObject doPostToJson(String url, Map<String, Object> map,Map<String, Object> headmap) throws Exception{
        return JSONObject.parseObject(doPostKV(url, map, headmap));
    }


    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url     请求的URL地址
     * @param reqStr  请求的查询参数,可以为null
     * @param charset 字符集
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, String reqStr, String contentType, String charset) {
        HttpClient client = new HttpClient();

        PostMethod method = new PostMethod(url);
        try {
            HttpConnectionManagerParams managerParams = client
                    .getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000); // 设置连接超时时间(单位毫秒)
            managerParams.setSoTimeout(30000); // 设置读数据超时时间(单位毫秒)

            method.setRequestEntity(new StringRequestEntity(reqStr, contentType, "utf-8"));

            client.executeMethod(method);
            logger.info("返回的状态码为" + method.getStatusCode());
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                return StreamUtils.copyToString(method.getResponseBodyAsStream(), Charset.forName(charset));
            }
        } catch (UnsupportedEncodingException e1) {
            logger.error(e1.getMessage());
            return "";
        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！" + e.toString());

            return "";
        } finally {
            method.releaseConnection();
        }

        return null;
    }

    /**
     * 执行一个带参数的HTTP POST请求，返回请求响应的JSON字符串
     *
     * @param url 请求的URL地址
     * @param map 请求的map参数
     * @return 返回请求响应的JSON字符串
     */
    public static String doPostKV(String url, Map<String, Object> map) throws Exception{
        return doPostKV(url,map,null);

    }

    /**
     * json post请求 返回JSONObject
     * @param url
     * @param map
     * @return JSONObject
     */
    public static JSONObject doPostKVtoJson(String url, Map<String, Object> map ,Map<String, Object> headmap) throws Exception{

        return JSONObject.parseObject(doPostKV(url,map,headmap));

    }
    /**
     * json post请求 返回JSONObject
     * @param url
     * @param map
     * @return JSONObject
     */
    public static JSONObject doPostKVtoJson(String url, Map<String, Object> map) throws Exception{

        return doPostKVtoJson(url,map,null);

    }
    /**
     * json post请求 返回T
     * @param url
     * @param map
     * @return T
     */
    public static <T>T doPostKVtoEntity(String url, Map<String, Object> map,Map<String, Object> headmap,Class<T> tClass) throws Exception{
        String result = doPostKV(url, map, headmap);
        return JSONObject.parseObject(result,tClass);

    }
    /**
     * json post请求 返回T
     * @param url
     * @param map
     * @return T
     */
    public static <T>T doPostKVtoEntity(String url, Map<String, Object> map,Class<T> tClass) throws Exception{
        return doPostKVtoEntity(url,map,null,tClass);

    }
    /**
     * 执行一个带参数的HTTP POST请求，返回请求响应的JSON字符串
     *
     * @param url 请求的URL地址
     * @param headMap 请求的头参数
     * @return 返回请求响应的JSON字符串
     */
    public static String doPostXML(String url,  String paramXml,String mchId){
        CloseableHttpClient client = null;
        try {
            // 证书密码，默认为商户ID
            String key = mchId;

            // 指定读取证书格式为PKCS12
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            // 读取本机存放的PKCS12证书文件
            URL certUrl = Thread.currentThread().getContextClassLoader().getResource("apiclient_cert.p12");

            FileInputStream inputStream = new FileInputStream(new File(certUrl.getPath()));

            try {
                // 指定PKCS12的密码(商户ID)
                keyStore.load(inputStream, key.toCharArray());
            } finally {
                inputStream.close();
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, key.toCharArray()).build();
            SSLConnectionSocketFactory sslsf =
                    new SSLConnectionSocketFactory(sslcontext, new String[] {"TLSv1"}, null,
                            SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            client = HttpClients.custom().setSSLSocketFactory(sslsf).build();



            HttpPost postMethod = new HttpPost(url);

            postMethod.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            StringEntity postEntity = new StringEntity(paramXml, "UTF-8");
            postMethod.setEntity(postEntity);
            postMethod.addHeader("Content-Type", "text/xml");

            //默认的重试策略
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);//设置超时时间
            HttpResponse response = client.execute(postMethod);
            return EntityUtils.toString(response.getEntity(),"UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 执行一个带参数的HTTP POST请求，返回请求响应的JSON字符串
     *
     * @param url 请求的URL地址
     * @param headMap 请求的头参数
     * @return 返回请求响应的JSON字符串
     */
    public static String doPostJSON(String url,  JSONObject paramJson,Map<String, Object> headMap){

        try {
            HttpClient client = new HttpClient();

            PostMethod postMethod = new PostMethod(url);

            RequestEntity re = new StringRequestEntity(paramJson.toJSONString(),"application/json" ,"UTF-8");

            postMethod.setRequestEntity(re);

            if(headMap == null){
                headMap = new HashMap<>();
            }
            headMap.put("Content-Type","application/json");
            //默认的重试策略
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);//设置超时时间
            int httpStatus = client.executeMethod(postMethod);
            return postMethod.getResponseBodyAsString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行一个带参数的HTTP POST请求，返回请求响应的JSON字符串
     *
     * @param url 请求的URL地址
     * @return 返回请求响应的JSON字符串
     */
    public static String doPostJSON(String url,  JSONObject paramJson){

        return doPostJSON(url,paramJson,null);
    }

    /**
     * json Post请求 JSONObject
     * @param url
     * @param paramJson
     * @return
     */
    public static JSONObject doPostJSONtoJson(String url,  JSONObject paramJson){

        return JSONObject.parseObject(doPostJSON(url,paramJson,null));
    }


    /**
     * json Post请求  T
     * @param url
     * @param paramJson
     * @param headMap
     * @param tclass
     * @param <T>
     * @return
     */
    public static <T> T doPostJSONtoEntity(String url,  JSONObject paramJson,Map<String, Object> headMap,Class<T> tclass){

        String result = doPostJSON(url, paramJson, headMap);

        return JSONObject.parseObject(result,tclass);

    }
    /**
     * json Post请求  T
     * @param url
     * @param paramJson
     * @param tclass
     * @param <T>
     * @return
     */
    public static <T> T doPostJSONtoEntity(String url,  JSONObject paramJson,Class<T> tclass){

        String result = doPostJSON(url, paramJson);

        return JSONObject.parseObject(result,tclass);

    }

    public static void main(String[] args) throws Exception {
//        Map<String,Object> headMap = new HashMap<>();
//        headMap.put("Cookie","JSESSIONID=B08D3B89685FD787053881378BE00060");
//        String s = doPostKV("http://jxgl.wyu.edu.cn/xsgrkbcx!xsAllKbList.action?xnxqdm=201701", null, headMap);
////        WyuUserVO wyuUserVO = HtmlUtils.wyuUserInfo(s);
//        Document document = Jsoup.parse(s);
//        Element script = document.getElementsByTag("script").last();
//        String text = script.html();
//        String matcher = StringUtil.getMatcher(Contants.WYU_REGEX_SCHEDULE, text);
//
//
//        System.out.println(matcher);


    }

}

