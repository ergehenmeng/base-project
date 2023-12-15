package com.eghm.vo.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/12/15
 */
@Data
public class ManageLogResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("操作人(关联字段)")
    private String userName;

    @ApiModelProperty("请求地址")
    private String url;

    @ApiModelProperty("请求参数")
    private String request;

    @ApiModelProperty("访问ip")
    private String ip;

    @ApiModelProperty("业务耗时")
    private Long businessTime;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
