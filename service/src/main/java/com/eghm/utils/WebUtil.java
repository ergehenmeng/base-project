package com.eghm.utils;

import com.eghm.enums.ErrorCode;
import com.eghm.dto.ext.RespBody;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * web工具类
 *
 * @author 二哥很猛
 * @date 2018/1/26 10:30
 */
public class WebUtil {

    private WebUtil() {
    }

    /**
     * 直接返回前台json格式信息
     *
     * @param response 响应对象
     * @param object   json内容
     * @throws IOException exception
     */
    public static void printJson(HttpServletResponse response, RespBody<Void> object) throws IOException {
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            writer.write(new Gson().toJson(object));
            writer.flush();
        }
    }


    /**
     * 直接返回前台错误json格式信息
     *
     * @param response 响应对象
     * @param errorCode 错误信息
     * @throws IOException exception
     */
    public static void printJson(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            writer.write(new Gson().toJson(RespBody.error(errorCode)));
            writer.flush();
        }
    }

    /**
     * 是否为spring自动注入的对象
     * @param paramType 对象类型
     * @return true: 自动注入 false:非自动注入
     */
    public static boolean isAutoInject(Class<?> paramType) {
        return ServletRequest.class.isAssignableFrom(paramType) ||
                ServletResponse.class.isAssignableFrom(paramType) ||
                MultipartRequest.class.isAssignableFrom(paramType) ||
                MultipartFile.class.isAssignableFrom(paramType) ||
                HttpSession.class.isAssignableFrom(paramType) ||
                Model.class.isAssignableFrom(paramType);
    }

    /**
     * 参数校验或数据绑定异常
     * @param result 绑定结果
     * @return 错误信息
     */
    public static RespBody<Void> fieldError(BindingResult result) {
        FieldError error = result.getFieldError();
        if (error == null) {
            return RespBody.error(ErrorCode.PARAM_VERIFY_ERROR.getCode(), result.getAllErrors().get(0).getDefaultMessage());
        } else {
            return RespBody.error(ErrorCode.PARAM_VERIFY_ERROR.getCode(), error.getDefaultMessage());
        }
    }
}
