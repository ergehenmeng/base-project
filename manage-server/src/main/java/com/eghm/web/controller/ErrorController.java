package com.eghm.web.controller;

import com.eghm.annotation.SkipPerm;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author 二哥很猛
 * @since 2024/11/1
 */
@Slf4j
@Tag(name= "错误提示")
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    @SkipPerm
    public RespBody<Void> error(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Object path = webRequest.getAttribute(RequestDispatcher.ERROR_REQUEST_URI, RequestAttributes.SCOPE_REQUEST);
        log.error("请求地址不存在: [{}]", path);
        return RespBody.error(ErrorCode.PAGE_NOT_FOUND.getCode(), String.format(ErrorCode.PAGE_NOT_FOUND.getMsg(), path));
    }

}
