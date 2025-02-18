package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 系统缓存
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SysCache extends BaseEntity {

    /**
     * 缓存名称<br>
     * 表 : sys_cache<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 缓存名称 必须与CacheConstant中保持一致<br>
     * 表 : sys_cache<br>
     * 对应字段 : cache_name<br>
     */
    private String cacheName;

    /**
     * 缓存更新状态 0:未更新 1:更新成功 2:更新失败<br>
     * 表 : sys_cache<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    /**
     * 更新时间<br>
     * 表 : sys_cache<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 备注说明<br>
     * 表 : sys_cache<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    /**
     * 是否锁定 0:否 1:锁定(锁定状态刷新缓存无效)<br>
     * 表 : sys_cache<br>
     * 对应字段 : locked<br>
     */
    private Boolean locked;
}