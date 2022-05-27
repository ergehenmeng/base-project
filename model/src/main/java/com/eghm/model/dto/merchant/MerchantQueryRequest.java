package com.eghm.model.dto.merchant;

import com.eghm.model.dto.ext.PagingQuery;
import com.eghm.model.validation.annotation.OptionInt;
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
    
    @ApiModelProperty("商户名称")
    private String merchantName;
    
    @ApiModelProperty("商户类型 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路")
    @OptionInt(value = {1, 2, 4, 8, 16}, message = "商户类型错误")
    private Integer type;
    
    @ApiModelProperty("商户状态 0:锁定 1:正常 2:销户")
    @OptionInt(value = {0, 1, 2}, message = "商户状态错误")
    private Integer state;
    
}
