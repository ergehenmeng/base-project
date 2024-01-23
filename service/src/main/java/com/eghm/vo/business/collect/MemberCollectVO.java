package com.eghm.vo.business.collect;

import com.eghm.enums.ref.CollectType;
import com.eghm.vo.business.homestay.HomestayVO;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.vo.business.item.store.ItemStoreVO;
import com.eghm.vo.business.line.LineVO;
import com.eghm.vo.business.line.TravelAgencyVO;
import com.eghm.vo.business.news.NewsVO;
import com.eghm.vo.business.restaurant.RestaurantVO;
import com.eghm.vo.business.scenic.ScenicVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
public class MemberCollectVO {

    @ApiModelProperty(value = "收藏id")
    private Long collectId;

    @ApiModelProperty(value = "收藏对象类型")
    private CollectType collectType;

    @ApiModelProperty("景区信息")
    private ScenicVO scenic;

    @ApiModelProperty("商品信息")
    private ItemVO item;

    @ApiModelProperty("民宿店铺")
    private HomestayVO homestay;

    @ApiModelProperty("民宿信息")
    private RestaurantVO restaurant;

    @ApiModelProperty("旅行社信息")
    private TravelAgencyVO travelAgency;

    @ApiModelProperty("线路信息")
    private LineVO line;

    @ApiModelProperty("资讯")
    private NewsVO news;

    @ApiModelProperty("零售门店")
    private ItemStoreVO itemStore;
}
