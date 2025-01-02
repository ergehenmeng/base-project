package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 后台系统操作日志
 *
 * @author 二哥很猛
 */
@Data
@TableName("manage_log")
public class ManageLog {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "请求地址")
    private String url;

    @Schema(description = "操作人")
    private Long userId;

    @Schema(description = "请求参数")
    private String request;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "访问ip")
    private String ip;

    @Schema(description = "业务耗时")
    private Long businessTime;

    @Schema(description = "响应参数")
    private String response;
}