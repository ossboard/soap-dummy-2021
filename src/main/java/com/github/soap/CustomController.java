package com.github.soap;

import com.github.soap.utils.DarcXmlUtil;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Controller
public class CustomController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String docs(ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
        return "redirect:/wss/cams.wsdl";
    }

    @RequestMapping(value = {"/ws","/ws/**"})
    public ResponseEntity<String> wss(@RequestBody String body, ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {

        System.out.println(body);
        try {

            Map<String, Object> map1 = DarcXmlUtil.xml2Map(body);
            Map<String, Object> map2 = null;
            for (String key : map1.keySet()) {
                map2 = (Map<String, Object>) MapUtils.getMap(map1, key); // soapenv:Envelope
                break;
            }
            Map<String, Object> map3 = null;
            for (String key : map2.keySet()) {
                if (StringUtils.indexOf(StringUtils.upperCase(key), "BODY") > -1) {
                    map3 = (Map<String, Object>) MapUtils.getMap(map2, key); // soapenv:Body
                    break;
                }
            }
            Map<String, Object> map4 = null;
            for (String key : map3.keySet()) {
                if (StringUtils.indexOf(StringUtils.upperCase(key), "CAMSEXCALL") > -1) {
                    map4 = (Map<String, Object>) MapUtils.getMap(map3, key); // cmd:CamsExCallRequest
                    break;
                }
            }
            String requestxml = null;
            for (String key : map4.keySet()) {
                if (StringUtils.indexOf(StringUtils.upperCase(key), "REQUESTXML") > -1) {
                    requestxml = MapUtils.getString(map4, key); // cmd:requestxml
                    break;
                }
            }

            System.out.println(requestxml);

            Map<String, Object> map10 = DarcXmlUtil.xml2Map(requestxml);
            Map<String, Object> map11 = (Map<String, Object>) MapUtils.getMap(map10, "mamex_request");
            Map<String, Object> map12 = (Map<String, Object>) MapUtils.getMap(map11, "cams_ex_create_recordfile_from_cms");
            String bsid = MapUtils.getString(map12, "bsid");
            String assetid = MapUtils.getString(map12, "assetid");
            String assettype = MapUtils.getString(map12, "assettype");

            System.out.println("bsid = " + bsid );
            System.out.println("assetid = " + assetid );
            System.out.println("assettype = " + assettype );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String s = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <SOAP-ENV:Header/>\n" +
                "   <SOAP-ENV:Body>\n" +
                "      <ns2:CamsExCallResponse xmlns:ns2=\"http://cmd.soap.cams\">\n" +
                "         <ns2:return><![CDATA[<mamex_response>\n" +
                "  <metaset>\n" +
                "    <DSID>000000000001</DSID>\n" +
                "    <error_code></error_code>\n" +
                "    <error_msg></error_msg>\n" +
                "  </metaset>\n" +
                "</mamex_response>]]></ns2:return>\n" +
                "      </ns2:CamsExCallResponse>\n" +
                "   </SOAP-ENV:Body>\n" +
                "</SOAP-ENV:Envelope>";

        ResponseEntity<String> responseEntity = new ResponseEntity<String>(s, HttpStatus.OK);
        return responseEntity;
    }

}
