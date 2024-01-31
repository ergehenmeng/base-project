package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 场地信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("venue_site")
public class VenueSite extends BaseEntity {

    @ApiModelProperty(value = "场地名称")
    private String title;

    @ApiModelProperty(value = "所属场馆")
    private Long venueId;

    @ApiModelProperty(value = "所属商户")
    private Long merchantId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private Boolean state;

    @ApiModelProperty(value = "场地详细信息")
    private String introduce;

}
