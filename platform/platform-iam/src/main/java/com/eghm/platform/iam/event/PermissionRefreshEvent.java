package com.eghm.platform.iam.event;

import org.springframework.context.ApplicationEvent;

/**
 * 权限菜单刷新事件
 * 当菜单信息变更后发布该事件，PermInterceptor 监听并重新加载权限映射
 *
 * @author 二哥很猛
 * @since 2026/6/5
 */
public class PermissionRefreshEvent extends ApplicationEvent {

    public PermissionRefreshEvent() {
        super("Menu permission refresh");
    }
}