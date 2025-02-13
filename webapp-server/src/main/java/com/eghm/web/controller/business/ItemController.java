package com.eghm.web.controller.business;

import cn.hutool.core.collection.CollUtil;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.item.ItemQueryDTO;
import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.VisitType;
import com.eghm.service.business.ItemService;
import com.eghm.vo.business.item.ItemDetailVO;
import com.eghm.vo.business.item.ItemSkuDetailVO;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.vo.business.item.express.TotalExpressVO;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/23
 */

@RestController
@Tag(name="商品信息")
@AllArgsConstructor
@RequestMapping(value = "/webapp/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    @VisitRecord(VisitType.PRODUCT_LIST)
    public RespBody<List<ItemVO>> listPage(ItemQueryDTO dto) {
        List<ItemVO> byPage = itemService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    @VisitRecord(VisitType.PRODUCT_DETAIL)
    public RespBody<ItemDetailVO> detail(@Validated IdDTO dto) {
        ItemDetailVO detail = itemService.detailById(dto.getId());
        return RespBody.success(detail);
    }

    @GetMapping("/skuDetail")
    @Operation(summary = "规格详情")
    public RespBody<ItemSkuDetailVO> skuDetail(@Validated IdDTO dto) {
        ItemSkuDetailVO detail = itemService.skuDetailById(dto.getId());
        return RespBody.success(detail);
    }

    @GetMapping("/recommend")
    @Operation(summary = "商品推荐列表")
    public RespBody<List<ItemVO>> recommend() {
        List<ItemVO> recommend = itemService.getRecommend();
        return RespBody.success(recommend);
    }

    @PostMapping(value = "/calcExpress", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "计算快递费")
    public RespBody<TotalExpressVO> calcExpress(@RequestBody @Validated List<ExpressFeeCalcDTO> dtoList) {
        if (CollUtil.isEmpty(dtoList)) {
            return RespBody.error(ErrorCode.ORDER_ITEM_NULL);
        }
        TotalExpressVO expressFee = itemService.calcExpressFee(dtoList);
        return RespBody.success(expressFee);
    }

}
