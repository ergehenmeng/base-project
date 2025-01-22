package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.LoginDevice;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface LoginDeviceMapper extends BaseMapper<LoginDevice> {

    /**
     * 添加或更新登陆设备信息(仅仅为了精简代码)
     * @param device 根据唯一性约束唯一性，如果存在则更新，不存在则插入
     */
    void insertOrUpdateSelective(LoginDevice device);

    /**
     * 查找指定设备是否有登陆日志
     *
     * @param memberId     用户id
     * @param serialNumber 唯一编号
     * @return 登陆日志
     */
    LoginDevice getBySerialNumber(@Param("memberId") Long memberId, @Param("serialNumber") String serialNumber);

    /**
     * 物理删除登陆设备信息(减少不必要的垃圾数据)
     *
     * @param memberId memberId
     * @param id       id
     */
    void deleteLoginDevice(@Param("memberId") Long memberId, @Param("id") Long id);

}