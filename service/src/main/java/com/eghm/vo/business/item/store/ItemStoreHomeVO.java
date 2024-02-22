package com.eghm.vo.business.item.store;

import com.eghm.convertor.NumberParseEncoder;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.vo.business.lottery.LotteryVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("店铺名称")
    private String title;

    @ApiModelProperty(value = "店铺logo")
    private String logoUrl;

    @ApiModelProperty("封面图")
    private String coverUrl;

    @ApiModelProperty("是否收藏")
    private Boolean collect;

    @ApiModelProperty("总收藏数量")
    @JsonSerialize(using = NumberParseEncoder.class)
    private Long commentNum;

    @ApiModelProperty(value = "营业时间")
    private String openTime;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家电话")
    private String telephone;

    @ApiModelProperty(value = "商家介绍")
    private String introduce;

    @ApiModelProperty("推荐商品列表")
    private List<ItemVO> itemList;

    @ApiModelProperty("抽奖列表")
    List<LotteryVO> lotteryList;
}
