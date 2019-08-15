package com.fanyin.controller;

import com.fanyin.model.ext.RespBody;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 4xx 5xx异常返回页面或者json结果
 * 该类为了与全局返回数据结构保持一致
 * @author 二哥很猛
 * @date 2019/1/18 14:17
 */
@RequestMapping("${server.error.path:'/error'}")
//@Controller
public class ErrorPageController extends AbstractErrorController {

    private ErrorProperties errorProperties;

    public ErrorPageController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers, ErrorProperties errorProperties) {
        super(errorAttributes, errorViewResolvers);
        this.errorProperties = errorProperties;
    }

    /**
     * 错误页面返回
     * @param request request
     * @param response response
     * @return ftl
     */
    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
                request, false));
        ModelAndView modelAndView = super.resolveErrorView(request, response, status, model);
        return (modelAndView == null ? new ModelAndView("error", model) : modelAndView);
    }

    /**
     * json错误返回
     * @param request request
     * @return response
     */
    @RequestMapping
    @ResponseBody
    public RespBody error(HttpServletRequest request) {
        HttpStatus status = super.getStatus(request);
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Object msg = requestAttributes.getAttribute(RequestDispatcher.ERROR_MESSAGE, RequestAttributes.SCOPE_REQUEST);
        return RespBody.getInstance().setCode(status.value()).setMsg(msg != null ? msg.toString() : "");
    }

    @Override
    public String getErrorPath() {
        return errorProperties.getPath();
    }
}
