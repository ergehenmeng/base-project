package com.eghm.web.controller.business;

import com.eghm.dto.business.purchase.LimitPurchaseQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.VisitType;
import com.eghm.service.business.LimitPurchaseItemService;
import com.eghm.vo.business.limit.LimitItemVO;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/28
 */

@RestController
@Api(tags = "限时购商品")
@AllArgsConstructor
@RequestMapping(value = "/webapp/item/limit", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemLimitController {

    private final LimitPurchaseItemService limitPurchaseItemService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    @VisitRecord(VisitType.PRODUCT_LIST)
    public RespBody<List<LimitItemVO>> listPage(LimitPurchaseQueryDTO dto) {
        List<LimitItemVO> voList = limitPurchaseItemService.getByPage(dto);
        return RespBody.success(voList);
    }

}
