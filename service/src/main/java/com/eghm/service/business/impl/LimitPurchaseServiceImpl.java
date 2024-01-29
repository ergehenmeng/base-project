package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.limit.LimitPurchaseQueryRequest;
import com.eghm.dto.business.purchase.LimitPurchaseAddRequest;
import com.eghm.dto.business.purchase.LimitPurchaseEditRequest;
import com.eghm.dto.business.purchase.PurchaseItemRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.LimitPurchaseMapper;
import com.eghm.model.LimitPurchase;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.ItemService;
import com.eghm.service.business.LimitPurchaseItemService;
import com.eghm.service.business.LimitPurchaseService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.limit.LimitPurchaseResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 限时购活动表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */

@Slf4j
@AllArgsConstructor
@Service("limitPurchaseService")
public class LimitPurchaseServiceImpl implements LimitPurchaseService {

    private final ItemService itemService;

    private final CommonService commonService;

    private final LimitPurchaseMapper limitPurchaseMapper;

    private final LimitPurchaseItemService limitPurchaseItemService;

    @Override
    public Page<LimitPurchaseResponse> getByPage(LimitPurchaseQueryRequest request) {
        return limitPurchaseMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(LimitPurchaseAddRequest request) {
        this.checkTime(request.getStartTime(), request.getEndTime());
        this.redoTitle(request.getTitle(), null);
        LimitPurchase purchase = DataUtil.copy(request, LimitPurchase.class);
        purchase.setCreateTime(LocalDateTime.now());
        limitPurchaseMapper.insert(purchase);

        List<Long> itemIds = request.getItemList().stream().map(PurchaseItemRequest::getItemId).collect(Collectors.toList());
        itemService.updateLimitPurchase(itemIds, purchase.getId());
        limitPurchaseItemService.insertOrUpdate(request.getItemList(), purchase);
    }

    @Override
    public void update(LimitPurchaseEditRequest request) {
        this.checkTime(request.getStartTime(), request.getEndTime());
        this.redoTitle(request.getTitle(), request.getId());

        LimitPurchase purchase = limitPurchaseMapper.selectById(request.getId());
        // 校验活动是否属于该商户
        commonService.checkIllegal(purchase.getMerchantId());
        if (!purchase.getStartTime().isAfter(LocalDateTime.now())) {
            throw new BusinessException(ErrorCode.ACTIVITY_NOT_EDIT);
        }
        List<Long> itemIds = request.getItemList().stream().map(PurchaseItemRequest::getItemId).collect(Collectors.toList());
        itemService.updateLimitPurchase(itemIds, purchase.getId());

        LimitPurchase limitPurchase = DataUtil.copy(request, LimitPurchase.class);
        limitPurchaseMapper.updateById(limitPurchase);
        limitPurchaseItemService.insertOrUpdate(request.getItemList(), purchase);
    }

    @Override
    public void delete(Long id) {
        LimitPurchase purchase = limitPurchaseMapper.selectById(id);
        if (purchase == null) {
            return;
        }
        LambdaUpdateWrapper<LimitPurchase> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LimitPurchase::getId, id);
        wrapper.eq(LimitPurchase::getMerchantId, SecurityHolder.getMerchantId());
        limitPurchaseMapper.delete(wrapper);
        limitPurchaseItemService.delete(id);
        itemService.releasePurchase(id);
    }

    /**
     * 校验时间
     *
     * @param startTime 开始日期
     */
    private void checkTime(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        if (startTime.isAfter(now)) {
            throw new BusinessException(ErrorCode.LIMIT_GT_TIME);
        }
        long between = ChronoUnit.MONTHS.between(startTime, endTime.minusSeconds(1));
        if (between > 0) {
            log.warn("限时购活动时间跨度不能大于一个月 [{}] [{}]", startTime, endTime);
            throw new BusinessException(ErrorCode.LIMIT_GT_MONTH);
        }
    }

    /**
     * 重复标题校验
     *
     * @param title 活动标题
     * @param id id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<LimitPurchase> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LimitPurchase::getTitle, title);
        wrapper.ne(id != null, LimitPurchase::getId, id);
        Long count = limitPurchaseMapper.selectCount(wrapper);
        if (count > 0 ) {
            log.error("限时购活动名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.REDO_TITLE_LIMIT);
        }
    }
}
