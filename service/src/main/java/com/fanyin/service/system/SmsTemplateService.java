package com.fanyin.service.system;

/**
 * @author 二哥很猛
 * @date 2019/8/21 10:35
 */
public interface SmsTemplateService {


    /**
     * 获取短信发送模板
     * @param nid nid
     * @return 短信内容模板
     */
    String getTemplate(String nid);

}
