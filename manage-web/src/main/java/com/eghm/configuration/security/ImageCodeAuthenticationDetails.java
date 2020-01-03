package com.eghm.configuration.security;

import com.eghm.common.constant.CommonConstant;
import com.eghm.common.constant.SecurityParam;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取图形验证码信息
 *
 * @author 二哥很猛
 * @date 2018/1/25 14:49
 */
class ImageCodeAuthenticationDetails extends WebAuthenticationDetails {

    private static final long serialVersionUID = 6385522500580848508L;
    /**
     * 前台传递过来的图形验证码
     */
    private String imageCode;

    /**
     * session中存放的图形验证码
     */
    private String sessionImageCode;

    ImageCodeAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.imageCode = request.getParameter(SecurityParam.IMAGE_CODE);
        this.sessionImageCode = (String) request.getSession().getAttribute(CommonConstant.IMG_AUTH_CODE);
    }

    String getImageCode() {
        return imageCode;
    }


    String getSessionImageCode() {
        return sessionImageCode;
    }

}
