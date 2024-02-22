package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.OrderVerifyDTO;
import com.eghm.dto.business.verify.VerifyLogQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.access.AccessHandler;
import com.eghm.service.business.handler.context.OrderVerifyContext;
import com.eghm.vo.business.order.OrderScanVO;
import com.eghm.vo.business.verify.VerifyLogResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.eghm.enums.ErrorCode.VERIFY_ORDER_ERROR;
import static com.eghm.enums.ErrorCode.VERIFY_TYPE_ERROR;

/**
 * @author wyb
 * @since 2023/6/13
 */
@RestController
@Api(tags = "核销")
@AllArgsConstructor
@RequestMapping("/manage/verify")
public class VerifyController {

    private final VerifyLogService verifyLogService;

    private final CommonService commonService;

    private final OrderService orderService;

    @GetMapping("/listPage")
    @ApiOperation("核销记录列表")
    public RespBody<PageData<VerifyLogResponse>> listPage(VerifyLogQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VerifyLogResponse> roomPage = verifyLogService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @GetMapping("/scan")
    @ApiOperation("查询扫码结果")
    @ApiImplicitParam(name = "verifyNo", value = "核销码", required = true)
    public RespBody<OrderScanVO> scan(@RequestParam("verifyNo") String verifyNo) {
        verifyNo = orderService.decryptVerifyNo(verifyNo);
        ProductType productType = ProductType.prefix(verifyNo);
        if (productType == ProductType.HOMESTAY || productType == ProductType.LINE || productType == ProductType.TICKET || productType == ProductType.RESTAURANT) {
            OrderScanVO vo = orderService.getScanResult(verifyNo, SecurityHolder.getMerchantId());
            return RespBody.success(vo);
        } else {
            return RespBody.error(VERIFY_ORDER_ERROR);
        }
    }

    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("核销")
    public RespBody<Integer> verify(@RequestBody @Validated OrderVerifyDTO dto) {
        ProductType productType = ProductType.prefix(dto.getOrderNo());
        AccessHandler accessHandler = commonService.getHandler(productType, AccessHandler.class);
        if (accessHandler == null) {
            throw new BusinessException(VERIFY_TYPE_ERROR);
        }

        OrderVerifyContext context = new OrderVerifyContext();
        context.setOrderNo(dto.getOrderNo());
        context.setUserId(SecurityHolder.getUserId());
        context.setMerchantId(SecurityHolder.getMerchantId());
        context.setRemark(dto.getRemark());
        context.setVisitorList(dto.getVisitorList());

        accessHandler.verifyOrder(context);

        return RespBody.success(context.getVerifyNum());
    }
}
