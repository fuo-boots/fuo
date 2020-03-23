package cn.night.fuo.web.log.decorators;

import cn.night.fuo.pattern.Pattern;
import cn.night.fuo.pattern.asserts.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

public class HttpServletRequestLogDecorator {
    private HttpServletRequest request;
    private StringBuilder textBuilder = new StringBuilder(100);

    public HttpServletRequestLogDecorator(HttpServletRequest request) {
        Assert.notNull(request, "web log resolved failed,HttpServletResponse can not be null");
        this.request = request;
    }

    public String getUri() {
        return this.request.getRequestURI();
    }

    public String getMethod() {
        return this.request.getMethod();
    }

    public String getQueryString() {
        return this.request.getRequestURI();
    }

    public String getCookie() {
        return "Nil";
    }

    public String getBody()  {
        BufferedReader br = null;
        textBuilder.setLength(0);

        try {
            Pattern.using(request::getReader, bufferedReader -> {
                String str;
                while ((str = br.readLine()) != null) {
                    textBuilder.append(str);
                }
            });
        }catch (Exception e){
            return  "Nil";
        }
        return textBuilder.toString();
    }

    public String getHeaders() {
        textBuilder.setLength(0);
        Enumeration<String> headerNames = this.request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = this.request.getHeader(headerName);
            textBuilder.append(headerName).append(":").append(headerValue);
        }
        return textBuilder.toString();
    }
}
