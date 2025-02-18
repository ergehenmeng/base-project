package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 图片上传记录表
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ImageLog extends BaseEntity {

    /**
     * 文件名称<br>
     * 表 : image_log<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 图片分类 数据字典image_log_type<br>
     * 表 : image_log<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    /**
     * 文件存放地址<br>
     * 表 : image_log<br>
     * 对应字段 : path<br>
     */
    private String path;

    /**
     * 文件大小<br>
     * 表 : image_log<br>
     * 对应字段 : size<br>
     */
    private Long size;

    /**
     * 备注信息<br>
     * 表 : image_log<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    /**
     * 添加时间<br>
     * 表 : image_log<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : image_log<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 删除状态 0:未删除 1:已删除<br>
     * 表 : image_log<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 类型名称 通过数据字典转换
     */
    private String classifyName;
}