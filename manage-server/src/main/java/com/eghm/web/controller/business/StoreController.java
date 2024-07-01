package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.annotation.SkipPerm;
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

    @GetMapping("/productList")
    @ApiOperation("店铺列表(不分页)")
    @SkipPerm
    public RespBody<List<BaseStoreResponse>> productList(BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        request.setLimit(false);
        List<BaseStoreResponse> responseList = Lists.newArrayList();
        Page<BaseStoreResponse> scenicPage = scenicService.getStorePage(request);
        responseList.addAll(scenicPage.getRecords());
        Page<BaseStoreResponse> homestayPage = homestayService.getStorePage(request);
        responseList.addAll(homestayPage.getRecords());
        Page<BaseStoreResponse> listPage = itemStoreService.getStorePage(request);
        responseList.addAll(listPage.getRecords());
        Page<BaseStoreResponse> storePage = travelAgencyService.getStorePage(request);
        responseList.addAll(storePage.getRecords());
        Page<BaseStoreResponse> restaurantPage = restaurantService.getStorePage(request);
        responseList.addAll(restaurantPage.getRecords());
        Page<BaseStoreResponse> venuePage = venueService.getStorePage(request);
        responseList.addAll(venuePage.getRecords());
        return RespBody.success(responseList);
    }

}
