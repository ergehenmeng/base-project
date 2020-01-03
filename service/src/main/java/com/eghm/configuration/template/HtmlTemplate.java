package com.eghm.configuration.template;

import java.util.Map;

/**
 * html模板渲染 默认实现freemarker,可使用thymeleaf实现
 * @author 二哥很猛
 * @date 2019/7/10 15:47
 */
public interface HtmlTemplate {

    /**
     * 模板渲染
     * @param html html文件
     * @param param 参数名
     * @return 字符串格式html
     */
    String renderHtml(String html, Map<String, Object> param);

    /**
     * 模板渲染
     * @param path 文件及路径
     * @param param 参数名
     * @return 字符串格式html
     */
    String renderFile(String path, Map<String, Object> param);
}
