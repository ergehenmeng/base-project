package com.fanyin.configuration.template;

import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.constants.SystemConstant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * @author 王艳兵
 * @date 2019/7/10 15:50
 */
@Slf4j
public class FreemarkerHtmlTemplate implements HtmlTemplate {

    @Autowired
    private Configuration configuration;

    private static final String DEFAULT_TITLE = "freemarker_title";

    @Override
    public String renderHtml(String html, Map<String, Object> param) {
        try{
            Template textTemplate = new Template(DEFAULT_TITLE, html, configuration);
            return this.doRender(textTemplate,param);
        }catch (Exception e){
            log.error("freemarker解析异常",e);
            throw new BusinessException(ErrorCodeEnum.TEMPLATE_RENDER_ERROR);
        }
    }

    /**
     * 渲染
     * @param template 模板
     * @param params 参数
     * @return 字符串
     * @throws Exception 解析异常
     */
    private String doRender(Template template,Map<String, Object> params)throws Exception{
        try (StringWriter writer = new StringWriter()){
            template.process(params,writer);
            return writer.toString();
        }
    }


    @Override
    public String renderFile(String path, Map<String, Object> params) {
        try{
            Template template = configuration.getTemplate(path, Locale.getDefault(), SystemConstant.CHARSET);
            return this.doRender(template,params);
        }catch (Exception e){
            log.error("freemarker获取模板异常 path:[{}]",path,e);
            throw new BusinessException(ErrorCodeEnum.TEMPLATE_RENDER_ERROR);
        }
    }
}
