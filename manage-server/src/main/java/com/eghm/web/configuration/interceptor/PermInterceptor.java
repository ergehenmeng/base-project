package com.eghm.web.configuration.interceptor;

import cn.hutool.core.collection.CollUtil;
import com.eghm.annotation.SkipPerm;
import com.eghm.configuration.interceptor.InterceptorAdapter;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.ErrorCode;
import com.eghm.model.SysMenu;
import com.eghm.service.sys.SysMenuService;
import com.eghm.utils.WebUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2022/11/4
 */
@Slf4j
@AllArgsConstructor
public class PermInterceptor implements InterceptorAdapter {

    private static final Map<String, List<String>> PERM_MAP = new ConcurrentHashMap<>(256);

    private final SysMenuService sysMenuService;

    public static final String DELIMITERS = ",; ";

    @PostConstruct
    public void refresh() {
        List<SysMenu> selectList = sysMenuService.getButtonList();
        PERM_MAP.clear();
        for (SysMenu menu : selectList) {
            if (isNotBlank(menu.getSubPath())) {
                for (String subUrl : StringUtils.tokenizeToStringArray(menu.getSubPath(), DELIMITERS)) {
                    List<String> codeList = PERM_MAP.getOrDefault(subUrl, new ArrayList<>(8));
                    codeList.add(menu.getCode());
                    PERM_MAP.put(subUrl, codeList);
                }
            }
        }
    }

    @Override
    public boolean beforeHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        if (this.getAnnotation(handler, SkipPerm.class) != null || this.getClassAnnotation(handler, SkipPerm.class) != null || this.match(request)) {
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
