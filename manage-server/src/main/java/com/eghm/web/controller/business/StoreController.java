package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.*;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛 2024/6/30
 */
@RestController
@Api(tags = "店铺信息")
@AllArgsConstructor
@RequestMapping(value = "/manage/store", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoreController {

    private final ScenicService scenicService;

    private final HomestayService homestayService;

    private final ItemStoreService itemStoreService;

    private final TravelAgencyService travelAgencyService;

    private final RestaurantService restaurantService;

    private final VenueService venueService;

    @GetMapping("/storeList")
    @ApiOperation("店铺列表(不分页)")
    public RespBody<List<BaseStoreResponse>> storeList(BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        request.setLimit(false);
        List<BaseStoreResponse> responseList = Lists.newArrayList();
        Page<BaseStoreResponse> storePage = scenicService.getStorePage(request);
        responseList.addAll(storePage.getRecords());
        storePage = homestayService.getStorePage(request);
        responseList.addAll(storePage.getRecords());
        storePage = itemStoreService.getStorePage(request);
        responseList.addAll(storePage.getRecords());
        storePage = travelAgencyService.getStorePage(request);
        responseList.addAll(storePage.getRecords());
        storePage = restaurantService.getStorePage(request);
        responseList.addAll(storePage.getRecords());
        storePage = venueService.getStorePage(request);
        responseList.addAll(storePage.getRecords());
        return RespBody.success(responseList);
    }

}
