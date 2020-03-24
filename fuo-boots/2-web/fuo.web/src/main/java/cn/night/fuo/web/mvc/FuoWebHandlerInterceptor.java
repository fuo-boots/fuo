package cn.night.fuo.web.mvc;

import lombok.Data;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

@Data
public class FuoWebHandlerInterceptor {
    private String name;
    private HandlerInterceptor interceptor;
    private List<String> path = new ArrayList<>();
    private List<String> excludes = new ArrayList<>();
}
