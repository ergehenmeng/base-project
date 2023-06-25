package com.eghm.web.configuration.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.JwtUser;
import com.eghm.enums.ErrorCode;
import com.eghm.model.SysMenu;
import com.eghm.service.sys.SysMenuService;
import com.eghm.utils.WebUtil;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

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

    private static final Map<String, String> PERM_MAP = new ConcurrentHashMap<>(256);

    private final SysMenuService sysMenuService;

    @PostConstruct
    public void refresh() {
        List<SysMenu> selectList = sysMenuService.getButtonList();
        PERM_MAP.clear();
        for (SysMenu menu : selectList) {
            if (StrUtil.isNotBlank(menu.getSubPath())) {
                for (String subUrl : StrUtil.splitToArray(menu.getSubPath(), ',')) {
                    PERM_MAP.put(subUrl, menu.getCode());
                }
            }
        }
    }

    @Override
    public boolean beforeHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        SkipPerm permission = this.getAnnotation(handler, SkipPerm.class);
        if (permission != null || this.match(request)) {
            return true;
        }
        WebUtil.printJson(response, ErrorCode.ACCESS_DENIED);
        return false;
    }

    /**
     * 校验登陆人权限与访问的该url是否匹配
     * @param request 请求信息
     * @return true: 匹配 false: 不匹配
     */
    private boolean match(HttpServletRequest request) {
        JwtUser jwtUser = SecurityHolder.getUserRequired();
        List<String> codeList = jwtUser.getAuthList();
        String code = PERM_MAP.get(request.getRequestURI());
        return CollUtil.isNotEmpty(codeList) && codeList.contains(code);
    }
}
