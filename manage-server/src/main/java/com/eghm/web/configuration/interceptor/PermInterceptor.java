package com.eghm.web.configuration.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.annotation.SkipPerm;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.ErrorCode;
import com.eghm.model.SysMenu;
import com.eghm.service.sys.SysMenuService;
import com.eghm.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 二哥很猛
 * @since 2022/11/4
 */
@Slf4j
@AllArgsConstructor
public class PermInterceptor implements InterceptorAdapter {

    private static final Map<String, List<String>> PERM_MAP = new ConcurrentHashMap<>(256);

    private final SysMenuService sysMenuService;

    @PostConstruct
    public void refresh() {
        List<SysMenu> selectList = sysMenuService.getButtonList();
        PERM_MAP.clear();
        for (SysMenu menu : selectList) {
            if (StrUtil.isNotBlank(menu.getSubPath())) {
                for (String subUrl : StringUtils.tokenizeToStringArray(menu.getSubPath(), ",; ")) {
                    List<String> codeList = PERM_MAP.getOrDefault(subUrl, new ArrayList<>(8));
                    codeList.add(menu.getCode());
                    PERM_MAP.put(subUrl, codeList);
                }
            }
        }
    }

    @Override
    public boolean beforeHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        if (this.getAnnotation(handler, SkipPerm.class) != null || this.match(request)) {
            return true;
        }
        log.warn("用户无权限访问该地址 [{}] 用户ID:[{}]", request.getRequestURI(), SecurityHolder.getUserId());
        WebUtil.printJson(response, ErrorCode.ACCESS_DENIED);
        return false;
    }

    /**
     * 校验登陆人权限与访问的该url是否匹配
     *
     * @param request 请求信息
     * @return true: 匹配 false: 不匹配
     */
    private boolean match(HttpServletRequest request) {
        UserToken userToken = SecurityHolder.getUserRequired();
        List<String> codeList = userToken.getAuthList();
        List<String> codes = PERM_MAP.get(request.getRequestURI());

        return CollUtil.isNotEmpty(codeList) && CollUtil.isNotEmpty(codes) && CollUtil.containsAny(codeList, codes);
    }
}
