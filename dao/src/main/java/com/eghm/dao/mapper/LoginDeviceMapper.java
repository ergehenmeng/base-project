package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.LoginDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface LoginDeviceMapper extends BaseMapper<LoginDevice> {

    /**
     * 添加或更新登陆设备信息(仅仅为了精简代码)
     */
    int insertOrUpdateSelective(LoginDevice record);

    /**
     * 查找指定设备是否有登陆日志
     * @param userId   用户id
     * @param serialNumber 唯一编号
     * @return 登陆日志
     */
    LoginDevice getBySerialNumber(@Param("userId")Long userId, @Param("serialNumber") String serialNumber);

    /**
     * 物理删除登陆设备信息(减少不必要的垃圾数据)
     * @param userId userId
     * @param serialNumber 设备唯一识别号
     * @return 1
     */
    int deleteLoginDevice(@Param("userId")Long userId, @Param("serialNumber") String serialNumber);

    /**
     * 用户登陆设备列表
     * @param userId userId
     * @return 列表
     */
    List<LoginDevice> getByUserId(@Param("userId") Long userId);
}