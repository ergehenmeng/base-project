package com.eghm.vo.business.redeem;

import com.eghm.convertor.CentToYuanOmitSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/7/1
 */

@Data
public class RedeemDetailResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "cd名称")
    private String title;

    @ApiModelProperty(value = "有效开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "有效截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @ApiModelProperty("金额")
    @JsonSerialize(using = CentToYuanOmitSerializer.class)
    private Integer amount;

    @ApiModelProperty(value = "发放数量")
    private Integer num;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("使用范围")
    private List<Long> storeIds;
}
