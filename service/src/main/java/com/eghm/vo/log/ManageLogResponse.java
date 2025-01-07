package com.eghm.vo.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/12/15
 */
@Data
public class ManageLogResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "操作人姓名")
    private String nickName;

    @Schema(description = "操作人手机号")
    private String mobile;

    @Schema(description = "请求地址")
    private String url;

    @Schema(description = "请求参数")
    private String request;

    @Schema(description = "访问ip")
    private String ip;

    @Schema(description = "接口耗时(ms)")
    private Long businessTime;

    @Schema(description = "访问时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "响应结果")
    private String response;
}
