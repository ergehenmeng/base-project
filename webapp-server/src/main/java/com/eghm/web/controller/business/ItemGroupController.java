package com.eghm.web.controller.business;

import com.eghm.dto.business.group.GroupBookingQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.VisitType;
import com.eghm.service.business.GroupBookingService;
import com.eghm.service.business.ItemGroupOrderService;
import com.eghm.vo.business.group.GroupItemVO;
import com.eghm.vo.business.group.GroupOrderDetailVO;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@RestController
@Tag(name="拼团商品")
@AllArgsConstructor
@RequestMapping(value = "/webapp/item/group", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemGroupController {

    private final GroupBookingService groupBookingService;

    private final ItemGroupOrderService itemGroupOrderService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    @VisitRecord(VisitType.PRODUCT_LIST)
    public RespBody<List<GroupItemVO>> listPage(GroupBookingQueryDTO dto) {
        List<GroupItemVO> voList = groupBookingService.listPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @Operation(summary = "拼团详情")
    @VisitRecord(VisitType.PRODUCT_DETAIL)
    @Parameter(name = "bookingNo", description = "拼团订单号", required = true)
    public RespBody<GroupOrderDetailVO> detail(@RequestParam("bookingNo") String bookingNo) {
        GroupOrderDetailVO detail = itemGroupOrderService.getGroupDetail(bookingNo);
        return RespBody.success(detail);
    }
}
