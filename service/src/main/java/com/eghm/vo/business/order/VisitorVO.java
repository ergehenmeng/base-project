package com.eghm.vo.business.order;

import com.eghm.enums.ref.VisitorState;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/6/27
 */
@Data
public class VisitorVO {

    @ApiModelProperty("id主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("状态 0: 初始化(待支付) 1: 已支付,待使用 2:已使用 3:退款中 4:已退款")
    private VisitorState state;

    @ApiModelProperty(value = "游客姓名")
    private String memberName;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;
}
