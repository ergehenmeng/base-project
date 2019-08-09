package com.fanyin.configuration;

import com.fanyin.freemark.AuthDirectiveModel;
import com.fanyin.freemark.DictDirectiveModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * freemarker配置
 * @author 二哥很猛
 * @date 2018/11/27 18:02
 */
@Configuration
public class FreemarkerConfiguration {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private DictDirectiveModel dictDirectiveModel;

    @Autowired
    private AuthDirectiveModel authDirectiveModel;

    /**
     * 加载自定义宏和全局定义的宏
     */
    @PostConstruct
    public void setSharedVariable(){
        configuration.setSharedVariable("select",dictDirectiveModel);
        configuration.setSharedVariable("auth",authDirectiveModel);
    }

}
