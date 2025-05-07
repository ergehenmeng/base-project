
package com.eghm.vo.business.restaurant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 餐饮标签
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-09
 */
@Data
public class VoucherTagVO {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "标签名称")
    private String title;

}
