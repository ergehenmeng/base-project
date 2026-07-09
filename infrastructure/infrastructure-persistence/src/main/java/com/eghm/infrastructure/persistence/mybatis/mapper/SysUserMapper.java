package com.eghm.infrastructure.persistence.mybatis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.user.UserQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.po.SysUserPO;
import com.eghm.vo.sys.user.UserResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SysUserMapper extends BaseMapper<SysUserPO> {

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param request 分页条件
     * @return 列表
     */
    Page<UserResponse> listPage(Page<UserResponse> page, @Param("param") UserQueryRequest request);

}
