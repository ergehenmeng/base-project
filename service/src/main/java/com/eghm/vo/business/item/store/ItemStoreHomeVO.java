package com.eghm.vo.business.item.store;

import com.eghm.convertor.NumberParseSerializer;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.vo.business.lottery.LotteryVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/13
 */
@Data
public class ItemStoreHomeVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "店铺名称")
    private String title;

    @Schema(description = "店铺logo")
    private String logoUrl;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "是否收藏")
    private Boolean collect;

    @Schema(description = "总收藏数量")
    @JsonSerialize(using = NumberParseSerializer.class)
    private Long commentNum;

    @Schema(description = "营业时间")
    private String openTime;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "商家电话")
    private String telephone;

    @Schema(description = "商家介绍")
    private String introduce;

    @Schema(description = "推荐商品列表")
    private List<ItemVO> itemList;

    @Schema(description = "抽奖列表")
    List<LotteryVO> lotteryList;
}
