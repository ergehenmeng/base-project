package com.fanyin.configuration;

import com.fanyin.common.utils.StringUtil;
import com.google.common.collect.Lists;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/7/4 13:48
 */
public abstract class AbstractIgnoreFilter implements Filter {

    /**
     * 排除忽略的地址或者模糊匹配的url
     */
    private List<String> exclude = Lists.newArrayListWithCapacity(4);

    /**
     * 忽略的parameter字段名称
     */
    private static final String EXCLUDE = "exclude";

    /**
     * url分隔符
     */
    private static final String DELIMITERS = ";";


    private AntPathMatcher matcher = new AntPathMatcher();


    @Override
    public void init(FilterConfig filterConfig){
        String param = filterConfig.getInitParameter(EXCLUDE);
        if(StringUtil.isNotEmpty(param)){
            String[] array = StringUtils.tokenizeToStringArray(param, DELIMITERS);
            exclude.addAll(Lists.newArrayList(array));
        }
    }

    /**
     * 当前request的url是否为忽略拦截的url
     * @param request request
     * @return true:需要忽略 false:不需要忽略
     */
    private boolean isIgnoreUrl(HttpServletRequest request){
        for (String url : exclude){
            if(matcher.match(url,request.getRequestURI())){
                return true;
            }
        }
        return false;
    }

    public List<String> getExclude() {
        return exclude;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if(this.isIgnoreUrl(httpRequest)){
            chain.doFilter(request,response);
        }else{
            this.doInternalFilter(request, response, chain);
        }
    }

    /**
     * 过滤器拦截处理由子类实现业务逻辑
     * @param request request
     * @param response response
     * @param chain chain
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    protected abstract void doInternalFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

    @Override
    public void destroy() {
    }
}
