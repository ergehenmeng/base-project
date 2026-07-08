package com.eghm.operate.model;

import com.eghm.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图片上传记录表
 *
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageLog extends BaseEntity {

    /** 文件名称 */
    private String title;

    /** 图片分类 数据字典image_type */
    private Integer imageType;

    /** 文件存放地址 */
    private String path;

    /** 文件大小 */
    private Long size;

    /** 备注信息 */
    private String remark;

}
