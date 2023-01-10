package com.eghm.model.vo.business.homestay.room.config;

import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/10
 */
@Data
public class HomestayMinPriceVO {

    /**
     * 民宿id
     */
    private Long homestayId;

    /**
     * 民宿最低价
     */
    private Integer minPrice;
}
