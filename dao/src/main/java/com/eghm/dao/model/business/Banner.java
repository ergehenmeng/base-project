package com.eghm.dao.model.business;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 轮播图管理
 * @author 二哥很猛
 */
@Data
public class Banner implements Serializable {

    private static final long serialVersionUID = 6177166783814386108L;
    /**
     * <br>
     *     主键
     * 表 : banner<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 标题
     * 表 : banner<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 轮播图类型:由system_dict的banner_type维护(不同模块的轮播均在该表中维护)<br>
     * 表 : banner<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    /**
     * 客户端类型 PC IOS,ANDROID,H5<br>
     * 表 : banner<br>
     * 对应字段 : client_type<br>
     */
    private String clientType;

    /**
     * 轮播图片地址<br>
     * 表 : banner<br>
     * 对应字段 : img_url<br>
     */
    private String imgUrl;

    /**
     * 轮播图点击后跳转的URL<br>
     * 表 : banner<br>
     * 对应字段 : turn_url<br>
     */
    private String turnUrl;

    /**
     * 轮播图顺序(大<->小) 最大的在最前面<br>
     * 表 : banner<br>
     * 对应字段 : index<br>
     */
    private Byte sort;

    /**
     * 开始展示时间(可在指定时间后开始展示)<br>
     * 表 : banner<br>
     * 对应字段 : start_time<br>
     */
    private Date startTime;

    /**
     * 取消展示的时间(只在某个时间段展示)<br>
     * 表 : banner<br>
     * 对应字段 : end_time<br>
     */
    private Date endTime;

    /**
     * 是否可点击 0:否 1:可以<br>
     * 表 : banner<br>
     * 对应字段 : click<br>
     */
    private Boolean click;

    /**
     * 备注信息<br>
     * 表 : banner<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    /**
     * 添加时间<br>
     * 表 : banner<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : banner<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 删除状态 0:未删除 1已删除<br>
     * 表 : banner<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 数据字典解析字段
     */
    private String classifyName;
}