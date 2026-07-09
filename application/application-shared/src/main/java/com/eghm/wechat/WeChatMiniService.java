package com.eghm.wechat;

/**
 * 小程序
 *
 * @author 二哥很猛
 * @since 2021/12/4 下午3:18
 */
public interface WeChatMiniService {

    /**
     * 微信小程序授权获取手机号
     *
     * @param jsCode jsCode
     * @return 手机号
     */
    String authMobile(String jsCode);

    /**
     * 生成小程序短链链接
     *
     * @param pageUrl    链接地址 相对路径(/pages/publishHomework/publishHomework?query1=q1) 必须是已发布的路径
     * @param pageTitle  页面标题
     * @param persistent 是否长久有效, 默认 false
     * @return 链接
     */
    String generateShortUrl(String pageUrl, String pageTitle, boolean persistent);

    /**
     * 生成小程序加密url
     *
     * @param pageUrl  链接地址 相对路径(/pages/publishHomework/publishHomework) 必须是已发布的路径
     * @param query    请求参数
     * @param validDay 有效日
     * @return 链接
     */
    String generateUrl(String pageUrl, String query, int validDay);

    /**
     * 生成小程序二维码
     *
     * @param path     相对路径
     * @param query    请求参数
     * @param validDay 有效时间(跟微信无关,是请求参数的缓存时间)
     * @return 二维码字节
     */
    byte[] generateQrCode(String path, String query, int validDay);

    /**
     * 解析小程序码参数
     *
     * @param scene 参数(32位以内)
     * @return 原始参数
     */
    String parseScene(String scene);
}
