package com.eghm.web.controller;

import com.eghm.annotation.SkipPerm;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * @author 二哥很猛
 * @since 2024/11/1
 */
@Slf4j
@Tag(name = "错误重定向")
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @GetMapping
    @SkipPerm
    @Operation(summary = "错误信息(get)")
    public RespBody<Void> getError(HttpServletRequest request) {
        return getBody(request);
    }

    @PostMapping
    @SkipPerm
    @Operation(summary = "错误信息(post)")
    public RespBody<Void> postError(HttpServletRequest request) {
        return getBody(request);
    }

    private static RespBody<Void> getBody(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Object path = webRequest.getAttribute(RequestDispatcher.ERROR_REQUEST_URI, RequestAttributes.SCOPE_REQUEST);
        log.error("请求地址不存在: [{}] [{}] ", request.getMethod(), path);
        return RespBody.error(ErrorCode.PAGE_NOT_FOUND.getCode(), String.format(ErrorCode.PAGE_NOT_FOUND.getMsg(), path));
    }

}
