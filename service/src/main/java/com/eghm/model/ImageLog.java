package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图片上传记录表
 * @author 二哥很猛
 */
@Data
@TableName("image_log")
@EqualsAndHashCode(callSuper = true)
public class ImageLog extends BaseEntity {

    @ApiModelProperty("文件名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;

    @ApiModelProperty("图片分类 数据字典image_log_type")
    private Integer classify;

    @ApiModelProperty("文件存放地址")
    private String path;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("备注信息")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String remark;

}