package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
public class LineConfig {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "线路商品id")
    private Long lineId;

    @Schema(description = "状态 false:不可预定 true:可预定")
    private Boolean state;

    @Schema(description = "配置日期")
    private LocalDate configDate;

    @Schema(description = "总库存")
    private Integer stock;

    @Schema(description = "销售价格")
    private Integer salePrice;

    @Schema(description = "划线价格")
    private Integer linePrice;

    @Schema(description = "销售数量")
    private Integer saleNum;

    @Schema(description = "添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
