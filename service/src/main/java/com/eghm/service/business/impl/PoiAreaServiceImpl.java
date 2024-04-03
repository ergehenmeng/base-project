package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.poi.PoiAreaAddRequest;
import com.eghm.dto.poi.PoiAreaEditRequest;
import com.eghm.dto.poi.StateRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.PoiAreaMapper;
import com.eghm.model.PoiArea;
import com.eghm.service.business.PoiAreaService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.poi.PoiAreaResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * poi区域表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Slf4j
@AllArgsConstructor
@Service("poiAreaService")
public class PoiAreaServiceImpl implements PoiAreaService {

    private final PoiAreaMapper poiAreaMapper;

    @Override
    public Page<PoiArea> getByPage(PagingQuery query) {
        LambdaQueryWrapper<PoiArea> wrapper = Wrappers.lambdaQuery();
        wrapper.and(StrUtil.isNotBlank(query.getQueryName()), queryWrapper -> queryWrapper.like(PoiArea::getTitle, query.getQueryName()).or().like(PoiArea::getCode, query.getQueryName()));
        wrapper.orderByDesc(PoiArea::getId);
        return poiAreaMapper.selectPage(query.createPage(), wrapper);
    }

    @Override
    public List<PoiAreaResponse> getList() {
        return poiAreaMapper.getList();
    }

    @Override
    public void create(PoiAreaAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        this.redoCode(request.getCode(), null);
        PoiArea poiArea = DataUtil.copy(request, PoiArea.class);
        poiAreaMapper.insert(poiArea);
    }

    @Override
    public void update(PoiAreaEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        this.redoCode(request.getCode(), request.getId());
        PoiArea poiArea = DataUtil.copy(request, PoiArea.class);
        poiAreaMapper.updateById(poiArea);
    }

    @Override
    public void updateState(StateRequest request) {
        LambdaUpdateWrapper<PoiArea> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(PoiArea::getState, request.getState());
        wrapper.eq(PoiArea::getId, request.getId());
        poiAreaMapper.update(null, wrapper);
    }

    @Override
    public void deleteById(Long id) {
        poiAreaMapper.deleteById(id);
    }

    /**
     * 校验区域名称是否重复
     *
     * @param title 区域名称
     * @param id    区域id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<PoiArea> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PoiArea::getTitle, title);
        wrapper.ne(id != null, PoiArea::getId, id);
        Long count = poiAreaMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.AREA_TITLE_REDO);
        }
    }

    /**
     * 校验区域编号是否重复
     *
     * @param code 区域编号
     * @param id   区域id
     */
    private void redoCode(String code, Long id) {
        LambdaQueryWrapper<PoiArea> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PoiArea::getCode, code);
        wrapper.ne(id != null, PoiArea::getId, id);
        Long count = poiAreaMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.AREA_CODE_REDO);
        }
    }
}
