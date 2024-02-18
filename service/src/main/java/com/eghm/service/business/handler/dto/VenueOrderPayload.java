package com.eghm.service.business.handler.dto;

import com.eghm.model.*;
import lombok.Data;

import java.util.List;

/**
 * @author wyb
 * @since 2024/2/3
 */
@Data
public class VenueOrderPayload {

    /**
     * 场馆信息
     */
    private Venue venue;

    /**
     * 场地信息
     */
    private VenueSite venueSite;

    /**
     * 场地价格信息
     */
    private List<VenueSitePrice> priceList;

    /**
     * cdKey金额
     */
    private Integer cdKeyAmount;
}
