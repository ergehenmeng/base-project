package com.eghm.i18n.provider;

/**
 * @author 殿小二
 * @since 2026/5/18
 */
public interface RespBodyProvider {
    
    /**
     * 获取响应信息编码
     *
     * @return code
     */
    Integer getCode();
    
    /**
     * 获取响应信息编码对应的信息
     * @return msg
     */
    String getMsg();
    
    /**
     * 设置响应信息编码对应的信息
     * @param msg msg
     */
    void setMsg(String msg);
    
    /**
     * 成功时的code (此码不做翻译)
     *
     * @return 200
     */
    Integer successCode();
}
