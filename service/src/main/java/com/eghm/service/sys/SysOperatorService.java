package com.eghm.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SysOperator;
import com.eghm.model.dto.operator.OperatorAddRequest;
import com.eghm.model.dto.operator.OperatorEditRequest;
import com.eghm.model.dto.operator.OperatorQueryRequest;
import com.eghm.model.dto.operator.PasswordEditRequest;
import com.eghm.model.vo.login.LoginResponse;

/**
 * @author 二哥很猛
 * @date 2018/11/26 10:24
 */
public interface SysOperatorService {

    /**
     * 根据手机号码查询管理员信息
     * @param mobile 手机号码
     * @return 系统管理人员
     */
    SysOperator getByMobile(String mobile);

    /**
     * 更新登陆密码
     * @param request 前台参数
     * @return 新加密过的密码
     */
    String updateLoginPassword(PasswordEditRequest request);

    /**
     * 校验密码是否正确
     * @param rawPassword 原始密码
     * @param targetPassword 真实加密后的密码
     */
    void checkPassword(String rawPassword, String targetPassword);

    /**
     * 分页查询系统人员信息
     * @param request 请求参数
     * @return 系统人员信息
     */
    Page<SysOperator> getByPage(OperatorQueryRequest request);

    /**
     * 添加管理人员 初始密码默认手机号后6位
     * @param request 前台参数
     */
    void create(OperatorAddRequest request);

    /**
     * 根据手机号生成初始化密码,手机号后六位
     * @param mobile 手机号
     * @return 加密密码
     */
    String initPassword(String mobile);

    /**
     * 根据主键查询管理人员
     * @param id 主键
     * @return 用户信息
     */
    SysOperator getById(Long id);

    /**
     * 更新用户信息
     * @param request 请求参数
     */
    void update(OperatorEditRequest request);

    /**
     * 重置用户登录密码 默认手机号后六位
     *
     * @param id 系统用户id
     */
    void resetPassword(Long id);

    /**
     * 删除用户
     * @param id operatorId
     */
    void delete(Long id);

    /**
     * 锁定用户
     * @param id operatorId
     */
    void lockOperator(Long id);

    /**
     * 解锁用户
     * @param id id
     */
    void unlockOperator(Long id);

    /**
     * 系统用户登陆平台
     * @param userName 账号
     * @param password 密码
     * @return token及权限
     */
    LoginResponse login(String userName , String password);

}

