package com.eghm.web.configuration.listener;

import com.eghm.configuration.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 自定义监听器用来设置application级别的参数,以便于freemarker直接在页面显示
 *
 * @author 二哥很猛
 * @date 2018/1/18 18:38
 */
@WebListener
public class ContextWebListener implements ServletContextListener {

    private ApplicationProperties applicationProperties;

    @Autowired
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("ctxPath", servletContext.getContextPath());
        servletContext.setAttribute("version", applicationProperties.getVersion());
    }

}
