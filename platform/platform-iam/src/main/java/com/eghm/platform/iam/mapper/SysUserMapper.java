package com.eghm.platform.iam.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.iam.dto.UserQueryRequest;
import com.eghm.platform.iam.entity.SysUser;
import com.eghm.platform.iam.vo.UserResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param request 分页条件
     * @return 列表
     */
    Page<UserResponse> listPage(Page<UserResponse> page, @Param("param") UserQueryRequest request);

}