package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.poi.LinePointBindRequest;
import com.eghm.dto.poi.PoiLineAddRequest;
import com.eghm.dto.poi.PoiLineEditRequest;
import com.eghm.dto.poi.PoiLineQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.PoiLineMapper;
import com.eghm.mapper.PoiLinePointMapper;
import com.eghm.model.PoiLine;
import com.eghm.model.PoiLinePoint;
import com.eghm.service.business.PoiLineService;
import com.eghm.service.business.PoiPointService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.poi.BasePointResponse;
import com.eghm.vo.poi.LinePointResponse;
import com.eghm.vo.poi.PoiLineResponse;
import com.eghm.vo.poi.PoiLineVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * poi线路表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-21
 */

@Slf4j
@AllArgsConstructor
@Service("poiLineService")
public class PoiLineServiceImpl implements PoiLineService {

    private final PoiLineMapper poiLineMapper;

    private final PoiLinePointMapper poiLinePointMapper;

    private final PoiPointService poiPointService;

    @Override
    public Page<PoiLineResponse> getByPage(PoiLineQueryRequest request) {
        return poiLineMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(PoiLineAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        PoiLine poiLine = DataUtil.copy(request, PoiLine.class);
        poiLine.setState(0);
        poiLineMapper.insert(poiLine);
    }

    @Override
    public void update(PoiLineEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        PoiLine poiLine = DataUtil.copy(request, PoiLine.class);
        poiLineMapper.updateById(poiLine);
    }

    @Override
    public void deleteById(Long id) {
        poiLineMapper.deleteById(id);
    }

    @Override
    public void bindPoint(LinePointBindRequest request) {
        LambdaUpdateWrapper<PoiLinePoint> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PoiLinePoint::getLineId, request.getLineId());
        poiLinePointMapper.delete(wrapper);
        int i = 0;
        for (Long pointId : request.getPointIds()) {
            PoiLinePoint poiLinePoint = new PoiLinePoint();
            poiLinePoint.setLineId(request.getLineId());
            poiLinePoint.setPointId(pointId);
            poiLinePoint.setSort(i++);
            poiLinePointMapper.insert(poiLinePoint);
        }
    }

    @Override
    public void updateState(Long id, Integer state) {
        List<Long> pointIds = poiLinePointMapper.getList(id);
        if (pointIds.isEmpty()) {
            throw new BusinessException(ErrorCode.BIND_POINT_NULL);
        }
        LambdaUpdateWrapper<PoiLine> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(PoiLine::getId, id);
        wrapper.set(PoiLine::getState, state);
        poiLineMapper.update(null, wrapper);
    }

    @Override
    public LinePointResponse getLinePoint(Long id) {
        PoiLine poiLine = this.selectByIdRequired(id);
        LinePointResponse response = new LinePointResponse();
        List<BasePointResponse> pointList = poiPointService.getList(poiLine.getAreaCode());
        response.setPointList(pointList);
        List<Long> checkedList = poiLinePointMapper.getList(id);
        response.setCheckedList(checkedList);
        return response;
    }

    @Override
    public PoiLine selectByIdRequired(Long id) {
        PoiLine poiLine = poiLineMapper.selectById(id);
        if (poiLine == null) {
            throw new BusinessException(ErrorCode.POI_LINE_NULL);
        }
        return poiLine;
    }

    @Override
    public List<PoiLineVO> getList(String areaCode) {
        return poiLineMapper.getList(areaCode);
    }

    /**
     * 校验线路是否重复
     *
     * @param title 线路名称
     * @param id    线路id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<PoiLine> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PoiLine::getTitle, title);
        wrapper.ne(id != null, PoiLine::getId, id);
        Long count = poiLineMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.POI_LINE_REDO);
        }
    }
}
