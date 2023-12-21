package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.poi.PoiTypeAddRequest;
import com.eghm.dto.poi.PoiTypeEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.PoiPointMapper;
import com.eghm.mapper.PoiTypeMapper;
import com.eghm.model.PoiPoint;
import com.eghm.model.PoiType;
import com.eghm.service.business.PoiTypeService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * poi类型表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Slf4j
@AllArgsConstructor
@Service("poiTypeService")
public class PoiTypeServiceImpl implements PoiTypeService {

    private final PoiTypeMapper poiTypeMapper;

    private final PoiPointMapper poiPointMapper;

    @Override
    public Page<PoiType> getByPage(PagingQuery query) {
        LambdaQueryWrapper<PoiType> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(query.getQueryName()), PoiType::getTitle, query.getQueryName());
        wrapper.last(" order by id desc ");
        return poiTypeMapper.selectPage(query.createPage(), wrapper);
    }

    @Override
    public void create(PoiTypeAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        PoiType poiType = DataUtil.copy(request, PoiType.class);
        poiTypeMapper.insert(poiType);
    }

    @Override
    public void update(PoiTypeEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        PoiType poiType = DataUtil.copy(request, PoiType.class);
        poiTypeMapper.updateById(poiType);
    }

    @Override
    public void deleteById(Long id) {
        LambdaQueryWrapper<PoiPoint> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PoiPoint::getTypeId, id);
        Long count = poiPointMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.TYPE_POINT_OCCUPY);
        }
        poiTypeMapper.deleteById(id);
    }

    /**
     * 校验区域类型是否重复
     *
     * @param title 区域类型
     * @param id 区域类型id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<PoiType> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PoiType::getTitle, title);
        wrapper.ne(id != null, PoiType::getId, id);
        Long count = poiTypeMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.TYPE_CODE_REDO);
        }
    }
}
