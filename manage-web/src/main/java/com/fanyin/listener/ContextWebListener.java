package com.fanyin.listener;

import com.fanyin.configuration.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 自定义监听器用来设置application级别的参数,以便于freemarker直接在页面显示
 * @author 二哥很猛
 * @date 2018/1/18 18:38
 */
@WebListener
public class ContextWebListener implements ServletContextListener {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("ctxPath",servletContext.getContextPath());
        servletContext.setAttribute("version", applicationProperties.getVersion());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
