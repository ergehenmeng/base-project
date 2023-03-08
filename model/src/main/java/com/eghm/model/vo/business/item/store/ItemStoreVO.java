package com.eghm.model.vo.business.item.store;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/29
 */

@Data
public class ItemStoreVO {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("店铺名称")
    private String title;

    @ApiModelProperty(value = "店铺logo")
    private String logoUrl;

    @ApiModelProperty("封面图")
    private String coverUrl;

    @ApiModelProperty(value = "商家电话")
    private String telephone;

    @ApiModelProperty(value = "商家介绍")
    private String introduce;
}
