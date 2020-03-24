package cn.night.fuo.web.log;

import cn.night.fuo.web.log.decorators.HttpServletRequestLogDecorator;
import cn.night.fuo.web.log.decorators.HttpServletResponseDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class WebLogFilter extends OncePerRequestFilter implements Ordered {
    private int order = Ordered.LOWEST_PRECEDENCE - 8;

    @Autowired
    private WebLogEnvironment webLogEnvironment;

    @Override
    public int getOrder() {
        return order;
    }

    private boolean checkLogRequest(HttpServletRequest request) {
        if (!this.webLogEnvironment.getEnabled()) return false;
        if (this.webLogEnvironment.getIsMethodAllowAll()) return true;
        if (this.webLogEnvironment.getMethodFilters().contains(request.getMethod())) {
            return true;
        }
        return false;
    }

    private void logRequest(HttpServletRequest request, StringBuilder textBuilder) {

        HttpServletRequestLogDecorator requestLogDecorator = new HttpServletRequestLogDecorator(request);
        textBuilder.setLength(0);
        textBuilder.append(request.getRequestURI()).append(" received web-request:");
        HashSet<String> includes = webLogEnvironment.getIncludeInfos();
        if (includes.contains(WebLogConf.WebLogIncludeInfoName.REQUEST_URI)) {
            textBuilder.append("uri:").append(requestLogDecorator.getUri()).append(",");
        }
        if (includes.contains(WebLogConf.WebLogIncludeInfoName.REQUEST_METHOD)) {
            textBuilder.append("method:").append(requestLogDecorator.getMethod()).append(",");
        }
        if (includes.contains(WebLogConf.WebLogIncludeInfoName.REQUEST_BODY)) {
            textBuilder.append("body:").append(requestLogDecorator.getBody()).append(",");
        }
        if (includes.contains(WebLogConf.WebLogIncludeInfoName.REQUEST_HEADER)) {
            textBuilder.append("header:").append(requestLogDecorator.getHeaders()).append(",");
        }
        if (includes.contains(WebLogConf.WebLogIncludeInfoName.REQUEST_COOKIE)) {
            textBuilder.append("cookie:").append(requestLogDecorator.getCookie()).append(",");
        }
        if (includes.contains(WebLogConf.WebLogIncludeInfoName.RESPONSE_BODY)) {
            textBuilder.append("res:").append(requestLogDecorator.getCookie()).append(",");
        }
        log.info(textBuilder.toString());
    }

    private void logResponse(HttpServletRequest request, HttpServletResponse response, ContentCachingResponseWrapper wrapperResponse, StringBuilder textBuilder) {
        HashSet<String> includes = webLogEnvironment.getIncludeInfos();
        if (includes.contains(WebLogConf.WebLogIncludeInfoName.RESPONSE_BODY)) {
            textBuilder.setLength(0);
            textBuilder.append(request.getRequestURI()).append(" send web-response:");
            HttpServletResponseDecorator responseDecorator = new HttpServletResponseDecorator(response);
            String responseBodyStr = responseDecorator.getResponseBody(wrapperResponse);
            textBuilder.append(responseBodyStr);
            log.info(textBuilder.toString());
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //日志trade
        ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);

        Boolean isWriteWebLog = checkLogRequest(request);

        StringBuilder textBuilder = new StringBuilder(200);
        try {
            if (isWriteWebLog) {
                logRequest(request, textBuilder);
            }
        } catch (Exception e) {
            log.error("WebLogInterceptor log request error", e);
        }

        try {
            filterChain.doFilter(wrapperRequest, wrapperResponse);
        } catch (Exception e) {
            log.error("WebLogInterceptor log process error:" + e.getMessage(), e);
        }

        try {
            if (isWriteWebLog) {
                logResponse(request, response, wrapperResponse, textBuilder);
            }
        } catch (Exception e) {
            log.error("WebLogInterceptor log response error", e);
        }

        wrapperResponse.copyBodyToResponse();
    }
}
