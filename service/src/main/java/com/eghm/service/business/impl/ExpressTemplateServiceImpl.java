package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.item.express.ItemExpressAddRequest;
import com.eghm.dto.business.item.express.ItemExpressEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ExpressTemplateMapper;
import com.eghm.mapper.ItemMapper;
import com.eghm.model.ExpressTemplate;
import com.eghm.model.Item;
import com.eghm.service.business.ExpressTemplateService;
import com.eghm.service.business.ItemExpressRegionService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.item.express.ExpressSelectResponse;
import com.eghm.vo.business.item.express.ItemExpressResponse;
import com.eghm.vo.business.item.express.ItemExpressVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.enums.ErrorCode.EXPRESS_NOT_FOUND;

/**
 * <p>
 * 快递模板表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
@Slf4j
@AllArgsConstructor
@Service("expressTemplateService")
public class ExpressTemplateServiceImpl implements ExpressTemplateService {

    private final ExpressTemplateMapper expressTemplateMapper;

    private final ItemExpressRegionService itemExpressRegionService;

    private final ItemMapper itemMapper;

    @Override
    public List<ItemExpressResponse> getList(Long merchantId) {
        return expressTemplateMapper.getList(merchantId);
    }

    @Override
    public List<ExpressSelectResponse> selectList(Long merchantId) {
        LambdaQueryWrapper<ExpressTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.select(ExpressTemplate::getId, ExpressTemplate::getTitle, ExpressTemplate::getChargeMode);
        wrapper.eq(ExpressTemplate::getMerchantId, merchantId);
        wrapper.eq(ExpressTemplate::getState, 1);
        wrapper.orderByDesc(ExpressTemplate::getId);
        List<ExpressTemplate> expressList = expressTemplateMapper.selectList(wrapper);
        return DataUtil.copy(expressList, ExpressSelectResponse.class);
    }

    @Override
    public void create(ItemExpressAddRequest request) {
        ExpressTemplate express = DataUtil.copy(request, ExpressTemplate.class);
        expressTemplateMapper.insert(express);
        itemExpressRegionService.createOrUpdate(express.getId(), request.getRegionList());
    }

    @Override
    public void update(ItemExpressEditRequest request) {
        ExpressTemplate selected = this.selectByIdRequired(request.getId());
        if (!selected.getMerchantId().equals(request.getMerchantId())) {
            log.error("查询快递模板不合法 [{}] [{}]", request.getId(), request.getMerchantId());
            throw new BusinessException(ErrorCode.EXPRESS_NULL);
        }
        if (!selected.getChargeMode().equals(request.getChargeMode()) && this.itemCount(request.getId()) > 0) {
            throw new BusinessException(ErrorCode.EXPRESS_CHARGE_MODE);
        }
        ExpressTemplate express = DataUtil.copy(request, ExpressTemplate.class);
        expressTemplateMapper.updateById(express);
        itemExpressRegionService.createOrUpdate(express.getId(), request.getRegionList());
    }

    @Override
    public List<ItemExpressVO> getExpressList(List<Long> itemIds, Long storeId) {
        if (CollUtil.isEmpty(itemIds)) {
            return Lists.newArrayList();
        }
        return expressTemplateMapper.getExpressList(itemIds, storeId);
    }

    @Override
    public void deleteById(Long id) {
        Long count = this.itemCount(id);
        if (count > 0) {
            throw new BusinessException(ErrorCode.EXPRESS_NOT_DELETE);
        }
        LambdaUpdateWrapper<ExpressTemplate> wrapper = Wrappers.lambdaUpdate();
        // 防止误删除其他人的信息
        wrapper.eq(ExpressTemplate::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.eq(ExpressTemplate::getId, id);
        expressTemplateMapper.delete(wrapper);
    }

    @Override
    public ExpressTemplate selectByIdRequired(Long id) {
        ExpressTemplate selected = expressTemplateMapper.selectById(id);
        if (selected == null) {
            log.error("物流模板不存在或已删除 [{}]", id);
            throw new BusinessException(EXPRESS_NOT_FOUND);
        }
        return selected;
    }

    /**
     * 统计快递被零售商品占用的数量
     *
     * @param expressId 快递id
     * @return 数量
     */
    private Long itemCount(Long expressId) {
        LambdaQueryWrapper<Item> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Item::getExpressId, expressId);
        return itemMapper.selectCount(wrapper);
    }
}
