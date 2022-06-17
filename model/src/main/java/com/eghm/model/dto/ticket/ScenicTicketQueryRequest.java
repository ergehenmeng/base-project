package com.eghm.model.dto.ticket;

import com.eghm.model.dto.ext.PagingQuery;
import com.eghm.model.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wyb 2022/6/17 19:08
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ScenicTicketQueryRequest extends PagingQuery {

    @ApiModelProperty("门票所属商户")
    private Long merchantId;

    @ApiModelProperty(value = "门票种类 1: 成人票 2: 老人票 3:儿童票")
    @OptionInt(value = {1, 2, 3}, message = "门票种类错误", required = false)
    private Integer category;

    @ApiModelProperty(value = "上下架状态 0:未上架 1:已上架")
    @OptionInt(value = {0, 1}, message = "上下架状态错误", required = false)
    private Integer state;

}
