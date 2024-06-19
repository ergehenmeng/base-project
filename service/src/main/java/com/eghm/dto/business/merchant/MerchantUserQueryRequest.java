package com.eghm.dto.business.merchant;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 殿小二
 * @since 2022/5/27
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MerchantUserQueryRequest extends PagingQuery {

    @ApiModelProperty("锁定状态 0:锁定 1:正常")
    @OptionInt(value = {0, 1}, required = false, message = "用户状态非法")
    private Integer state;

    @Assign
    @ApiModelProperty(value = "商户id", hidden = true)
    private Long merchantId;
}
