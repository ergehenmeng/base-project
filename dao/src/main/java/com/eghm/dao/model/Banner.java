package com.eghm.dao.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 轮播图管理
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class Banner extends BaseEntity {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("轮播图类型:由sys_dict的banner_type维护(不同模块的轮播均在该表中维护)")
    private Byte classify;

    @ApiModelProperty("客户端类型: PC,IOS,ANDROID,H5,WECHAT_MINI")
    private String clientType;

    @ApiModelProperty("轮播图片地址")
    private String imgUrl;

    @ApiModelProperty("轮播图点击后跳转的URL")
    private String jumpUrl;

    @ApiModelProperty("轮播图顺序(大<->小) 最大的在最前面")
    private Byte sort;

    @ApiModelProperty("开始展示时间(可在指定时间后开始展示)")
    private Date startTime;

    @ApiModelProperty("取消展示的时间(只在某个时间段展示)")
    private Date endTime;

    @ApiModelProperty("是否可点击 0:否 1:可以")
    private Boolean click;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("删除状态 0:未删除 1已删除")
    private Boolean deleted;

}