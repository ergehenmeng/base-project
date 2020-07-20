package com.eghm.configuration;

import com.eghm.freemark.AuthDirectiveModel;
import com.eghm.freemark.DictDirectiveModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * freemarker配置
 *
 * @author 二哥很猛
 * @date 2018/11/27 18:02
 */
@Configuration
public class FreemarkerConfiguration {

    private freemarker.template.Configuration configuration;

    private DictDirectiveModel dictDirectiveModel;

    private AuthDirectiveModel authDirectiveModel;

    @Autowired
    public void setConfiguration(freemarker.template.Configuration configuration) {
        this.configuration = configuration;
    }

    @Autowired
    public void setDictDirectiveModel(DictDirectiveModel dictDirectiveModel) {
        this.dictDirectiveModel = dictDirectiveModel;
    }

    @Autowired
    public void setAuthDirectiveModel(AuthDirectiveModel authDirectiveModel) {
        this.authDirectiveModel = authDirectiveModel;
    }

    /**
     * 加载自定义宏和全局定义的宏
     */
    @PostConstruct
    public void setSharedVariable() {
        configuration.setSharedVariable("select", dictDirectiveModel);
        configuration.setSharedVariable("auth", authDirectiveModel);
    }

}
