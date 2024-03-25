package com.eghm.web.controller.business;

import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.line.LineOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.LineOrderService;
import com.eghm.vo.business.order.line.LineOrderDetailVO;
import com.eghm.vo.business.order.line.LineOrderSnapshotDetailVO;
import com.eghm.vo.business.order.line.LineOrderVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@AccessToken
@RestController
@Api(tags = "线路订单")
@AllArgsConstructor
@RequestMapping(value = "/webapp/line/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class LineOrderController {

    private final LineOrderService lineOrderService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<List<LineOrderVO>> listPage(@Validated LineOrderQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<LineOrderVO> voList = lineOrderService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<LineOrderDetailVO> detail(@Validated OrderDTO dto) {
        LineOrderDetailVO detail = lineOrderService.getDetail(dto.getOrderNo(), ApiHolder.getMemberId());
        return RespBody.success(detail);
    }

    @GetMapping("/snapshot")
    @ApiOperation("订单快照")
    public RespBody<LineOrderSnapshotDetailVO> snapshot(@Validated OrderDTO dto) {
        LineOrderSnapshotDetailVO detail = lineOrderService.snapshotDetail(dto.getOrderNo(), ApiHolder.getMemberId());
        return RespBody.success(detail);
    }

}
