package com.eghm.vo.business.collect;

import com.eghm.enums.ref.CollectType;
import com.eghm.vo.business.news.NewsVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberCollectVO {

    @ApiModelProperty(value = "收藏id")
    private Long collectId;

    @ApiModelProperty(value = "收藏对象类型(1:景区 2:民宿 3:零售门店 4:零售商品 5:线路商品 6:餐饮门店 7:资讯 8:旅行社)")
    private CollectType collectType;

    @ApiModelProperty("资讯")
    private NewsVO news;

}
