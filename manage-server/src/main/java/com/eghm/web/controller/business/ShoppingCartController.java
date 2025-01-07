package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.shopping.ShoppingCartQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ShoppingCartService;
import com.eghm.vo.business.shopping.ShoppingCartResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@RestController
@Tag(name="购物车管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/shopping/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<ShoppingCartResponse>> listPage(ShoppingCartQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<ShoppingCartResponse> byPage = shoppingCartService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

}
