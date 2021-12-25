package com.eghm.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.dto.operator.OperatorQueryRequest;
import com.eghm.dao.model.SysOperator;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysOperatorMapper extends BaseMapper<SysOperator> {

    /**
     * 根据手机号码查询管理员信息
     * @param mobile 手机号码
     * @return
     */
    SysOperator getByMobile(String mobile);

}