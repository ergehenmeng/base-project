package com.eghm.dto.business.scenic.ticket;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛 2022/6/17 19:08
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ScenicTicketQueryRequest extends PagingQuery {

    @ApiModelProperty("景区id")
    private Long scenicId;

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @ApiModelProperty(value = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合")
    @OptionInt(value = {1, 2, 3}, message = "门票种类错误", required = false)
    private Integer category;

    @ApiModelProperty(value = "上下架状态 0:未上架 1:已上架 2:强制下架")
    @OptionInt(value = {0, 1, 2}, message = "上下架状态错误", required = false)
    private Integer state;

}
