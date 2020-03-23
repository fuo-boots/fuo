package cn.night.fuo.web.log.decorators;

import cn.night.fuo.pattern.asserts.Assert;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class HttpServletResponseDecorator {
    private HttpServletResponse response;
    private StringBuilder textBuilder = new StringBuilder(100);

    public HttpServletResponseDecorator(HttpServletResponse response) {
        Assert.notNull(response,"web log resolved failed,HttpServletResponse can not be null");
        this.response = response;
    }

    public String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(responseWrapper, ContentCachingResponseWrapper.class);
        if(wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if(buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                return payload;
            }
        }
        return "";
    }

    public String getBody(){
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
        return getResponseBody(wrapperResponse);
    }
}
