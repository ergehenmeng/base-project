package com.eghm.platform.config.service;

import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.platform.config.vo.SysAreaVO;

import java.util.List;
import java.util.function.Consumer;

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
     * 点赞或取消点赞
     *
     * @param key      key
     * @param hashKey  value
     * @param consumer 后置处理 true:点赞 false:取消点赞
     */
    void praise(String key, String hashKey, Consumer<Boolean> consumer);

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

    /**
     * 生成下一个id
     *
     * @param maxId 当前列的最大值
     * @param pid 父节点
     * @param step 步长
     * @param errorCode 超过最大时的错误码
     * @return 下一个id
     */
    String generateNextId(String maxId, String pid, int step, ErrorCode errorCode);
}
