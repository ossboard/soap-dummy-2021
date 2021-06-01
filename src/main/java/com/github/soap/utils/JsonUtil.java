package com.github.soap.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    public static String object2String(Object o) {
        if (o == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            return null;
        }
    }

    public static Map json2MapNoException(String s) {
        try {
            return json2Map(s);
        } catch (Exception e) {
            return null;
        }
    }
    public static Map json2Map(String s) throws IOException {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s,HashMap.class);
    }

    public static Object json2Object(String s, Class<?> c) throws IOException {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s, c );
    }

    public static Map object2Map(Object o) throws IOException {
        if (o == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.convertValue(o, Map.class);
    }

    public static String printing(Object o) throws IOException {
        if (o == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

    public static String clob2String(Clob clob) throws IOException, SQLException {
        if (clob == null) {
            return null;
        }
        StringBuffer strOut = new StringBuffer();
        String str = "";
        BufferedReader br = new BufferedReader(clob.getCharacterStream());
        while ((str = br.readLine()) != null) {
            strOut.append(str);
        }
        return strOut.toString();
    }



}
