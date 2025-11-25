package gov.cms.esmd.common.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public final  class HttpClientUtil {



    public static HttpPost getHttpPost(String endpointUrl, RequestConfig requestConfig, Map<String, String> headerMap, ObjectNode jsonBody) {

        HttpPost httpPost = new HttpPost(endpointUrl);
        httpPost.setConfig(requestConfig);
        headerMap.forEach(httpPost::addHeader);
        httpPost.setEntity(new StringEntity(jsonBody!=null ? jsonBody.toString() : "{}", StandardCharsets.UTF_8));
        return httpPost;
    }

}
