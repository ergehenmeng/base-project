package com.eghm.vo.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class ImageLogResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "文件名称")
    private String title;

    @Schema(description = "图片分类 数据字典image_type")
    private Integer imageType;

    @Schema(description = "文件存放地址")
    private String path;

    @Schema(description = "文件大小")
    private Long size;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
