package com.eghm.web.configuration.interceptor;

import cn.hutool.core.util.StrUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.model.SysMenu;
import com.eghm.model.dto.ext.JwtManage;
import com.eghm.model.dto.ext.RespBody;
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
        JwtManage jwtManage = SecurityHolder.getManageRequired();
        List<String> codeList = jwtManage.getAuthList();
        String code = PERM_MAP.get(request.getRequestURI());
        return codeList.contains(code);
    }
}
