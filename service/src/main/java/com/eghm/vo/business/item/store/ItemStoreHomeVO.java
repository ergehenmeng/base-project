package com.eghm.vo.business.item.store;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eghm.vo.business.item.ItemListVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/13
 */
@Data
public class ItemStoreHomeVO {

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

    @ApiModelProperty(value = "营业时间")
    private String openTime;

    @ApiModelProperty(value = "经度")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家电话")
    private String telephone;

    @ApiModelProperty(value = "商家介绍")
    private String introduce;

    @ApiModelProperty("推荐商品列表")
    private List<ItemListVO> itemList;

}
