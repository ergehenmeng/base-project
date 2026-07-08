package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 轮播图管理
 *
 * @author 二哥很猛
 */
@Data
@TableName("banner")
@EqualsAndHashCode(callSuper = true)
public class BannerPO extends BaseEntityPO {

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

}


