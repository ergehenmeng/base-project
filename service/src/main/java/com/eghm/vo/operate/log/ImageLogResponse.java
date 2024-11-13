package com.eghm.vo.operate.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class ImageLogResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("文件名称")
    private String title;

    @ApiModelProperty("图片分类 数据字典image_type")
    private Integer imageType;

    @ApiModelProperty("文件存放地址")
    private String path;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
