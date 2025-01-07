package com.eghm.vo.business.collect;

import com.eghm.enums.ref.CollectType;
import com.eghm.vo.business.homestay.HomestayVO;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.vo.business.item.store.ItemStoreVO;
import com.eghm.vo.business.line.LineVO;
import com.eghm.vo.business.line.TravelVO;
import com.eghm.vo.business.news.NewsVO;
import com.eghm.vo.business.restaurant.RestaurantVO;
import com.eghm.vo.business.scenic.ScenicVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberCollectVO {

    @Schema(description = "收藏id")
    private Long collectId;

    @Schema(description = "收藏对象类型(1:景区 2:民宿 3:零售门店 4:零售商品 5:线路商品 6:餐饮门店 7:资讯 8:旅行社)")
    private CollectType collectType;

    @Schema(description = "景区信息")
    private ScenicVO scenic;

    @Schema(description = "商品信息")
    private ItemVO item;

    @Schema(description = "民宿店铺")
    private HomestayVO homestay;

    @Schema(description = "民宿信息")
    private RestaurantVO restaurant;

    @Schema(description = "旅行社信息")
    private TravelVO travelAgency;

    @Schema(description = "线路信息")
    private LineVO line;

    @Schema(description = "资讯")
    private NewsVO news;

    @Schema(description = "零售门店")
    private ItemStoreVO itemStore;
}
