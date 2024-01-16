package com.eghm.dto.business.merchant;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MerchantQueryRequest extends PagingQuery {

    @ApiModelProperty("商户类型 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路")
    @OptionInt(value = {1, 2, 4, 8, 16}, message = "商户类型错误", required = false)
    private Integer type;
    
    @ApiModelProperty("商户状态 0:锁定 1:正常 2:销户")
    @OptionInt(value = {0, 1, 2}, message = "商户状态错误", required = false)
    private Integer state;

    @ApiModelProperty("企业类型 1:个体工商户 2:企业")
    private Integer enterpriseType;
}
