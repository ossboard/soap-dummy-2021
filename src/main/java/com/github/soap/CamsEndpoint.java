package com.github.soap;

import cams.soap.cmd.CamexReponse;
import cams.soap.cmd.CamsExCallRequest;
import cams.soap.cmd.CamsExCallResponse;
import com.github.soap.utils.DarcXmlUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Map;

@Endpoint
public class CamsEndpoint {
	private static final String NAMESPACE_URI = "http://cmd.soap.cams";


	@Autowired
	public CamsEndpoint() {
	}




	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CamsExCallRequest")
	@ResponsePayload
	public CamsExCallResponse camsExCall(@RequestPayload CamsExCallRequest request) throws Exception {

		String xml = request.getRequestxml();
		Map<String, Object> map1 = DarcXmlUtil.xml2Map(xml);
		Map<String, Object> map2 = (Map<String, Object>) MapUtils.getMap(map1, "mamex_request");
		Map<String, Object> map3 = (Map<String, Object>) MapUtils.getMap(map2, "cams_ex_create_recordfile_from_cms");
		String bsid = MapUtils.getString(map3, "bsid");
		String assetid = MapUtils.getString(map3, "assetid");
		String assettype = MapUtils.getString(map3, "assettype");
		String dsid = "00000001";
		CamsExCallResponse response = new CamsExCallResponse();
		CamexReponse reponse = new CamexReponse();
		String metaset = "<mamex_response>\n" +
				"  <metaset>\n" +
				"    <DSID>" + dsid + "</DSID>\n" +
				"    <error_code></error_code>\n" +
				"    <error_msg></error_msg>\n" +
				"  </metaset>\n" +
				"</mamex_response>";
		reponse.setMetaset(metaset);
		response.setCamexReponse(reponse);
		return response;
	}
}
