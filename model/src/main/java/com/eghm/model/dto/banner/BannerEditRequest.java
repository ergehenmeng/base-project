package com.eghm.model.dto.banner;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/8/22 19:58
 */
@Data
public class BannerEditRequest implements Serializable {

    private static final long serialVersionUID = -6001565267553951736L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 轮播图类型
     */
    private Byte classify;

    /**
     * 客户端类型 PC APP
     */
    private String clientType;

    /**
     * 轮播图片地址
     */
    private String imgUrl;

    /**
     * 轮播图点击后跳转的URL
     */
    private String turnUrl;

    /**
     * 轮播图顺序(大<->小) 最大的在最前面
     */
    private Byte sort;

    /**
     * 开始展示时间(可在指定时间后开始展示)
     */
    private Date startTime;

    /**
     * 取消展示的时间(只在某个时间段展示)
     */
    private Date endTime;

    /**
     * 是否可点击 0:否 1:可以
     */
    private Boolean click;

    /**
     * 备注信息
     */
    private String remark;
}
