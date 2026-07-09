package com.eghm.domain.operate.model;

import com.eghm.domain.shared.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 轮播图管理
 *
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Banner extends BaseEntity {

    /** 标题 */
    private String title;

    /** 轮播图类型:由sys_dict的banner_type维护(不同模块的轮播均在该表中维护) */
    private Integer bannerType;

    /** 客户端类型: PC,IOS,ANDROID,H5,WECHAT_MINI */
    private String clientType;

    /** 状态 true:启用 false:禁用 */
    private Boolean state;

    /** 轮播图片地址 */
    private String imgUrl;

    /** 轮播图点击后跳转的URL */
    private String jumpUrl;

    /** 轮播图顺序(小<->大) 最小的在最前面 */
    private Integer sort;

    /** 开始展示时间(可在指定时间后开始展示) */
    private LocalDateTime startTime;

    /** 取消展示的时间(只在某个时间段展示) */
    private LocalDateTime endTime;

    /** 是否可点击 0:否 1:可以 */
    private Boolean click;

    /** 备注信息 */
    private String remark;

    public void enable() {
        this.state = true;
    }

    public void disable() {
        this.state = false;
    }

    public boolean isEnabled() {
        return Boolean.TRUE.equals(this.state);
    }

    public boolean isEffective(java.time.LocalDateTime now) {
        if (!isEnabled()) {
            return false;
        }
        if (startTime != null && now.isBefore(startTime)) {
            return false;
        }
        if (endTime != null && now.isAfter(endTime)) {
            return false;
        }
        return true;
    }

}
