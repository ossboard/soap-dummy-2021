package com.github.soap.utils;

import org.json.JSONObject;
import org.json.XML;

import java.util.Map;

public class DarcXmlUtil {

    public static Map<String, Object> xml2Map(String xml) throws Exception {
        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        Map<String, Object> result = JsonUtil.json2Map(xmlJSONObj.toString());
        return result;
    }

}
