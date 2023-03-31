package com.eghm.dto.business.homestay;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/1/9
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayQueryDTO extends PagingQuery {

    @ApiModelProperty("经度")
    private BigDecimal longitude;

    @ApiModelProperty("纬度")
    private BigDecimal latitude;

    @ApiModelProperty("是否按距离排序")
    private Boolean sortByDistance = false;

    @ApiModelProperty("星级 5:五星级 4:四星级 3:三星级 0: 其他, 空查询全部")
    private Integer level;
}
