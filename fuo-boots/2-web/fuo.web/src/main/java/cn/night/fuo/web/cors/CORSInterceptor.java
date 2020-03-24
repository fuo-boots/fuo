package cn.night.fuo.web.cors;

import cn.night.fuo.utils.Utils;
import cn.night.fuo.web.core.mvc.IFuoMvcInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CORSInterceptor implements IFuoMvcInterceptor {

    @Autowired
    private CorsEnvironment corsEnvironment;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (corsEnvironment.getEnable()) {
            try {
                String postOrigin = request.getHeader("Origin");
                String postOriginDomain = Utils.web.getDomain(postOrigin);
                if (!StringUtils.isEmpty(postOriginDomain) && corsEnvironment.getOrigins().contains(postOriginDomain.toLowerCase())) {
                    response.setHeader("Access-Control-Allow-Origin", postOrigin);
                    response.setHeader("Access-Control-Allow-Methods", corsEnvironment.getAllowMethod());
                    response.setHeader("Access-Control-Max-Age", corsEnvironment.getMaxAge());
                    response.setHeader("Access-Control-Allow-Credentials", corsEnvironment.getAllowCredentials());
                    response.setHeader("Access-Control-Allow-Headers", corsEnvironment.getAllowHeaders());
                }
            } catch (Exception e) {
                log.error("CORSInterceptor: preHandle 异常", e);
            }
        }

        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
