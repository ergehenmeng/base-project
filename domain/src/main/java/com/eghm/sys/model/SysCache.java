package com.eghm.sys.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统缓存
 *
 * @author 二哥很猛
 */
@Data
public class SysCache {

    /** 主键 */
    private Long id;

    /** 缓存名称 */
    private String title;

    /** 缓存名称 必须与CacheConstant中保持一致 */
    private String cacheName;

    /** 缓存更新状态 0:未更新 1:更新成功 2:更新失败 */
    private Integer state;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 备注说明 */
    private String remark;

    public void initialize(String title, String cacheName, String remark) {
        this.title = title;
        this.cacheName = cacheName;
        this.remark = remark;
        this.state = 0;
    }

    public void markUpdated() {
        this.state = 1;
        this.updateTime = LocalDateTime.now();
    }

    public void markUpdateFailed() {
        this.state = 2;
        this.updateTime = LocalDateTime.now();
    }

    public boolean isUpdated() {
        return Integer.valueOf(1).equals(this.state);
    }
}
