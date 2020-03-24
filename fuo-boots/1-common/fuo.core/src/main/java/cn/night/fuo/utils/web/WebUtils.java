package cn.night.fuo.utils.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class WebUtils {

    public String getDomain(String url) {
        try {
            if (StringUtils.isEmpty(url)) return url;
            String[] s_URL = url.split("//");
            String urlClear = "";
            if (s_URL.length >= 2) {
                urlClear = s_URL[1];
            } else if (s_URL.length == 1) {
                urlClear = s_URL[0];
            }
            String[] s_domain = urlClear.split(":");
            if (s_domain.length > 0) {
                return s_domain[0];
            }
            return url;
        } catch (Exception e) {
            log.error("CORSInterceptor: getDomain读取失败" + url, e);
            return url;
        }
    }
}
