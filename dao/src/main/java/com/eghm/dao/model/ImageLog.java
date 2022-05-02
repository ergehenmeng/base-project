package com.eghm.dao.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 图片上传记录表
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ImageLog extends BaseEntity {

    @ApiModelProperty("文件名称")
    private String title;

    @ApiModelProperty("图片分类 数据字典image_log_type")
    private Byte classify;

    @ApiModelProperty("文件存放地址")
    private String path;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("备注信息")
    private String remark;

}