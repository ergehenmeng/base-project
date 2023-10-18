package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 快递模板区域
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("item_express_region")
public class ItemExpressRegion extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板id")
    private Long expressId;

    @ApiModelProperty(value = "首件或首重")
    private BigDecimal firstPart;

    @ApiModelProperty(value = "首件或首重的价格")
    private Integer firstPrice;

    @ApiModelProperty(value = "续重或续件")
    private BigDecimal nextPart;

    @ApiModelProperty(value = "续重或续件的单价")
    private Integer nextUnitPrice;

    @ApiModelProperty(value = "区域编号(逗号分隔)")
    private String regionCode;

    @ApiModelProperty(value = "区域名称(逗号分隔)")
    private String regionName;

}
