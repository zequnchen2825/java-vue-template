package com.woc.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception {

        JSONArray resultArr = new JSONArray();

        String resp = HttpUtils.doGet("https://translate.google.cn/#view=home&op=translate&sl=auto&tl=en&text=%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4%E6%B0%B4wdasdasdasd");
        Document document = Jsoup.parse(resp);
        Elements elementsByClass1 = document.body().getElementsByClass("result tlid-copy-target");
        Elements elementsByAttributeValue = document.body().getElementsByAttributeValue("id", "DailyTarot");

        for(int i = 0 ; i < elementsByAttributeValue.size()-2 ; i++){
            JSONObject type = new JSONObject();
            resp = HttpUtils.doGet(elementsByAttributeValue.get(i).attr("href"));
            type.put("type",elementsByAttributeValue.get(i).text());
            Document subDocument = Jsoup.parse(resp);
            Elements from = subDocument.getElementsByAttributeValue("name", "TG_DeckForm");
            String action = from.get(0).attr("action");
            Elements onclick = subDocument.body().getElementsByAttributeValueContaining("onclick", "javascript:TarotDeckSubmitTarotCard");
            JSONArray cardArr = new JSONArray();
            for(Element card :onclick){
                String onclickStr = card.attr("onclick");
                String cardNum = onclickStr.substring(onclickStr.indexOf("(") + 1, onclickStr.indexOf(")"));
                Map<String,Object> param = new HashMap<>();
                param.put("CardNumber_1_numericalint",cardNum);
                String result = HttpUtils.doPostKV(action, param);
                Document resultDocument = Jsoup.parse(result);
                Elements elementsByClass = resultDocument.body().getElementsByClass("content text-left");
                Element resultElement = elementsByClass.get(0);
                Elements childrens = resultElement.children();
                JSONObject content = new JSONObject();
                content.put("title",childrens.get(1).text());
                content.put("content",childrens.get(2).text());
                cardArr.add(content);
            }
            type.put("card",cardArr);
            resultArr.add(type);
        }

        System.out.println(resultArr);
    }
}
