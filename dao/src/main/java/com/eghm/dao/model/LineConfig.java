package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 线路商品配置表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("line_config")
@ApiModel(value="LineConfig对象", description="线路商品配置表")
public class LineConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "线路商品id")
    private Long lineId;

    @ApiModelProperty(value = "配置日期")
    private LocalDate configDate;

    @ApiModelProperty(value = "总库存")
    private Integer stock;

    @ApiModelProperty(value = "销售价格")
    private Integer salePrice;

    @ApiModelProperty(value = "销售数量")
    private Integer saleNum;

}
