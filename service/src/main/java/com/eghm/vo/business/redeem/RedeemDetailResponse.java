package com.eghm.vo.business.redeem;

import com.eghm.convertor.CentToYuanOmitSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/7/1
 */

@Data
public class RedeemDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "cd名称")
    private String title;

    @Schema(description = "有效开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Schema(description = "有效截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Schema(description = "金额")
    @JsonSerialize(using = CentToYuanOmitSerializer.class)
    private Integer amount;

    @Schema(description = "发放数量")
    private Integer num;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "使用范围")
    private List<Long> storeIds;
}
