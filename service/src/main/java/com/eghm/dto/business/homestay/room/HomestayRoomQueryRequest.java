package com.eghm.dto.business.homestay.room;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.RefundType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2022/6/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayRoomQueryRequest extends PagingQuery {

    @ApiModelProperty("上架状态 0:待上架 1:已上架")
    private Integer state;

    @ApiModelProperty("退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty("民宿id")
    private Long homestayId;

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
