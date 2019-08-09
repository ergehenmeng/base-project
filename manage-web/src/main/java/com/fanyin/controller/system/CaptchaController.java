package com.fanyin.controller.system;

import com.fanyin.common.constant.CommonConstant;
import com.fanyin.controller.AbstractController;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图形验证码controller
 * @author 二哥很猛
 * @date 2018/1/19 11:50
 */
@Controller
@Slf4j
public class CaptchaController extends AbstractController {


    @Autowired
    private Producer producer;

    /**
     * 图形验证码
     * @param module 哪个模块下的访问的
     * @param session session对象
     * @param response 响应对象
     * @throws IOException 写流异常
     */
    @GetMapping("/{module}/captcha")
    public void module(@PathVariable("module")String module, HttpSession session, HttpServletResponse response)throws IOException{
        String text = producer.createText();
        writeBack(module,text,session,response);
    }

    /**
     * 默认的图片验证码
     * @param session session对象
     * @param response 响应对象
     * @throws IOException 写异常
     */
    @GetMapping("/captcha")
    @ResponseBody
    public void captcha(HttpSession session, HttpServletResponse response)throws IOException{
        String text = producer.createText();
        writeBack(CommonConstant.IMG_AUTH_CODE,text,session,response);
    }

    /**
     * 将图片数据写入响应中
     * @param key 图片保存session中的key
     * @param value 图片验证码的值
     * @param session session对象
     * @param response 响应
     * @throws IOException 写流异常
     */
    private void writeBack(String key,String value,HttpSession session, HttpServletResponse response)throws IOException{
        response.setDateHeader("Expires", 0);
        response.setHeader("CacheCreate-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        log.info("图形验证码************[{}]*****[{}]************",key,value);
        this.putSession(session, key, value);
        BufferedImage bi = producer.createImage(value);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        out.flush();
        out.close();
    }

}
