package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.OrderVerifyDTO;
import com.eghm.dto.business.verify.VerifyLogQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.state.machine.access.AccessHandler;
import com.eghm.state.machine.context.OrderVerifyContext;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.order.OrderScanVO;
import com.eghm.vo.business.verify.VerifyLogResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eghm.enums.ErrorCode.VERIFY_NO_ERROR;
import static com.eghm.enums.ErrorCode.VERIFY_TYPE_ERROR;

/**
 * @author wyb
 * @since 2023/6/13
 */
@Slf4j
@RestController
@Tag(name="商户核销")
@AllArgsConstructor
@RequestMapping(value = "/manage/verify", produces = MediaType.APPLICATION_JSON_VALUE)
public class VerifyController {

    private final OrderService orderService;

    private final CommonService commonService;

    private final VerifyLogService verifyLogService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<VerifyLogResponse>> listPage(@ParameterObject VerifyLogQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VerifyLogResponse> roomPage = verifyLogService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @GetMapping("/scan")
    @Operation(summary = "查询扫码结果")
    @Parameter(name = "verifyNo", description = "核销码(二选一)")
    @Parameter(name = "orderNo", description = "订单号(二选一)")
    public RespBody<OrderScanVO> scan(@RequestParam(value = "verifyNo", required = false) String verifyNo, @RequestParam(value = "orderNo", required = false) String orderNo) {
        if (verifyNo == null && orderNo == null) {
            log.warn("核销码或订单号不能为空");
            return RespBody.error(VERIFY_NO_ERROR);
        }
        OrderScanVO vo = orderService.getScanResult(verifyNo, orderNo, SecurityHolder.getMerchantId());
        return RespBody.success(vo);
    }

    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "核销")
    public RespBody<Integer> verify(@RequestBody @Validated OrderVerifyDTO dto) {
        ProductType productType = ProductType.prefix(dto.getOrderNo());
        AccessHandler accessHandler = commonService.getHandler(productType, AccessHandler.class);
        if (accessHandler == null) {
            throw new BusinessException(VERIFY_TYPE_ERROR);
        }
        OrderVerifyContext context = new OrderVerifyContext();
        context.setCombineId(dto.getCombineId());
        context.setOrderNo(dto.getOrderNo());
        context.setUserId(SecurityHolder.getUserId());
        context.setMerchantId(SecurityHolder.getMerchantId());
        context.setRemark(dto.getRemark());
        context.setIds(dto.getIds());
        accessHandler.verifyOrder(context);
        return RespBody.success(context.getVerifyNum());
    }

    @GetMapping("/export")
    @Operation(summary = "民宿导出")
    public void export(HttpServletResponse response, @ParameterObject VerifyLogQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<VerifyLogResponse> byPage = verifyLogService.getList(request);
        EasyExcelUtil.export(response, "核销列表", byPage, VerifyLogResponse.class);
    }
}
