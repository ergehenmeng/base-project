package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
public class Banner extends BaseEntity {

    @ApiModelProperty("标题")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;

    @ApiModelProperty("轮播图类型:由sys_dict的banner_type维护(不同模块的轮播均在该表中维护)")
    private Integer bannerType;

    @ApiModelProperty("客户端类型: PC,IOS,ANDROID,H5,WECHAT_MINI")
    private String clientType;

    @ApiModelProperty("轮播图片地址")
    private String imgUrl;

    @ApiModelProperty("轮播图点击后跳转的URL")
    private String jumpUrl;

    @ApiModelProperty("轮播图顺序(大<->小) 最大的在最前面")
    private Integer sort;

    @ApiModelProperty("开始展示时间(可在指定时间后开始展示)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty("取消展示的时间(只在某个时间段展示)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty("是否可点击 0:否 1:可以")
    private Boolean click;

    @ApiModelProperty("备注信息")
    private String remark;

}