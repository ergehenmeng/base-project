package com.eghm.common;

import com.eghm.vo.sys.ext.SysAreaVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
public interface CommonService {

    /**
     * 查询地址列表(树状结构)
     *
     * @return list
     */
    List<SysAreaVO> getTreeAreaList();

    /**
     * 查询地址列表(树状结构)
     *
     * @param gradeList 省份1级 市2级 县3级
     * @return list
     */
    List<SysAreaVO> getTreeAreaList(List<Integer> gradeList);

    /**
     * rsa 私钥解密
     *
     * @param rsa rsa加密后的字符串
     * @return rsa 解密后的字符串
     */
    String rsaDecrypt(String rsa);

    /**
     * 保存用户的按钮权限
     *
     * @param token token
     * @param permList 权限列表
     */
    void savePermission(String token, List<String> permList);

    /**
     * 获取用户的按钮权限
     *
     * @param token token
     * @return 权限列表
     */
    List<String> getPermission(String token);

    /**
     * 清除用户的按钮权限
     *
     * @param token token
     */
    void clearPermission(String token);
}
