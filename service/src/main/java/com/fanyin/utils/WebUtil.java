package com.fanyin.utils;

import com.alibaba.fastjson.JSONObject;
import com.fanyin.ext.RespBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * web工具类
 * @author 二哥很猛
 * @date 2018/1/26 10:30
 */
public class WebUtil {

    /**
     * 直接返回前台json格式信息
     * @param response 响应对象
     * @param object json内容
     * @throws IOException 水电费
     */
    public static void printJson(HttpServletResponse response, RespBody<Object> object) throws IOException{
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()){
            response.setContentType("application/json;charset=utf-8");
            writer.write(JSONObject.toJSONString(object));
            writer.flush();
        }
    }
}
