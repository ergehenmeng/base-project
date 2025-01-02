package com.eghm.vo.operate.banner;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 轮播图管理
 *
 * @author 二哥很猛
 */
@Data
public class BannerResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "轮播图类型:由sys_dict的banner_type维护(不同模块的轮播均在该表中维护)")
    private Integer bannerType;

    @Schema(description = "客户端类型: PC,IOS,ANDROID,H5,WECHAT_MINI")
    private String clientType;

    @Schema(description = "状态 true:启用 false:禁用")
    private Boolean state;

    @Schema(description = "轮播图片地址")
    private String imgUrl;

    @Schema(description = "轮播图点击后跳转的URL")
    private String jumpUrl;

    @Schema(description = "轮播图顺序(大<->小) 最大的在最前面")
    private Integer sort;

    @Schema(description = "开始展示时间(可在指定时间后开始展示)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Schema(description = "取消展示的时间(只在某个时间段展示)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Schema(description = "是否可点击 0:否 1:可以")
    private Boolean click;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}