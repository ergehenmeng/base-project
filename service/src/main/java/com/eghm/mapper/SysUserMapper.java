package com.eghm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.user.UserQueryRequest;
import com.eghm.model.SysUser;
import com.eghm.vo.user.UserResponse;
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

    /**
     * 获取商户类型
     *
     * @param userId 用户id
     * @return 商户类型 1 2 4 8 16 32
     */
    int getMerchantType(@Param("userId") Long userId);
}