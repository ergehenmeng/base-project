package com.eghm.web.configuration.filter;

import cn.hutool.http.HtmlUtil;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2023/9/15
 */
@Slf4j
public class XssHttServletRequestWrapper extends ByteHttpServletRequestWrapper  {

    public XssHttServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    protected String filterBody(String content) {
        return filterHtml(content);
    }

    @Override
    public String getHeader(String name) {
        return filterHtml(super.getHeader(name));
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(filterHtml(name));
        if (StringUtil.isNotBlank(value)) {
            value = filterHtml(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters != null && parameters.length != 0) {
            for(int i = 0; i < parameters.length; ++i) {
                parameters[i] = filterHtml(parameters[i]);
            }
            return parameters;
        } else {
            return new String[0];
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; ++i) {
                values[i] = filterHtml(values[i]);
            }

            map.put(entry.getKey(), values);
        }

        return map;
    }

    private static String filterHtml(String html) {
        if (html == null) {
            return null;
        }
        return HtmlUtil.filter(html);
    }
}
