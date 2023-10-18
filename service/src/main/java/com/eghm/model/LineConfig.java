package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 线路商品配置表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-26
 */
@Data
@TableName("line_config")
public class LineConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "线路商品id")
    private Long lineId;

    @ApiModelProperty("状态 false:不可预定 true:可预定")
    private Boolean state;

    @ApiModelProperty(value = "配置日期")
    private LocalDate configDate;

    @ApiModelProperty(value = "总库存")
    private Integer stock;

    @ApiModelProperty(value = "销售价格")
    private Integer salePrice;

    @ApiModelProperty(value = "划线价格")
    private Integer linePrice;

    @ApiModelProperty(value = "销售数量")
    private Integer saleNum;

    @ApiModelProperty("添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
