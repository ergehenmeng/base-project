package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.poi.PoiPointAddRequest;
import com.eghm.dto.poi.PoiPointEditRequest;
import com.eghm.dto.poi.PoiPointQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.PoiPointMapper;
import com.eghm.model.PoiPoint;
import com.eghm.service.business.PoiPointService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.poi.BasePointResponse;
import com.eghm.vo.poi.PoiPointResponse;
import com.eghm.vo.poi.PoiPointVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Page<PoiPointResponse> getByPage(PoiPointQueryRequest query) {
        return poiPointMapper.getByPage(query.createPage(), query);
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

    @Override
    public PoiPoint selectByIdRequired(Long id) {
        PoiPoint poiPoint = poiPointMapper.selectById(id);
        if (poiPoint == null) {
            throw new BusinessException(ErrorCode.AREA_POINT_NULL);
        }
        return poiPoint;
    }

    @Override
    public List<BasePointResponse> getList(String areaCode) {
        return poiPointMapper.getList(areaCode);
    }

    @Override
    public List<PoiPointVO> pointList(Long typeId) {
        return poiPointMapper.pointList(typeId);
    }

    @Override
    public List<PoiPointVO> searchPointList(String areaCode, String queryName) {
        return poiPointMapper.queryList(areaCode, queryName);
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
