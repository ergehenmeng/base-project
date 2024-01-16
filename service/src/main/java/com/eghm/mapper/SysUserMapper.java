package com.eghm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.user.UserQueryRequest;
import com.eghm.model.SysUser;
import com.eghm.vo.user.SysUserResponse;
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
    Page<SysUserResponse> listPage(Page<SysUserResponse> page, @Param("param") UserQueryRequest request);
}