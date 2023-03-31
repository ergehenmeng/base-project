package com.eghm.dto.business.scenic;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @date 2022/7/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScenicQueryDTO extends PagingQuery {

    @ApiModelProperty("经度")
    private BigDecimal longitude;

    @ApiModelProperty("纬度")
    private BigDecimal latitude;

}
