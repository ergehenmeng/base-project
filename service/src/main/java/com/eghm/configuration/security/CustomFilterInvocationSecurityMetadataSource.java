package com.eghm.configuration.security;

import cn.hutool.core.util.StrUtil;
import com.eghm.dao.model.SysMenu;
import com.eghm.service.sys.SysMenuService;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 权限配置资源管理器
 *
 * @author 二哥很猛
 * @date 2018/1/25 11:01
 */
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final Map<String, Collection<ConfigAttribute>> map = new ConcurrentHashMap<>(256);

    private SysMenuService sysMenuService;

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    /**
     * 重新加载所有菜单权限
     */
    @PostConstruct
    public void loadResource() {
        map.clear();
        List<SysMenu> list = sysMenuService.getAllList();
        for (SysMenu menu : list) {
            if (StrUtil.isNotBlank(menu.getUrl())) {
                List<String> subUrl = this.getTotalUrl(menu);
                List<ConfigAttribute> attributes = SecurityConfig.createList(menu.getNid());
                // 将该权限所涉及到所有访问链接均放入,防止操作人员知道连接,但没有权限,却能访问的问题
                for (String url : subUrl) {
                    map.put(url, attributes);
                }
            }
        }
    }

    /**
     * 获取菜单所用有的子菜单及本身菜单,子菜单逗号分割
     *
     * @param menu 菜单
     * @return 列表
     */
    private List<String> getTotalUrl(SysMenu menu) {
        List<String> stringList = Lists.newArrayList(menu.getUrl());
        if (StrUtil.isNotBlank(menu.getSubUrl())) {
            Iterable<String> split = Splitter.on(";").split(menu.getSubUrl());
            split.forEach(stringList::add);
        }
        return stringList;
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : map.entrySet()) {
            if (new AntPathRequestMatcher(entry.getKey()).matches(request)) {
                return entry.getValue();
            }
        }
        return new ArrayList<>(1);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
