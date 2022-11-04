package com.eghm.web.configuration.interceptor;

import cn.hutool.core.util.StrUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.model.SysMenu;
import com.eghm.model.dto.ext.JwtOperator;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.sys.SysMenuService;
import com.eghm.utils.WebUtil;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 二哥很猛
 * @since 2022/11/4
 */
@AllArgsConstructor
public class PermInterceptor implements InterceptorAdapter {

    /**
     * 系统所有菜单
     */
    private static final Map<String, List<String>> PERM_MAP = new ConcurrentHashMap<>(256);

    /**
     * url匹配规则
     */
    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    private final SysMenuService sysMenuService;

    @PostConstruct
    public void refresh() {
        List<SysMenu> selectList = sysMenuService.getList();
        PERM_MAP.clear();
        for (SysMenu menu : selectList) {
            PERM_MAP.put(menu.getCode(), StrUtil.splitTrim(menu.getSubPath(), ','));
        }
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (!this.supportHandler(handler)) {
            return true;
        }
        SkipPerm permission = this.getAnnotation(handler, SkipPerm.class);
        if (permission != null) {
            return true;
        }
        boolean match = this.match(request);
        if (match) {
            return true;
        }
        WebUtil.printJson(response, RespBody.error(ErrorCode.SYSTEM_AUTH));
        return false;
    }

    /**
     * 校验登陆人权限与访问的该url是否匹配
     * @param request 请求信息
     * @return true: 匹配 false: 不匹配
     */
    private boolean match(HttpServletRequest request) {
        JwtOperator jwtOperator = SecurityHolder.getOperatorRequired();
        List<String> codeList = jwtOperator.getAuthList();
        for (String code : codeList) {
            List<String> urlList = PERM_MAP.get(code);
            for (String url : urlList) {
                if (MATCHER.match(url, request.getRequestURI())) {
                    return true;
                }
            }
        }
        return false;
    }
}
