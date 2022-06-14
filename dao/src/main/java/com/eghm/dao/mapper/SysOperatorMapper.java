package com.eghm.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysOperator;

/**
 * @author 二哥很猛
 */
public interface SysOperatorMapper extends BaseMapper<SysOperator> {

    /**
     * 根据手机号码查询管理员信息
     * @param mobile 手机号码
     * @return 系统用户
     */
    SysOperator getByMobile(String mobile);

}