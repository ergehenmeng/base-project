package com.eghm.configuration.template;

import com.eghm.constants.CommonConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2019/7/10 15:50
 */
@Slf4j
@Service("freemarkerTemplate")
@AllArgsConstructor
public class FreemarkerTemplate implements TemplateEngine {

    private static final String DEFAULT_TITLE = "freemarker_title";

    private final Configuration configuration;

    @Override
    public String render(String content, Map<String, Object> param) {
        try {
            Template textTemplate = new Template(DEFAULT_TITLE, content, configuration);
            return this.doRender(textTemplate, param);
        } catch (Exception e) {
            log.error("freemarker解析异常", e);
            throw new BusinessException(ErrorCode.TEMPLATE_RENDER_ERROR);
        }
    }

    @Override
    public String renderFile(String path, Map<String, Object> params) {
        try {
            Template template = configuration.getTemplate(path, Locale.getDefault(), CommonConstant.CHARSET.displayName());
            return this.doRender(template, params);
        } catch (Exception e) {
            log.error("freemarker获取模板异常 path:[{}]", path, e);
            throw new BusinessException(ErrorCode.TEMPLATE_RENDER_ERROR);
        }
    }

    /**
     * 渲染
     *
     * @param template 模板
     * @param params   参数
     * @return 字符串
     * @throws IOException       异常
     * @throws TemplateException 异常
     */
    private String doRender(Template template, Map<String, Object> params) throws IOException, TemplateException {
        try (StringWriter writer = new StringWriter()) {
            template.process(params, writer);
            return writer.toString();
        }
    }

}
