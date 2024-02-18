package com.eghm.service.business.handler.dto;

import com.eghm.model.Homestay;
import com.eghm.model.HomestayRoom;
import com.eghm.model.HomestayRoomConfig;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/22
 */
@Data
public class HomestayOrderPayload {

    /**
     * 民宿信息
     */
    private Homestay homestay;

    /**
     * 房型信息
     */
    private HomestayRoom homestayRoom;

    /**
     * 房型配置信息
     */
    private List<HomestayRoomConfig> configList;

    /**
     * 兑换码金额
     */
    private Integer cdKeyAmount;

    /**
     * 优惠券金额
     */
    private Integer couponAmount;
}
