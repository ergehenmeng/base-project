package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.poi.PoiPointAddRequest;
import com.eghm.dto.poi.PoiPointEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.PoiPointMapper;
import com.eghm.model.PoiPoint;
import com.eghm.service.business.PoiPointService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * poi点位信息 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Slf4j
@AllArgsConstructor
@Service("poiPointService")
public class PoiPointServiceImpl implements PoiPointService {

    private final PoiPointMapper poiPointMapper;

    @Override
    public Page<PoiPoint> getByPage(PagingQuery query) {
        LambdaQueryWrapper<PoiPoint> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(query.getQueryName()), PoiPoint::getTitle, query.getQueryName());
        wrapper.last(" order by id desc ");
        return poiPointMapper.selectPage(query.createPage(), wrapper);
    }

    @Override
    public void create(PoiPointAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        PoiPoint poiPoint = DataUtil.copy(request, PoiPoint.class);
        poiPointMapper.insert(poiPoint);
    }

    @Override
    public void update(PoiPointEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        PoiPoint poiPoint = DataUtil.copy(request, PoiPoint.class);
        poiPointMapper.updateById(poiPoint);
    }

    @Override
    public void deleteById(Long id) {
        poiPointMapper.deleteById(id);
    }

    /**
     * 校验区域点位是否重复
     *
     * @param title 区域点位
     * @param id 区域点位id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<PoiPoint> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PoiPoint::getTitle, title);
        wrapper.ne(id != null, PoiPoint::getId, id);
        Long count = poiPointMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.AREA_POINT_REDO);
        }
    }
}
